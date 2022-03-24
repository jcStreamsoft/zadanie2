package zadanie2.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie2.enums.CurrencyCode;

public class Request {

	private LocalDate date;
	private BigDecimal value;
	private CurrencyCode currencyCode;
	private String dataFormat;

	private Request() {
	}

	public LocalDate getDate() {
		return date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public CurrencyCode getCurrencyCode() {
		return currencyCode;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getCurrencyCodeString() {
		return this.currencyCode.getCode();
	}

	public static Builder getBuilder(BigDecimal value, CurrencyCode currencyCode) {
		return new Builder(value, currencyCode);
	}

	public static class Builder {
		private LocalDate date;
		private BigDecimal value;
		private CurrencyCode currencyCode;

		public Builder(BigDecimal value, CurrencyCode currencyCode) {
			this.value = value;
			this.currencyCode = currencyCode;
		}

		public Builder date(LocalDate date) {
			this.date = date;
			return this;
		}

		public Request build() {
			Request request = new Request();
			request.value = this.value;
			request.currencyCode = this.currencyCode;
			if (date == null) {
				request.date = LocalDate.now();
			} else {
				request.date = this.date;
			}

			return request;
		}
	}
}
