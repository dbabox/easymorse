package com.easymorse.dbunit.demo.shop;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = { CascadeType.ALL },mappedBy="transaction")
	private List<TransactionItem> transactionItems;

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<TransactionItem> getTransactionItems() {
		return transactionItems;
	}

	public void setTransactionItems(List<TransactionItem> transactionItems) {
		this.transactionItems = transactionItems;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
