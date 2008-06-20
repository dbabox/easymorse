package com.easymorse.dbunit.demo.shop;

import java.util.List;

import com.easymorse.dbunit.demo.GenericDaoHibernate;

public class TransactionDaoImpl extends GenericDaoHibernate<Transaction, Long>
		implements TransactionDao {

	public TransactionDaoImpl() {
		super(Transaction.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findCustomerTransactionIdAndProductName(
			Long customerId) {
		return this
				.getHibernateTemplate()
				.find(
						"select t.id,i.merchandise.product.name from Transaction t"
								+ " inner join t.customer c inner join t.transactionItems i "
								+ "  where c.id=? ", customerId);
	}

}
