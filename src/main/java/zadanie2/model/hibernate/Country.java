package zadanie2.model.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Country")
public class Country implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "country_id")
	private int id;

	@Column(name = "country_name")
	private String name;
	@OneToOne
	@JoinColumn(name = "currency_id")
	private Currency currency_id;

	public Country(String name) {
		super();
		this.name = name;
	}

	public Country() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Currency getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(Currency currency_id) {
		this.currency_id = currency_id;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + ", currency_id=" + currency_id + "]";
	}

}
