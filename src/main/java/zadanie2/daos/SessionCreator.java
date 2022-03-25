package zadanie2.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import zadanie2.exceptions.CreatingSessionException;

public class SessionCreator {
	public static Session createSession() throws CreatingSessionException {
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

	public static void closeSession(Session session) {
		session.getTransaction().commit();
		session.close();
	}
}
