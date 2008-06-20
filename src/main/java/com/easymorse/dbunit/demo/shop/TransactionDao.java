package com.easymorse.dbunit.demo.shop;

import java.util.List;

import com.easymorse.dbunit.demo.GenericDao;

public interface TransactionDao extends GenericDao<Transaction, Long>{
	public List<Object[]> findCustomerTransactionIdAndProductName(Long customerId);
}
