def time=new Date().time
def classloader=new GroovyClassLoader()
def realpath=application.getRealPath('')
def helloworld=classloader.parseClass(new File("${realpath}/WEB-INF/groovy/helloworld.groovy")).newInstance()
def sql=classloader.parseClass(new File("${realpath}/WEB-INF/groovy/DBService.groovy")).newInstance().sql

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
insert into users(name,job) values('${name}','${job}') 
			"""
	)
	"success."
}

println """
<div>
您好，${helloworld.getHello()}
</div>
<div>
数据库测试 ...<br />
创建数据库，${createTable(sql)}<br />
初始化数据，${initData(sql)}<br />
查询数据，列表：<br />
<span>姓名</span>||<span>职业</span><br />
"""
sql.rows("select * from users").each{row->
	println "${row.name} || ${row.job}<br />"
}

println """
</div>
<div>耗时：${new Date().time-time}
</div>
"""
