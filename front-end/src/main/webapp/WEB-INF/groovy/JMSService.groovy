import org.apache.activemq.ActiveMQConnectionFactory;



class JMSService{
	
	private static connectionFactory
	
	def url="tcp://localhost:61616"
	def username=""
	def password=""
	
	def initConnectionFactory(){
		connectionFactory=new ActiveMQConnectionFactory(url)
	}
	
	def getConnectionFactory(){
		if(!connectionFactory){
			initConnectionFactory()
		}
		connectionFactory
	}
}