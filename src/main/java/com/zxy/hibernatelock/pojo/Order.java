package com.zxy.hibernatelock.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_order")
public class Order {

	@Id
	@GenericGenerator(name = "tt", strategy = "uuid")
	@GeneratedValue(generator = "tt")
	@Column(name = "id", length = 32)
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "create_time")
	private Date createTime;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="order_id")
	private Set<Product> products = new HashSet<>();

	public Order(String name, Date createTime) {
		super();
		this.name = name;
		this.createTime = createTime;
	}

	public Order() {
		super();
	}

	/** setter and getter method */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", name=" + name + ", createTime=" + createTime + ", products=" + products + "]";
	}
	
}
