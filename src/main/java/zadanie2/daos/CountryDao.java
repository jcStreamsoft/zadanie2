package zadanie2.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.exceptions.daoExceptions.RateDaoException;
import zadanie2.model.hibernate.Country;

public class CountryDao extends BaseDao<Country> {

	public CountryDao(SessionCreator sessionCreator) {
		super(sessionCreator);
	}

	@Override
	public Country get(long id) throws DaoException {
		try {

			Session session = sessionCreator.createSession();
			Query query = session.createQuery("from Country where country_id = :id ");
			query.setParameter("id", id);
			Country country = (Country) query.uniqueResult();
			sessionCreator.closeSession(session);
			return country;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy wyszukiwaniu Country po id", e);
		}
	}

	@Override
	public List<Country> getAll() throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			List<Country> list = session.createQuery("from Country").list();
			sessionCreator.closeSession(session);
			return list;
		} catch (Exception e) {
			throw new RateDaoException("Blad przy odczycie tabeli Country", e);
		}
	}

	@Override
	public void save(Country t) throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			session.save(t);
			sessionCreator.closeSession(session);
		} catch (Exception e) {
			throw new RateDaoException("Blad przy zapisie Country", e);
		}

	}

	@Override
	public void update(long id, Country t) throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			Query query = session.createQuery(
					"update Country set country_name =:country_name, currency_id = :currency_id  where country_id = :id");
			query.setParameter("country_name", t.getName());
			query.setParameter("country_name", t.getCurrency().getId());
			query.setParameter("id", id);
			query.executeUpdate();
			sessionCreator.closeSession(session);
		} catch (Exception e) {
			throw new RateDaoException("Blad przy update Rate", e);
		}

	}

	@Override
	public void deleteById(long id) throws DaoException {
		try {
			Session session = sessionCreator.createSession();
			Country country = get(id);
			session.delete(country);
			sessionCreator.closeSession(session);
		} catch (Exception e) {
			throw new RateDaoException("Blad przy usuwaniu Country", e);
		}
	}
}
