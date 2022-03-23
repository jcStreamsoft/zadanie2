package zadanie2;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import zadanie2.model.hibernate.Country;

public class MainTest {

	public static void main(String[] args) throws Exception {
		res();
	}

	public static void res() throws Exception {
		HibernateFactory factory = new HibernateFactory();
		SessionFactory sessionFactory = factory.factory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List<Country> result = session.createQuery("from Country", Country.class).getResultList();
		for (Country c : result) {
			System.out.println(c.getName());
		}
		session.getTransaction().commit();
		session.close();
	}

}
