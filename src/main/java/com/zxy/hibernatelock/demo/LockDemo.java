package com.zxy.hibernatelock.demo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.zxy.hibernatelock.pojo.Student;
import com.zxy.hibernatelock.utils.HibernateUtil;

/**
 * 乐观锁的demo
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年9月25日 上午9:36:24
 * @version  v1.0
 */

public class LockDemo {
	@Test
	public void test01() {
		Session session1 = null;
		Session session2 = null;
		Transaction tr1 = null;
		Transaction tr2 = null;
		try {
			// 开启2个session，开启2个事务
			session1 = HibernateUtil.openSession();
			session2 = HibernateUtil.openSession();
			tr1 = session1.beginTransaction();
			tr2 = session2.beginTransaction();
			Student s1 = (Student) session1.get(Student.class, 1);
			Student s2 = (Student) session2.get(Student.class, 1);
			// session1修改属性值
			s1.setName("宝强");
			session1.saveOrUpdate(s1);
			tr1.commit();	//这里提交后，版本变了
			// session2再修改
			s2.setName("宋哲");
			session2.saveOrUpdate(s2);
			// 再创建一个马蓉
			Student mr = new Student();
			mr.setName("马蓉");
			session2.save(mr);
			tr2.commit();	// 这里抛异常
		} catch (Exception e) {
			e.printStackTrace();
			tr2.rollback();
		} finally {
			if(session1 != null) {
				session1.close();
			}
			if(session2 != null) {
				session2.close();
			}
		}
		//未完待续
		
	}
}
