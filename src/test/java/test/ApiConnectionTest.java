package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie2.connectors.apiConnection.ApiConnection;
import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.model.exchangerModels.RateData;
import zadanie2.model.exchangerModels.Request;
import zadanie2.parsers.apiParsers.ApiJsonParser;

public class ApiConnectionTest {
	@Test
	public void shouldCorrectRateFromDate_whenFindingApiRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.parse("2002-01-04");
		BigDecimal rate = new BigDecimal("3.9383");
		BigDecimal value = new BigDecimal("1");
		CurrencyCode currencyCode = CurrencyCode.USD;
		RateData expected = new RateData(date, rate, currencyCode);
		Request request = Request.getBuilder(value, currencyCode).date(date).build();
		ApiConnection api = new ApiConnection(new ApiJsonParser());
		// when
		RateData result = api.getRateData(request, request.getDate());
		// then
		assertTrue(result.equals(expected));
	}

	@Test
	public void shouldReturnNull_whenFindingOlderRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.parse("2002-01-05");
		BigDecimal value = new BigDecimal("1");
		CurrencyCode currencyCode = CurrencyCode.USD;

		Request request = Request.getBuilder(value, currencyCode).date(date).build();
		ApiConnection api = new ApiConnection(new ApiJsonParser());
		// when
		RateData result = api.getRateData(request, date);
		// then
		assertEquals(result, null);
	}
}
