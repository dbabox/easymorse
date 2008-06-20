package com.easymorse.dbunit.demo;


public class PersonDaoImpl extends GenericDaoHibernate<Person, Long> implements
		PersonDao {

	public PersonDaoImpl() {
		super(Person.class);
	}

}
