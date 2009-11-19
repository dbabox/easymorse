import groovy.xml.MarkupBuilder;

def time=new Date().time
def classloader=new GroovyClassLoader()
def realpath=application.getRealPath('')
def helloworld=classloader.parseClass(new File("${realpath}/WEB-INF/groovy/helloworld.groovy")).newInstance()
def sql=classloader.parseClass(new File("${realpath}/WEB-INF/groovy/DBService.groovy")).newInstance().sql
def connectionFactory=classloader.parseClass(new File("${realpath}/WEB-INF/groovy/JMSService.groovy")).newInstance().connectionFactory

def createTable(sql){
	sql.execute("""
create table if not exists users(
	name varchar(50),
	job  varchar(50)
)
			""")
	"success."
}

def initData(sql){
	def name="张三"
	def job="程序员"
	sql.execute("""
			insert into users(name,job) values(?,?) 
			""",[name,job]
	)
	"success."
}

def sendMessageToQueue(connectionFactory,queueName,tag,text){
	def connection=connectionFactory.createConnection()
	connection.start()
	def session=connection.createSession(true,javax.jms.Session.AUTO_ACKNOWLEDGE)
	def destination=session.createQueue(queueName)
	def producer=session.createProducer(destination)
	producer.setDeliveryMode(javax.jms.DeliveryMode.PERSISTENT)
	def message=session.createTextMessage(text)
	message.setStringProperty('tag',tag)
	producer.send(message)
	session.commit()
	
	connection.close()
	'sucess.'
}

println """
<div>
您好，${helloworld.getHello()}
</div>
<div>
数据库测试  ... <br />
创建数据库 ... ${createTable(sql)}<br />
初始化数据 ... ${initData(sql)}<br />
查询数据，列表：<br />
<span>姓名</span>||<span>职业</span><br />
"""
sql.rows("select * from users").each{row->
	println "${row.name} || ${row.job}<br />"
}

def params=[:]
def address=1066987654321

params.smsItems=['123','223','323']
params.wapItms=['http://wap.mycompony.com/bz.php?id=23']

def out=new StringWriter()
def xmlResults=new MarkupBuilder(out)

xmlResults.messages{
	if(params.smsItems.isEmpty()){
		message 'sms have no data.'
	}else{
		sms(address:address){
			params.smsItems.each{
				content it
			}
		}
	}
	wap{
		params.wapItms.each{
			url it
		}
	}
}

def t1=new Date().time
println """
<div>
发送消息到队列  ... ${sendMessageToQueue(connectionFactory,'myqueue','15201234567',out.toString())}
</div>
"""

println """
</div>
<div>总耗时：${new Date().time-t1}
</div>
"""
println """
</div>
<div>总耗时：${new Date().time-time}
</div>
"""
