#!/usr/bin/env groovy

import groovyx.net.ws.*

@Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.1')
def printDate(){
	def proxy = new WSClient("http://localhost:9090/DateFormatService?wsdl", this.class.classLoader)
	proxy.initialize()
	println "今天是${proxy.format(new Date().time)}"
}

printDate()
