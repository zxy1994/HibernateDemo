package com.zxy.hibernatelock.demo;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.zxy.hibernatelock.pojo.Student;
import com.zxy.hibernatelock.utils.HibernateUtil;

public class QuickStart {
	public static void main(String[] args) {
		Student s = new Student();
		s.setName("老王");
		s.setAge(25);
		Session session = null;
		Transaction tr = null;
		try {
			session = HibernateUtil.openSession();
			tr = session.beginTransaction();
			session.save(s);
			tr.commit();
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
