package zadanie2.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.model.hibernate.Country;

public class CountryDao extends BaseDao<Country> {

	public CountryDao(SessionFactory sessionFactory) {
		super(sessionFactory);
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
	public void save(Country t) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.save(t);
			session.getTransaction().commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	@Override
	public void update(long id, Country t) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Query query = session.createQuery(
					"update Country set country_name =:country_name, currency_id = :currency_id  where country_id = :id");
			query.setParameter("country_name", t.getName());
			// query.setParameter("country_name", t.getCurrency().getId());
			query.setParameter("id", id);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}

	}

	@Override
	public void deleteById(long id) throws DaoException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Country country = get(id);
			session.delete(country);
			session.getTransaction().commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void findCountriesWithMoreThanTwoCurrencies() throws DaoException {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Query query = session.getNamedNativeQuery(Country.FIND_COUNTRIES_WITH_MORE_CURRENCIES);
			// List<Country> list = query.list();
			List<Object[]> result = query.list();
			for (Object[] x : result) {
				System.out.println(x[1] + " -- " + x[0]);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
