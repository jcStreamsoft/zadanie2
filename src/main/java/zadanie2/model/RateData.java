package zadanie2.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import zadanie2.enums.Currency;

public class RateData {
	private LocalDate date;
	private BigDecimal rate;
	private Currency currency;

	public RateData(LocalDate date, BigDecimal rate, Currency currency) {
		super();
		this.date = date;
		this.rate = rate;
		this.currency = currency;
	}

	public LocalDate getDate() {
		return date;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public Currency getCurrency() {
		return currency;
	}

	@Override
	public String toString() {
		return "[date=" + date + ", rate=" + rate + ", currency=" + currency + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(currency, date, rate);
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
		return currency == other.currency && Objects.equals(date, other.date) && Objects.equals(rate, other.rate);
	}
}
