import org.apache.log4j.Logger;

def log=Logger.getLogger(this.getClass())
log.debug('sms send ...')

def message

if(request.parameters.phone){
	log.debug("phone number: ${request.parameters.phone}")
	message=receiveMessage(request.parameters.phone)
	if(!message){
		message='--'
	}else{
		message=message.text
	}
	
}

println "收到消息，内容是：${message}"

log.debug('sms finished.')

def receiveMessage(phone){
	def connectionFactory=new GroovyClassLoader().parseClass(new File("${application.getRealPath('')}/WEB-INF/groovy/JMSService.groovy")).newInstance().connectionFactory
	def connection=connectionFactory.createConnection()
	connection.start()
	def session=connection.createSession(true,javax.jms.Session.AUTO_ACKNOWLEDGE)
	def destination=session.createQueue("outbound-${phone}")
	def consumer=session.createConsumer(destination)
	def message=consumer.receiveNoWait()
	session.commit()
	connection.close()
	message
}