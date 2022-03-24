package zadanie2.enums;

public enum CurrencyCode {
	USD("usd"),CHF("chf"),AUD("aud"), EUR("eur");

	private String code;

	CurrencyCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
