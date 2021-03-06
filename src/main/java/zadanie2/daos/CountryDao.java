package zadanie2.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import zadanie2.connectors.sqlConnection.HibernateFactory;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.interfaces.daos.Dao;
import zadanie2.model.hibernate.Country;

@Repository
public class CountryDao implements Dao<Country> {

	protected SessionFactory sessionFactory;

	public CountryDao(HibernateFactory hibernateFactory) {
		this.sessionFactory = hibernateFactory.getSessionFactory();
	}

	@Override
	public Country get(long id) throws DaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.createQuery("from Country where country_id = :id ");
			query.setParameter("id", id);
			Country country = (Country) query.uniqueResult();
			session.getTransaction().commit();
			return country;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Country> getAll() throws DaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			List<Country> list = session.createQuery("from Country").list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Country save(Country t) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.save(t);
			session.getTransaction().commit();
			return t;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return null;
		}
	}

	@Override
	public Country update(long id, Country t) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Country updatedCountry = new Country();
			updatedCountry.setId(id);
			updatedCountry.setName(t.getName());

			session.update(updatedCountry);
			session.getTransaction().commit();
			return get(id);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return null;
		}

	}

	@Override
	public Country deleteById(long id) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Country country = get(id);
			session.delete(country);
			session.getTransaction().commit();
			return country;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return null;
		}
	}

	public List<Country> findCountriesWithMoreThanTwoCurrencies() throws DaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.getNamedQuery(Country.FIND_COUNTRIES_WITH_MORE_CURRENCIES);
			List<Country> result = query.list();
			session.getTransaction().commit();
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
