package zadanie2;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import zadanie2.connectors.sqlConnection.HibernateFactory;

public class MainTest {

	public static void main(String[] args) throws Exception {
		test1();
	}

	public static void test1() throws Exception {
		HibernateFactory factory = new HibernateFactory();
		SessionFactory sessionFactory = factory.factory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Rate where date = '2012-01-04'");// here persistent class name is Emp
		List list = query.list();
		System.out.println(list);
	}
}
