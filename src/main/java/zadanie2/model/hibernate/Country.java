package zadanie2.model.hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@org.hibernate.annotations.NamedQueries({
		@org.hibernate.annotations.NamedQuery(name = Country.FIND_COUNTRIES_WITH_MORE_CURRENCIES, query = Country.FIND_COUNTRIES_WITH_MORE_CURRENCIES_QUERY) })

@SuppressWarnings("serial")
@Entity
@Table(name = "Country")
public class Country implements Serializable {
	// NAMED NATIVE QUEIRES
	public static final String FIND_COUNTRIES_WITH_MORE_CURRENCIES = "findCountriesWithMoreCurrencies";
	static final String FIND_COUNTRIES_WITH_MORE_CURRENCIES_QUERY = "from Country country  where size(country.currencies)>=2";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "country_id")
	private long id;

	@Column(name = "country_name")
	private String name;
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "Country_Currency", joinColumns = { @JoinColumn(name = "country_id") }, inverseJoinColumns = {
			@JoinColumn(name = "currency_id") })
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
