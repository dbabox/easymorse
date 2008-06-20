package com.easymorse.dbunit.demo.shop;

import java.util.List;

import org.junit.Test;
import org.springframework.test.AbstractTransactionalSpringContextTests;

public class TransactionDaoTest extends AbstractTransactionalSpringContextTests {

	private TransactionDao transactionDao;

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:applicationContext-resources.xml",
				"classpath:applicationContext-dao.xml" };
	}

	@Test
	public void testFindCustomerTransactionIdAndProductName() {
		List<Object[]> list = this.transactionDao
				.findCustomerTransactionIdAndProductName(1L);
		assertNotNull(list);
		Object[] result = list.get(0);
		Long id = (Long) result[0];
		String name = (String) result[1];
		assertEquals(new Long(1L), id);
		assertEquals(name, "AAA");

	}
}
