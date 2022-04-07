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
	private Rate rate = new Rate();

	public Rate get() throws DaoException {
		return rateDao.get(36795);
	}
}