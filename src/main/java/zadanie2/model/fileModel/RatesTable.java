package zadanie2.model.fileModel;

import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RatesTable {
	@JacksonXmlProperty(localName = "Table")
	char table;
	@JacksonXmlProperty(localName = "No")
	String no;
	@JacksonXmlProperty(localName = "EffectiveDate")
	LocalDate effectiveDate;
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

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = LocalDate.parse(effectiveDate);
	}

	public ArrayList<Rate> getRates() {
		return rates;
	}

	public void setRates(ArrayList<Rate> rates) {
		this.rates = rates;
	}
}
