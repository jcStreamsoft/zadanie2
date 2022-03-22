package test;


import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;

import org.testng.annotations.Test;

import zadanie2.CurrencyCalculator;

public class CurrencyCalculatorTest {

	@Test
	public void shouldReturnCorrectValue_whenInputCorrectOnCalculateTFromPln() {
		// given
		CurrencyCalculator calc = new CurrencyCalculator();
		BigDecimal value = new BigDecimal("2");
		BigDecimal rate = new BigDecimal("3.5346");
		BigDecimal expected = new BigDecimal("7.0692");

		// when
		BigDecimal result = calc.calculateFromPln(value, rate);

		// then
		assertEquals(result, expected);
	}

	@Test
	public void shouldReturnCorrectValue_whenInputCorrectOnCalculateToPln() {
		// given
		CurrencyCalculator calc = new CurrencyCalculator();
		BigDecimal value = new BigDecimal("2");
		BigDecimal rate = new BigDecimal("3.5346");
		BigDecimal expected = new BigDecimal("0.5658348893792791263509308");
		// when
		BigDecimal result = calc.calculateToPln(value, rate);
		// then
		assertEquals(result, expected);
	}
}
