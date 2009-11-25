#!/usr/bin/env groovy

import groovy.lang.Grab;
import org.apache.log4j.*
import javax.jms.*
import org.apache.activemq.ActiveMQConnectionFactory

@Grab(group="log4j",module="log4j",version="1.2.12")
@Grab(group="org.apache.activemq",module="activemq-core",version="5.3.0")
class Convertor implements MessageListener{
	
	private static Logger logger=Logger.getLogger(Convertor.class)
	
	def connection
	def session
	
	def start(){
		logger.info("启动视频转换器...")
		def connectionFactory=new ActiveMQConnectionFactory("tcp://localhost:61616")
		connection=connectionFactory.createConnection()
		connection.start()
		session=connection.createSession(true,Session.AUTO_ACKNOWLEDGE)
		def destination=session.createQueue("video_convert")
		def consumer=session.createConsumer(destination)
		consumer.setMessageListener(this)
		logger.info("视频转换器启动完毕。")
	}

	public void onMessage(Message message){
		logger.debug("收到消息，正文是：${message.text}")
		def parameters=message.text.split(";")
		convert(parameters[0],parameters[1])
		session.commit()
		logger.debug("提交会话。")
	}
	
	def convert(input,output){
		def time=new Date().time
		def file=new File(output)
		
		if(file.exists()){
			file.delete()
			logger.debug("删除已经存在的输出文件 ${output}。")
		}
		
		def process="ffmpeg -i ${input} ${output}".execute()
		process.waitFor()
		
		logger.debug("生成${output}成功，耗时${new Date().time-time}毫秒。")
	}
}

PropertyConfigurator.configure("log4j.properties");
new Convertor().start()