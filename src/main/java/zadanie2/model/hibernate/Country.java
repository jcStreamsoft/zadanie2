package zadanie2.model.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Country")
public class Country implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "country_id")
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "country_name")
	private String name;

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

}
