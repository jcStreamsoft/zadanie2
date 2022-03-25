package zadanie2.enums;

public enum CurrencyCode {
	AUD("aud"), ATS("ats"), BEF("bef"), CZK("czk"), DKK("dkk"), EEK("eek"), FIM("fim"), FRF("frf"), GRD("grd"),
	ESP("esp"), NLG("nlg"), IEP("iep"), JPY("jpy"), CAD("cad"), LUF("luf"), NOK("nok"), PTE("pte"), EUR("eur"),
	USD("usd"), CHF("chf"), SEK("sek"), HUF("huf"), GBP("gbp"), ITL("itl"), XDR("xdr"), THB("thb"), HKD("hkd"),
	NZD("nzd"), SGD("sgd"), UAH("uah"), ISK("isk"), HRK("hrk"), RON("ron"), BGN("bgn"), TRY("try"), ILS("ils"),
	CLP("clp"), PHP("php"), MXN("mxn"), ZAR("zar"), BRL("brl"), MYR("myr"), RUB("rub"), IDR("idr"), INR("inr"),
	KRW("krw"), CNY("cny"), LVL("lvl"), CYP("cyp"), SKK("skk"), MTL("mtl"), LTL("ltl"), SIT("sit");

	private String code;

	CurrencyCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
