package com.easymorse.dbunit.demo.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品
 * @author <a href="mailto:marshal.wu@gmail.com">Marshal Wu</a>
 *
 * $LastChangedBy$
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 *
 * $HeadURL$
 */
@Entity
@Table(name="PRODUCTS")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="PRODUCT_NAME")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
