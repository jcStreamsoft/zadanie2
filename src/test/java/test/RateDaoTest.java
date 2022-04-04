package test;

import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import zadanie2.daos.CurrencyDao;
import zadanie2.daos.RateDao;
import zadanie2.exceptions.CreatingSessionException;
import zadanie2.exceptions.daoExceptions.DaoException;
import zadanie2.model.hibernate.Currency;
import zadanie2.model.hibernate.Rate;

public class RateDaoTest {
	SessionFactory sessionFactory;

	@BeforeTest
	public void startUp() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}

	@Test
	public void shouldSaveGetAndDeleteRate_whenGivenCorrectData() throws DaoException, CreatingSessionException {
		// given
		RateDao rateDao = new RateDao(sessionFactory);
		CurrencyDao currencyDao = new CurrencyDao(sessionFactory);
		Currency currency = currencyDao.get(1);
		Rate rate = new Rate(new BigDecimal("10.00000000000000000000"), LocalDate.parse("2022-03-25"), currency);
		long id = 0;
		// when
		rateDao.save(rate);

		Rate expectedFindBy = rateDao.getRateByDateAndCurrencyCode(currency, LocalDate.parse("2022-03-25"));

		id = expectedFindBy.getId();
		rateDao.deleteById(id);
		Rate rateAfterDelete = rateDao.get(id);
		// then
		assertEquals(rate.getId(), expectedFindBy.getId());
		assertEquals(rate.getValue(), expectedFindBy.getValue());
		assertEquals(rate.getDate(), expectedFindBy.getDate());
		assertEquals(rate.getCurrency().getCode(), expectedFindBy.getCurrency().getCode());
		assertEquals(rate.getCurrency().getId(), expectedFindBy.getCurrency().getId());
		assertEquals(null, rateAfterDelete);
	}

	@Test
	public void shouldReturnNull_whenIdDontExist() throws DaoException, CreatingSessionException {
		// given
		RateDao rateDao = new RateDao(sessionFactory);
		long id = 0;
		Rate expected = null;
		// when
		Rate result = rateDao.get(id);
		// then
		assertEquals(expected, result);
	}

	@Test
	public void shouldSaveGetUpdateAndDeleteRate_whenGivenCorrectData() throws DaoException, CreatingSessionException {
		// given
		RateDao rateDao = new RateDao(sessionFactory);
		CurrencyDao currencyDao = new CurrencyDao(sessionFactory);
		Currency currency = currencyDao.get(1);
		Rate rate = new Rate(new BigDecimal("10.00000000000000000000"), LocalDate.parse("2022-03-25"), currency);
		long id = 0;
		// when
		rateDao.save(rate);

		Rate expectedFindBy = rateDao.getRateByDateAndCurrencyCode(currency, LocalDate.parse("2022-03-25"));

		id = expectedFindBy.getId();
		rateDao.deleteById(id);
		Rate rateAfterDelete = rateDao.get(id);
		// then
		assertEquals(rate.getId(), expectedFindBy.getId());
		assertEquals(rate.getValue(), expectedFindBy.getValue());
		assertEquals(rate.getDate(), expectedFindBy.getDate());
		assertEquals(rate.getCurrency().getCode(), expectedFindBy.getCurrency().getCode());
		assertEquals(rate.getCurrency().getId(), expectedFindBy.getCurrency().getId());
		assertEquals(null, rateAfterDelete);
	}
}
