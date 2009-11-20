#!/usr/bin/env groovy

import org.apache.log4j.*
import javax.jms.*
import org.apache.activemq.ActiveMQConnectionFactory
import groovy.xml.*

PropertyConfigurator.configure("log4j.properties");
def consumer=new JMSConsumer()

consumer.start()
Runtime.runtime.addShutdownHook(new ShutdownThread(consumer))

class ShutdownThread extends Thread{
	
	private static final Logger logger=Logger.getLogger(ShutdownThread.class)
	
	def consumer
	
	ShutdownThread(consumer){
		this.consumer=consumer
	}
	
	public void run(){
		logger.debug("shutdown consumer...")
		this.consumer.connection.close()
		logger.debug("shutdown succesful.")
	}
}

class JMSConsumer implements MessageListener{
	
	private static final Logger logger=Logger.getLogger(JMSConsumer.class)

	def connection
	def session
	
	public void start(){
		logger.debug("start consumer...")
		
		def connectionFactory=new ActiveMQConnectionFactory("tcp://localhost:61616")
		connection=connectionFactory.createConnection()
		connection.start()
		
		session=connection.createSession(true,javax.jms.Session.AUTO_ACKNOWLEDGE)
		def destination=session.createQueue("myqueue")
		def consumer=session.createConsumer(destination)
		consumer.setMessageListener(this)
		
		logger.debug("start ok.")
	}
	
	public void onMessage(Message message) {
		sendMessageToQueue('message.confirm','tag','ok.')
		session.commit()
		logger.debug("receive message successful.")
	}
	
	def sendMessageToQueue(queueName,tag,text){
		def producer=session.createProducer(session.createQueue(queueName))
		producer.setDeliveryMode(javax.jms.DeliveryMode.PERSISTENT)
		
		def out=new StringWriter()
		def xmlResults=new MarkupBuilder(out)
		
		xmlResults.messages{
			message text
		}
		
		def message=session.createTextMessage(out.toString())
		message.setStringProperty('tag',tag)
		producer.send(message)
		logger.debug("send confirm message successful.")
	}
}
