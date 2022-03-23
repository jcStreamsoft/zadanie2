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

@Entity
@Table(name = "Rate")
public class Rate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rate_id")
	private int id;

	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "date")
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "currency_id")
	private Currency currency;

	public Rate() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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