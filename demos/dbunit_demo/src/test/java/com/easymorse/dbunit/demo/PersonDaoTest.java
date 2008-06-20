package com.easymorse.dbunit.demo;

import org.junit.Test;
import org.springframework.test.AbstractTransactionalSpringContextTests;

public class PersonDaoTest extends AbstractTransactionalSpringContextTests {
	private PersonDao personDao;

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:applicationContext-resources.xml",
				"classpath:applicationContext-dao.xml" };
	}

	@Test
	public void testCreate() {
		Person person = new Person();
		person.setName("zhangsan");
		assertNotNull(this.personDao.save(person).getId());
	}

	@Test
	public void testGet() {
		Person person = this.personDao.get(1L);
		assertNotNull(person);
	}
}
