package zadanie2.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import zadanie2.exceptions.CreatingSessionException;

public class SessionCreator {
	private SessionFactory sessionFactory;

	public SessionCreator() throws CreatingSessionException {

		HibernateFactory factory = new HibernateFactory();
		try {
			sessionFactory = factory.factory();
		} catch (Exception e) {
			throw new CreatingSessionException("Blad przy tworzeniu sesji", e);
		}
	}

	public Session createSession() throws CreatingSessionException {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		return session;
	}

	public void closeSession(Session session) {
		session.getTransaction().commit();
		session.close();
	}
}
