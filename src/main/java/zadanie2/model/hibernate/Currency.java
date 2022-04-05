package zadanie2.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@org.hibernate.annotations.NamedNativeQueries({
		@org.hibernate.annotations.NamedNativeQuery(name = Currency.GET_MOST_CHANGED_BETWEEN_DATES, query = Currency.GET_MOST_CHANGED_BETWEEN_DATES_QUERY) })
@Entity
@Table(name = "Currency")
public class Currency {
	// NAMED NATIVE QUEIRES
	public static final String GET_MOST_CHANGED_BETWEEN_DATES = "findMostChangedRateBetweenDates";
	static final String GET_MOST_CHANGED_BETWEEN_DATES_QUERY = "SELECT (max(r.value)- min(r.value)) as wynik , c.currency_code\r\n"
			+ "	from rate r join currency c on c.currency_id = r.currency_id where r.date between :dateStart AND :dateEnd group by  c.currency_code\r\n"
			+ "	order by wynik desc	limit 1";
	@Id
	@GeneratedValue
	@Column(name = "currency_id")
	private long id;

	@Column(name = "currency_code")
	private String code;

	@ManyToMany(mappedBy = "currencies")
	private Set<Country> countries = new HashSet<>();

	public Currency() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<Country> getCountries() {
		return countries;
	}

	public void setCountries(Set<Country> countries) {
		this.countries = countries;
	}

	public void addCurrency(Country country) {
		this.countries.add(country);
	}

	public void removeCurrency(Country country) {
		this.countries.remove(country);
	}

	@Override
	public String toString() {
		return "Currency [id=" + id + ", code=" + code + "]";
	}

}
