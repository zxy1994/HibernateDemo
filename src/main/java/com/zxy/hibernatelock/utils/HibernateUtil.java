package com.zxy.hibernatelock.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * hibernate工具类
 * @author zxy
 * @Date 2017-06-26 11:23
 * @version V1.0
 */
public class HibernateUtil {
	private static Configuration configuration;
	private static SessionFactory sessionFactory;
	
	static{
		// 创建configuration对象
		configuration = new Configuration();
		// 默认从src下加载hibernate.cfg.xml文件。
		configuration.configure();
		// 创建sessionFactory
		sessionFactory = configuration.buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Session openSession() {
		return sessionFactory.openSession();
	}
	
	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
}
