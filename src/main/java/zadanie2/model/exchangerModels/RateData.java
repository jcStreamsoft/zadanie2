package zadanie2.model.exchangerModels;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import zadanie2.enums.CurrencyCode;

public class RateData {
	private LocalDate date;
	private BigDecimal rate;
	private CurrencyCode currencyCode;

	public RateData(LocalDate date, BigDecimal rate, CurrencyCode currencyCode) {
		super();
		this.date = date;
		this.rate = rate;
		this.currencyCode = currencyCode;
	}

	public LocalDate getDate() {
		return date;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public CurrencyCode getCurrencyCode() {
		return currencyCode;
	}

	@Override
	public String toString() {
		return "[date=" + date + ", rate=" + rate + ", currency=" + currencyCode + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(currencyCode, date, rate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RateData other = (RateData) obj;
		return currencyCode == other.currencyCode && Objects.equals(date, other.date)
				&& Objects.equals(rate, other.rate);
	}
}
