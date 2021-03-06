package zadanie2.model.hibernate;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@org.hibernate.annotations.NamedQueries({
		@org.hibernate.annotations.NamedQuery(name = Rate.GET_BY_ID, query = Rate.GET_BY_ID_QUERY),
		@org.hibernate.annotations.NamedQuery(name = Rate.GET_MAX_BETWEEN_DATES, query = Rate.GET_MAX_BETWEEN_DATES_QUERY),
		@org.hibernate.annotations.NamedQuery(name = Rate.GET_MIN_BETWEEN_DATES, query = Rate.GET_MIN_BETWEEN_DATES_QUERY),
		@org.hibernate.annotations.NamedQuery(name = Rate.GET_NUMBER_TOP_RATES_FOR_CURRENCY, query = Rate.GET_NUMBER_TOP_RATES_FOR_CURRENCY_QUERY),
		@org.hibernate.annotations.NamedQuery(name = Rate.GET_NUMBER_BOTTOM_RATES_FOR_CURRENCY, query = Rate.GET_NUMBER_BOTTOM_RATES_FOR_CURRENCY_QUERY),
		@org.hibernate.annotations.NamedQuery(name = Rate.GET_BY_DATE_AND_CURRENCY_ID, query = Rate.GET_BY_DATE_AND_CURRENCY_ID_QUERY),
		@org.hibernate.annotations.NamedQuery(name = Rate.GET_MOST_CHANGED_CURRENCY_ID_BETWEEN_DATES, query = Rate.GET_MOST_CHANGED_CURRENCY_ID_BETWEEN_DATES_QUERY) })

@Entity
@Table(name = "Rate", uniqueConstraints = {
		@UniqueConstraint(name = "UniqueDateCurrencyCode", columnNames = { "date", "currency_id" }) })
public class Rate {
	public static final String GET_BY_ID = "findById";
	static final String GET_BY_ID_QUERY = "FROM Rate WHERE rate_id = :id";
	public static final String GET_MAX_BETWEEN_DATES = "findMaxRateBetweenDates";
	static final String GET_MAX_BETWEEN_DATES_QUERY = "from Rate where (date between :dateStart AND :dateEnd ) AND currency_id = :id order by value desc";
	public static final String GET_MIN_BETWEEN_DATES = "findMinRateBetweenDates";
	static final String GET_MIN_BETWEEN_DATES_QUERY = "from Rate where (date between :dateStart AND :dateEnd ) AND currency_id = :id order by value asc";
	public static final String GET_BY_DATE_AND_CURRENCY_ID = "findByCurrencyIdAndDate";
	static final String GET_BY_DATE_AND_CURRENCY_ID_QUERY = "from Rate where currency_id = :id AND date = :date";
	public static final String GET_NUMBER_TOP_RATES_FOR_CURRENCY = "getTopRatesForCurrency";
	static final String GET_NUMBER_TOP_RATES_FOR_CURRENCY_QUERY = "from Rate  where currency_id = :id ORDER BY  value desc";
	public static final String GET_NUMBER_BOTTOM_RATES_FOR_CURRENCY = "getBottomRatesForCurrency";
	static final String GET_NUMBER_BOTTOM_RATES_FOR_CURRENCY_QUERY = "from Rate  where currency_id = :id ORDER BY  value asc";
	public static final String GET_MOST_CHANGED_CURRENCY_ID_BETWEEN_DATES = "findMostChangedRateBetweenDates";
	static final String GET_MOST_CHANGED_CURRENCY_ID_BETWEEN_DATES_QUERY = "from Rate r where r.date between :dateStart AND :dateEnd group by  r.currency.id "
			+ "order by (max(r.value)- min(r.value)) desc";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "rate_id")
	private long id;

	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "date")
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "currency_id")
	private Currency currency;

	public Rate(BigDecimal value, LocalDate date, Currency currency) {
		super();
		this.value = value;
		this.date = date;
		this.currency = currency;
	}

	public Rate() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Rate [id=" + id + ", value=" + value + ", date=" + date + ", currency=" + currency + "]";
	}

}
