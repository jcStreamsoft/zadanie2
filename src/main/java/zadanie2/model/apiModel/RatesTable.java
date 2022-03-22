package zadanie2.model.apiModel;

import java.util.ArrayList;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RatesTable {
	@JacksonXmlProperty(localName = "Table")
	char table;
	@JacksonXmlProperty(localName = "Country")
	String country;
	@JacksonXmlProperty(localName = "Currency")
	String currency;
	@JacksonXmlProperty(localName = "Symbol")
	String symbol;
	@JacksonXmlProperty(localName = "Code")
	String code;
	@JacksonXmlProperty(localName = "Rates")
	ArrayList<Rate> rates;

	public RatesTable() {
		super();
	}

	public char getTable() {
		return table;
	}

	public void setTable(char table) {
		this.table = table;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ArrayList<Rate> getRates() {
		return rates;
	}

	public void setRates(ArrayList<Rate> rates) {
		this.rates = rates;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
