package zadanie2;

import java.math.BigDecimal;
import java.math.MathContext;

public class CurrencyCalculator {

	public BigDecimal calculateToPln(BigDecimal value, BigDecimal rate) {
		MathContext m = new MathContext(25);
		BigDecimal result = value.divide(rate, m);
		return result;
	}

	public BigDecimal calculateFromPln(BigDecimal value, BigDecimal rate) {
		BigDecimal result = value.multiply(rate);
		return result;
	}
}
