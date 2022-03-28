package zadanie2;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import zadanie2.daos.HibernateFactory;

public class MainTest {

	public static void main(String[] args) throws Exception {
		test2(18);
	}

	public static void test1() throws Exception {
		HibernateFactory factory = new HibernateFactory();
		SessionFactory sessionFactory = factory.factory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Rate where date = '2012-01-04'");
		List list = query.list();
		System.out.println(list);
	}

	public static void test2(int id) throws Exception {
		HibernateFactory factory = new HibernateFactory();
		SessionFactory sessionFactory = factory.factory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Currency ");
		List list = query.list();
		System.out.println(list);
	}
}
