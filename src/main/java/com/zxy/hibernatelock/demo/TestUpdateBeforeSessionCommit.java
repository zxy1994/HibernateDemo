package com.zxy.hibernatelock.demo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.zxy.hibernatelock.pojo.Student;
import com.zxy.hibernatelock.utils.HibernateUtil;

/**
 * TestUpdateBeforeSessionCommit
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年11月16日 上午10:21:17
 * @version  v1.0
 */

public class TestUpdateBeforeSessionCommit {
	
	/**
	 * 主要测试目的：同一个session同，修改完后立即统计，统计结果是否正确？
	 * 测试案例：
	 * 在session中，
	 * 	（1）获取数据库中的年龄为20的student，然后修改为21;
	 * 	（2）修改完后在统计年龄为20的student的数量,看这个数量能否统计正确
	 * 	 其中（1）（2）在一个session中，也在同一个事务中;意味着（2）的时候（1）其实还没提交。
	 * 
	 * 该测试对学习Hibernate又较大的意义。 如：
	 * 	项目中一对多关系，通过修改多方的状态，然后同步一方的状态时，就需要这样处理。
	 *  处理方案：修改完多方后，统计下是不是所有的多方状态都修改了；如果是，才修改一方状态。
	 *  关键点：如果这个统计此时拿不到实时的数据，那么这方案就失败了
	 */
	// 测试结果：本测试通过，说明在同一个session中  update对象后后 马上统计数量是没问题的,数据是实时的;后续开发直接按这想法就ok了
	@Test
	public void test01(){
		Session session = HibernateUtil.openSession();
		Transaction tr = session.beginTransaction();
		Query query = session.createQuery("FROM Student s WHERE s.age = ?");
		// 先查20岁学生
		query.setParameter(0, 20);
		List<Student> list = query.list();
		// 查出来改为21岁
		for (Student student : list) {
			student.setAge(21);
			session.update(student);
		}
		// 再统计20岁学生数量,判断这个统计是否是实时的
		Query query2 = session.createQuery("SELECT count(*) FROM Student s WHERE s.age = ?");
		query2.setParameter(0, 20);
		long count = (long) query2.uniqueResult();
		
		System.out.println("修改后，20岁学生的数量为：" + count);//测试结果是0
		tr.commit();
		session.close();
	}
}
