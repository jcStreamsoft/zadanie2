package test;

import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie2.connectors.fileConnection.FileConnection;
import zadanie2.enums.CurrencyCode;
import zadanie2.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie2.model.RateData;
import zadanie2.model.Request;
import zadanie2.parsers.fileParsers.FileJsonParser;

public class FileConnectionTest {

	@Test
	public void shouldReturnRateData_whenFindingApiRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.parse("2022-03-07");
		BigDecimal rate = new BigDecimal("4.5722");
		BigDecimal value = new BigDecimal("1");
		CurrencyCode currencyCode = CurrencyCode.USD;
		RateData expected = new RateData(date, rate, currencyCode);
		Request request = Request.getBuilder(value, currencyCode).date(date).build();
		FileConnection file = new FileConnection(new FileJsonParser(), "dane/fileArrayJson.txt");
		// when
		RateData result = file.getRateData(request);
		// then
		assertTrue(result.equals(expected));
	}

	@Test
	public void shouldReturnTheSameRateData_whenFindingOlderRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.parse("2022-03-06");
		LocalDate olderDate = LocalDate.parse("2022-03-04");
		BigDecimal value = new BigDecimal("1");
		BigDecimal rate = new BigDecimal("4.3910");
		CurrencyCode currencyCode = CurrencyCode.USD;
		RateData expected = new RateData(olderDate, rate, currencyCode);
		Request request = Request.getBuilder(value, currencyCode).date(date).build();
		FileConnection file = new FileConnection(new FileJsonParser(), "dane/fileArrayJson.txt");
		// when
		RateData result = file.getRateData(request, olderDate);
		// then
		assertTrue(result.equals(expected));
	}
}
