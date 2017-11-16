package com.zxy.hibernatelock.demo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.zxy.hibernatelock.pojo.Student;
import com.zxy.hibernatelock.utils.HibernateUtil;

public class QuickStart {
	
	
	/** 测试保存和更新方法的区别 */
	@Test
	public void testSavaVsUpdate(){
		Session session = null;
		Transaction tr = null;
		// 这个学生是从数据库拿的，所以有id。它就是脱管态的
		Student s = this.getStudent();
		s.setName("Ling");
		try {
			session = HibernateUtil.openSession();
			tr = session.beginTransaction();
		    //session.save(s); //如果是游离态(也叫脱管态)对象，save是直接当作没有id来处理的
			session.update(s);
			tr.commit();
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	private Student getStudent() {
		Session session = null;
		Transaction tr = null;
		Student s = null;
		try {
			session = HibernateUtil.openSession();
			s = (Student) session.get(Student.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			return s;
		}
	}
	
	@Test
	public void testHibernate(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Student student = new Student();
		student.setName("王尼玛");
		student.setAge(20);
		session.save(student);
		transaction.commit();
		session.close();
	}
}
