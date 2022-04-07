package zadanie2.model.dto;

public class CurrencyDto {

	private long id;
	private String code;

	public CurrencyDto() {
		super();
	}

	public CurrencyDto(long id, String code) {
		super();
		this.id = id;
		this.code = code;
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

	@Override
	public String toString() {
		return "CurrencyDto [id=" + id + ", code=" + code + "]";
	}

}
