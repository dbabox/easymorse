#!/usr/bin/env groovy

import groovy.sql.Sql;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import groovy.xml.MarkupBuilder;

class MyBiz implements MessageListener{
	def classloader=new GroovyClassLoader()
	def connectionFactory=classloader.parseClass(new File("JMSService.groovy")).newInstance().connectionFactory
	def sql=classloader.parseClass(new File("DBService.groovy")).newInstance().sql
	def connection
	def session
	def consumer
	def destination
	def shutdownDestination
	def shutdownConsumer
	
	def runConsume(){
		println "启动监听器"
		connection=connectionFactory.createConnection()
		connection.start()
		session=connection.createSession(true,javax.jms.Session.AUTO_ACKNOWLEDGE)

		destination=session.createQueue("myqueue")
		consumer=session.createConsumer(destination)
		consumer.setMessageListener(this)

		shutdownDestination=session.createTopic("shutdownTopic")
		shutdownConsumer=session.createConsumer(shutdownDestination)
		shutdownConsumer.setMessageListener(this)
	}

	 public void onMessage(Message message) {
		if(message.destination.equals(destination)){
			println """
收到消息：${message.text}
			"""
			sendMessageToQueue('receive.confirm','--','ok.')
			session.commit()
		}else if (message.destination.equals(shutdownDestination)){
			print ('shutdown ...')
			session.commit()
			connection.close()
			println ('ok.')
		}
		
	 }

	 def sendMessageToQueue(queueName,tag,text){
		def destination=session.createQueue(queueName)
		def producer=session.createProducer(destination)
		producer.setDeliveryMode(javax.jms.DeliveryMode.PERSISTENT)

		def out=new StringWriter()
		def xmlResults=new MarkupBuilder(out)
		
		xmlResults.messages{
			message text
		}
		
		def message=session.createTextMessage(out.toString())
		message.setStringProperty('tag',tag)
		producer.send(message)
	}
}

def biz=new MyBiz()
def time=new Date().time

println """
获取到sql连接对象：${biz.sql}，数据库正常。
获取到消息服务器工厂对象：${biz.connectionFactory}，消息服务器正常。
"""

biz.runConsume()

println """
耗时：${new Date().time-time}		
"""

