package test;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie2.connectors.CachedConnection;
import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.model.RateData;
import zadanie2.model.Request;

public class CachedConnectionTest {

	@Test
	public void shouldSavedRateData_whenFindingSavedRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.now();
		BigDecimal value = new BigDecimal("1");
		CurrencyCode currencyCode = CurrencyCode.USD;
		RateData expected = new RateData(date, value, currencyCode);
		Request request = Request.getBuilder(value, currencyCode).date(date).build();
		CachedConnection cache = new CachedConnection();
		cache.saveRateData(expected);
		// when
		RateData result = cache.getRateData(request);

		// then
		assertEquals(result, expected);
	}

	@Test
	public void shouldReturnNull_whenFindingRequestWithNewDate() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.now();
		LocalDate newDate = LocalDate.now().minusDays(1);
		BigDecimal value = new BigDecimal("1");
		CurrencyCode currencyCode = CurrencyCode.USD;
		RateData rateData = new RateData(date, value, currencyCode);
		Request request = Request.getBuilder(value, currencyCode).date(newDate).build();
		CachedConnection cache = new CachedConnection();
		cache.saveRateData(rateData);
		// when
		RateData result = cache.getRateData(request);

		// then
		assertNotEquals(result, rateData);
	}

	@Test
	public void shouldReturnDiffrentRateDate_whenFindingRequestWithNewCurrency() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.now();
		BigDecimal value = new BigDecimal("1");
		CurrencyCode currencyCode = CurrencyCode.USD;
		CurrencyCode newCurrency = CurrencyCode.EUR;
		RateData rateData = new RateData(date, value, currencyCode);
		Request request = Request.getBuilder(value, newCurrency).date(date).build();
		CachedConnection cache = new CachedConnection();
		cache.saveRateData(rateData);
		// when
		RateData result = cache.getRateData(request);

		// then
		assertNotEquals(result, rateData);
	}

	@Test
	public void shouldFindExpectedRateDate_whenFindingRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.now();
		LocalDate olderDate = LocalDate.now().minusDays(1);
		BigDecimal value = new BigDecimal("1");
		CurrencyCode currencyCode = CurrencyCode.USD;
		RateData expected = new RateData(olderDate, value, currencyCode);
		Request request = Request.getBuilder(value, currencyCode).date(date).build();
		CachedConnection cache = new CachedConnection();
		cache.saveRateData(expected);
		// when
		RateData result = cache.getRateData(request, olderDate);

		// then
		assertEquals(result, expected);
	}
}
