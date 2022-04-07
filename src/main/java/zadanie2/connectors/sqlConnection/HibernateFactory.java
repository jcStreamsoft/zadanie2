package zadanie2.connectors.sqlConnection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateFactory {
	private final SessionFactory sessionFactory;

	public HibernateFactory() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

	}

	@Bean
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
