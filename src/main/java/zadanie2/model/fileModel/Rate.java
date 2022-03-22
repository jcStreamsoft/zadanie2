package zadanie2.model.fileModel;

import java.math.BigDecimal;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Rate {

	@JacksonXmlProperty(localName = "Code")
	String code;
	@JacksonXmlProperty(localName = "Currency")
	String currency;
	@JacksonXmlProperty(localName = "Mid")
	BigDecimal mid;
	@JacksonXmlProperty(localName = "Country")
	String country;
	@JacksonXmlProperty(localName = "Symbol")
	int symbol;

	public Rate() {
		super();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = new BigDecimal(mid);
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getSymbol() {
		return symbol;
	}

	public void setSymbol(int symbol) {
		this.symbol = symbol;
	}

	public void setMid(BigDecimal mid) {
		this.mid = mid;
	}

}
