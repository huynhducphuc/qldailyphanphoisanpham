package bean;

import java.io.Serializable;

public class City implements Serializable{
	private int cityId;
	private String cityName;

	public City() {

	}

	public City(String cityName) {
		this.cityName = cityName;
	}

	public City(int cityId, String cityName) {
		this.cityId = cityId;
		this.cityName = cityName;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", cityName=" + cityName + "]";
	}

}
