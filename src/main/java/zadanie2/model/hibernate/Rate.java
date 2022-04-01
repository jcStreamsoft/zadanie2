package zadanie2.model.hibernate;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@org.hibernate.annotations.NamedQueries({
		@org.hibernate.annotations.NamedQuery(name = "Rate_findById", query = "from Rate where rate_id = :id"),
//		@org.hibernate.annotations.NamedQuery(name = "Rate_findMaxRateBetweenDates", query = "from rate r\r\n"
//				+ "	where  (date between :dateStart AND :dateEnd ) AND currency_id = :id\r\n"
//				+ "	AND r.value =(select max(value) 	from rate \r\n"
//				+ "where (date between :dateStart1 AND :dateEnd1 ) AND currency_id = :id1)"),
//		@org.hibernate.annotations.NamedQuery(name = "Rate_findMinRateBetweenDates", query = "from rate r\r\n"
//				+ "	where  (date between :dateStart AND :dateEnd ) AND currency_id = :id\r\n"
//				+ "	AND r.value =(select min(value) 	from rate \r\n"
//				+ "where (date between :dateStart AND :dateEnd ) AND currency_id = :id)"),
		@org.hibernate.annotations.NamedQuery(name = "Rate_findByCurrencyIdAndDate", query = "from Rate where currency_id = :id AND date = :date") })

@org.hibernate.annotations.NamedNativeQueries(@org.hibernate.annotations.NamedNativeQuery(name = "Rate_findMostChangedRateBetweenDates", query = "select (max(r.value)- min(r.value)) as wynik , c.currency_code\r\n"
		+ "	from rate r join currency c on c.currency_id = r.currency_id\r\n"
		+ "	where r.date between :dateStart AND :dateEnd group by  c.currency_code\r\n"
		+ "	order by wynik desc	limit 1"))
@Entity
@Table(name = "Rate")
public class Rate {
	@Id
	@GeneratedValue
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
