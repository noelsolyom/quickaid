package hu.gdf.quickaid.model;

public class CityDto {

	private String city;

	public CityDto(String city) {
		super();
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "CityDto [city=" + city + "]";
	}

}
