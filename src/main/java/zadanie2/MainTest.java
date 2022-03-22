package zadanie2;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainTest {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tur");

	}
}
