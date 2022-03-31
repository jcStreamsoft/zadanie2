package zadanie2.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import zadanie2.exceptions.CreatingSessionException;

public final class SessionCreator {
	private static SessionFactory sessionFactory;

	public SessionCreator() throws CreatingSessionException {
		try {
			sessionFactory = createSessionFactory();
		} catch (Exception e) {
			sessionFactory = null;
			throw new CreatingSessionException("Blad przy tworzeniu sesji", e);
		}
	}

	private SessionFactory createSessionFactory() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

		return sessionFactory;
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
