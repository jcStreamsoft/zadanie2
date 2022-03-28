package test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import zadanie2.daos.CurrencyDao;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.model.hibernate.Currency;

public class CurrencyDaoTest {

	@Test
	public void shouldReturnCurrency_whenGivenExistingId() throws DaoException {
		// given
		CurrencyDao currencyDao = new CurrencyDao();
		long id = 1;
		String expected = "EUR";
		// when
		Currency result = currencyDao.get(id);
		// then
		assertEquals(expected, result.getCode());
	}

	@Test
	public void shouldReturnNull_whenGivenNotExistingId() throws DaoException {
		// given
		CurrencyDao currencyDao = new CurrencyDao();
		long id = 0;
		Currency expected = null;
		// when
		Currency result = currencyDao.get(id);
		// then
		assertEquals(expected, result);
	}
}
