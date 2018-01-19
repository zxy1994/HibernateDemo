package com.zxy.hibernatelock.demo;

import java.util.Date;
import java.util.HashSet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.zxy.hibernatelock.pojo.Order;
import com.zxy.hibernatelock.pojo.Product;
import com.zxy.hibernatelock.utils.HibernateUtil;

/**
 * 主要测试一对多的双向关联
 * @author   ZENG.XIAO.YAN
 * @date 	 2018年1月18日 下午4:09:54
 * @version  v1.0
 */

public class TestOneToMany {
	
	@Test
	public void testSave01() {
		Session session = null;
		Transaction tr = null;
		try {
			session = HibernateUtil.openSession();
			tr = session.beginTransaction();
			// 创建订单
			Order o1 = new Order("订单1", new Date());
			// 创建商品
			Product p1 = new Product("小米6", 2999.0);
			Product p2 = new Product("oppo R11", 3199.0);
			// 订单中添加商品信息
			HashSet<Product> set = new HashSet<>();
			set.add(p1);
			set.add(p2);
			o1.setProducts(set);
			// 保存
			session.save(o1);
			session.save(p1);
			session.save(p2);
			tr.commit();
			/*
			 * 生成sql如下：
			 * Hibernate: insert into t_order (create_time, name, id) values (?, ?, ?)
			 * Hibernate: insert into t_product (name, order_id, price) values (?, ?, ?)
			 * Hibernate: insert into t_product (name, order_id, price) values (?, ?, ?)
			 * Hibernate: update t_product set order_id=? where id=?
			 * Hibernate: update t_product set order_id=? where id=?
			 * */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != session){
				session.close();
			}
		}
	}
	
	@Test
	public void testSave02() {
		Session session = null;
		Transaction tr = null;
		try {
			session = HibernateUtil.openSession();
			tr = session.beginTransaction();
			// 创建订单
			Order o1 = new Order("订单2", new Date());
			// 创建商品
			Product p1 = new Product("小米5", 1999.0);
			Product p2 = new Product("oppo R9", 2499.0);
			// 商品中添加订单信息
			p1.setOrder(o1);
			p2.setOrder(o1);
			// 保存
			session.save(o1);
			session.save(p1);
			session.save(p2);
			tr.commit();
			/*
			 * 生成sql如下：
			 * Hibernate: insert into t_order (create_time, name, id) values (?, ?, ?)
			 * Hibernate: insert into t_product (name, order_id, price) values (?, ?, ?)
			 * Hibernate: insert into t_product (name, order_id, price) values (?, ?, ?)
			 * */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != session){
				session.close();
			}
		}
	}
	
	@Test
	public void testQuery() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			Order order = (Order) session.get(Order.class, "402881c061085a7c0161085a800f0000");
			System.out.println("order:{" + "id=" + order.getId() +" ,name="+order.getName()+" }");
			System.out.println("product:"+order.getProducts());
			/*
			 * 生成sql如下：
			 * Hibernate: 
			    select
			        order0_.id as id3_1_,
			        order0_.create_time as create2_3_1_,
			        order0_.name as name3_1_,
			        products1_.order_id as order4_3_3_,
			        products1_.id as id4_3_,
			        products1_.id as id4_0_,
			        products1_.name as name4_0_,
			        products1_.order_id as order4_4_0_,
			        products1_.price as price4_0_ 
			    from
			        t_order order0_ 
			    left outer join
			        t_product products1_ 
			            on order0_.id=products1_.order_id 
			    where
			        order0_.id=?*/
			/*打印的结果如下：
			order:{id=402881c061085a7c0161085a800f0000 ,name=订单1 }
			product:[Product [id=3, name=小米6, price=2999.0], 
			Product [id=4, name=oppo R11, price=3199.0]]*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != session){
				session.close();
			}
		}
	}
}
