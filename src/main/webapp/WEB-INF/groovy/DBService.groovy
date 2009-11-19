import groovy.lang.Singleton;
import groovy.sql.Sql;

class DBService{
	
	private static sql

	def databaseName='mydb'
	def url="jdbc:mysql://localhost/${databaseName}?useUnicode=true&characterEncoding=utf-8"
	def driverClassName="com.mysql.jdbc.Driver"
	def username="root"
	def password="password"
	
	def initDataSource(){
		sql=Sql.newInstance(
			url,
			username,
			password,
			driverClassName
		)
		
		sql.execute("create database if not exists " +databaseName+	" CHARACTER SET utf8")
	}
	
	def getSql(){
		if(!sql){
			initDataSource()
		}
		sql
	}
}