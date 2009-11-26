#!/usr/bin/env groovy

import groovyx.net.ws.*

@Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.1')
class DateFormatService{
	String format(long time){
		new Date(time).format('yyyy年MM月dd日')
	}
}

def server=new WSServer()
server.setNode("DateFormatService", "http://localhost:9090/DateFormatService")
server.start()