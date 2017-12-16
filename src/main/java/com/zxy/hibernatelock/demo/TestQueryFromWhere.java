package com.zxy.hibernatelock.demo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.zxy.hibernatelock.pojo.Book;
import com.zxy.hibernatelock.utils.HibernateUtil;

/**
 * 测试当session一级缓存有数据时，是先从数据库查数据，还是从session中查数据
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年12月16日 下午2:23:27
 * @version  v1.0
 */

public class TestQueryFromWhere {
	
	public static void main(String[] args) {
		Session session = HibernateUtil.openSession();
		Transaction tr = session.beginTransaction();
		Book book = new Book("SSH实战3","李刚");
		Book book2 = new Book("SSH实战4","李刚");
		List<Book> bookList = new ArrayList<>();
		bookList.add(book);
		bookList.add(book2);
		for (int i = 0; i < 1000000; i++) {
			bookList.add(new Book("java a" + i,"李刚"));
		}
		int i = 0;
		for (Book b : bookList) {
			// 经测试发现，在第二次for循环时，执行这个之前会发送一句update语句
			List<Book>  list = session.createCriteria(Book.class)
					.add(Restrictions.eq("author", "李刚")).list();
			System.out.println("query------"+(++i));
			//System.out.println(list);
			if(list.size()>0){
				b.setId(list.get(0).getId());
			}
			session.merge(b);
		}
		tr.commit();
		session.close();
	}
}
