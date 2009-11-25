#!/usr/bin/env groovy

import groovy.lang.Grab;
import org.apache.log4j.*

@Grab(group="log4j",module="log4j",version="1.2.12")
class Convertor{
	
	private static Logger logger=Logger.getLogger(Convertor.class)
	
	static{
		PropertyConfigurator.configure("log4j.properties");				
	}
	
	def convert(input,output){
		def time=new Date().time
		def file=new File(output)
		
		if(file.exists()){
			file.delete()
			logger.debug("delete file ${output}.")
		}
		
		def process="ffmpeg -i ${input} ${output}".execute()
		process.waitFor()
		
		logger.debug("生成${output}成功，耗时${new Date().time-time}毫秒。")
	}
}


def input='test.mov'
def output='output.mp4'
new Convertor().convert(input,output)