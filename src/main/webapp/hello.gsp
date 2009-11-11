def time=new Date().time
def classloader=new GroovyClassLoader()
def realpath=application.getRealPath('')
def helloworld=classloader.parseClass(new File("${realpath}/WEB-INF/groovy/helloworld.groovy")).newInstance()

println """
您好，${helloworld.getHello()}，耗时：${new Date().time-time}
"""