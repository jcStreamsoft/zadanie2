package zadanie2;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import zadanie2.model.hibernate.Rate;

public class MainTest {

	public static void main(String[] args) throws Exception {
		res();
	}

	public static void res() throws Exception {
		HibernateFactory factory = new HibernateFactory();
		SessionFactory sessionFactory = factory.factory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List<Rate> result = session.createQuery("from Rate", Rate.class).getResultList();
		for (Rate c : result) {
			System.out.println(c.toString());
		}
		session.getTransaction().commit();
		session.close();
	}

}
