#!/usr/bin/env groovy

import java.util.Arrays;

import groovy.lang.Grab;
import org.apache.log4j.*
import javax.jms.*
import org.apache.activemq.ActiveMQConnectionFactory
import com.lightspeedleader.directorywatcher.*

PropertyConfigurator.configure("log4j.properties");

@Grab(group="com.lightspeedleader",module="directorywatcher",version="1.0")
@Grab(group="log4j",module="log4j",version="1.2.12")
@Grab(group="org.apache.activemq",module="activemq-core",version="5.3.0")
class Monitor extends BaseListener implements IFileListener{
	
	private static Logger logger=Logger.getLogger(Monitor.class)

	def videoExtends=['mov','flv','avi'] as Set

	def connection
	
	public void onStart(Object monitoredResource) {
		logger.debug("开始监控目录资源，目录为：${monitoredResource}")
		def connectionFactory=new ActiveMQConnectionFactory("tcp://localhost:61616")
		connection=connectionFactory.createConnection()
		connection.start()
	}

	public void onStop(Object notMonitoredResource) {
		logger.debug("停止监控目录资源，目录为：${notMonitoredResource}")
		connection.close()
	}
	
	public void onAdd(Object newResource) {
		logger.debug("监控目录增加了新资源，资源名称：${newResource}")
		
		if(!isVideoFile(newResource)){
			logger.debug("资源${newResource}不是视频文件。")
			return
		}
		
		def session=connection.createSession(true,Session.AUTO_ACKNOWLEDGE)
		def destination=session.createQueue("video_convert")
		def producer=session.createProducer(destination)
		producer.setDeliveryMode(DeliveryMode.PERSISTENT)
		def fileName=newResource.name.substring(0,newResource.name.lastIndexOf('.'))
		def message=session.createTextMessage("${newResource};${fileName}.mp4")
		producer.send(message)
		session.commit()
		session.close()
		logger.debug("已将${newResource}提交到队列${destination}中。")
	}

	def isVideoFile(file){
		if(file.isDirectory()){
			return false
		}
		
		if(file.name.startsWith('.')){
			return false
		}
		
		if (videoExtends.contains(file.name.tokenize('.').last().toLowerCase())){
			return true
		}
		
		false
	}
	
	public void onChange(Object changedResource) {
		logger.debug("监控目录有资源发生变化，资源名称：${changedResource}")
		onAdd(changedResource)
	}
	
	public void onDelete(Object deletedResource) {
		logger.debug("监控目录有资源被删除，资源名称：${deletedResource}")
	}
}

class ShutdownThread extends Thread{
	
	private static final Logger logger=Logger.getLogger(ShutdownThread.class)
	
	def dw
	
	ShutdownThread(dw){
		this.dw=dw
	}
	
	public void run(){
		logger.debug("关闭文件目录监控...")
		this.dw.stop()
		logger.debug("已关闭。")
	}
}

DirectoryWatcher dw = new DirectoryWatcher(".", 5);
dw.addListener(new Monitor());
dw.start();
Runtime.runtime.addShutdownHook(new ShutdownThread(dw))