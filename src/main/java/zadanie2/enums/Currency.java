package zadanie2.enums;

public enum Currency {
	USD("usd"),CHF("chf"),AUD("aud"), EUR("eur");

	private String code;

	Currency(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
