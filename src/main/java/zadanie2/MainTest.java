package zadanie2;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import zadanie2.connectors.sqlConnection.HibernateFactory;
import zadanie2.exceptions.CreatingSessionException;
import zadanie2.exceptions.DaoException.RateDaoException;
import zadanie2.model.hibernate.Rate;

public class MainTest {

	public static void main(String[] args) throws Exception {
		System.out.println(test2(10).toString());
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

	public static Rate test2(int id) throws RateDaoException {
		try {
			Session session = createSession();
			Query query = session.createQuery("from Rate where rate_id = :id ");
			query.setParameter("id", id);
			Rate rate = (Rate) query.uniqueResult();
			closeSession(session);
			return rate;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy wyszukiwaniu Rate po id", e);
		}
	}

	private static Session createSession() throws CreatingSessionException {
		try {
			HibernateFactory factory = new HibernateFactory();
			SessionFactory sessionFactory;
			sessionFactory = factory.factory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			return session;
		} catch (Exception e) {
			throw new CreatingSessionException("Blad przy tworzeniu sesji", e);

		}
	}

	private static void closeSession(Session session) {
		session.getTransaction().commit();
		session.close();
	}

}
