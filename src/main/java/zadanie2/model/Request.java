package zadanie2.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie2.enums.Currency;

public class Request {

	private LocalDate date;
	private BigDecimal value;
	private Currency currency;
	private String dataFormat;

	private Request() {
	}

	public LocalDate getDate() {
		return date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public Currency getCurrency() {
		return currency;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getCurrencyCode() {
		return this.currency.getCode();
	}

	public static Builder getBuilder(BigDecimal value, Currency currency) {
		return new Builder(value, currency);
	}

	public static class Builder {
		private LocalDate date;
		private BigDecimal value;
		private Currency currency;

		public Builder(BigDecimal value, Currency currency) {
			this.value = value;
			this.currency = currency;
		}

		public Builder date(LocalDate date) {
			this.date = date;
			return this;
		}

		public Request build() {
			Request request = new Request();
			request.value = this.value;
			request.currency = this.currency;
			if (date == null) {
				request.date = LocalDate.now();
			} else {
				request.date = this.date;
			}

			return request;
		}
	}
}
