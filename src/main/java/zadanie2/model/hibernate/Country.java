package zadanie2.model.hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@org.hibernate.annotations.NamedNativeQueries({
		@org.hibernate.annotations.NamedNativeQuery(name = Country.FIND_COUNTRIES_WITH_MORE_CURRENCIES, query = Country.FIND_COUNTRIES_WITH_MORE_CURRENCIES_QUERY) })

@SuppressWarnings("serial")
@Entity
@Table(name = "Country")
public class Country implements Serializable {
	// NAMED NATIVE QUEIRES
	public static final String FIND_COUNTRIES_WITH_MORE_CURRENCIES = "findCountriesWithMoreCurrencies";
	static final String FIND_COUNTRIES_WITH_MORE_CURRENCIES_QUERY = "select co.country_id,co.country_name from Country co\r\n"
			+ "join Country_Currency cc on cc.country_id = co.country_id\r\n"
			+ "group by co.country_id,co.country_name \r\n" + "having Count(cc.currency_id)>= 2";
	@Id
	@GeneratedValue
	@Column(name = "country_id")
	private long id;

	@Column(name = "country_name")
	private String name;
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "Country_Currency", joinColumns = { @JoinColumn(name = "Country_id") }, inverseJoinColumns = {
			@JoinColumn(name = "Currency_id") })
	Set<Currency> currencies = new HashSet<>();

	public Country(String name) {
		super();
		this.name = name;
	}

	public Country() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(Set<Currency> currencies) {
		this.currencies = currencies;
	}

	public void addCurrency(Currency currency) {
		this.currencies.add(currency);
	}

	public void removeCurrency(Currency currency) {
		this.currencies.remove(currency);
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + ", currency_id=" + currencies + "]";
	}

}
