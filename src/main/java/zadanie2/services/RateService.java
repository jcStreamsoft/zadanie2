package zadanie2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.interfaces.daos.Dao;
import zadanie2.model.hibernate.Rate;

@Service
public class RateService {

	@Autowired
	private Dao<Rate> rateDao;

	public Rate get(long id) throws DaoException {
		return rateDao.get(id);
	}

	public void deleteById(long id) throws DaoException {
		rateDao.deleteById(id);
	}

	public void save(Rate rate) throws DaoException {
		rateDao.save(rate);
	}

	public void update(long id, Rate rate) throws DaoException {
		rateDao.update(id, rate);
	}
}