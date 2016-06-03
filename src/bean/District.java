package bean;

import java.io.Serializable;

public class District implements Serializable {
	private int districtId;
	private String districtName;
	private int cityId;

        public District(String districtName, int cityId) {
		this.districtName = districtName;
		this.cityId = cityId;
	}
        
	public District(int districtId, String districtName, int cityId) {
		this.districtId = districtId;
		this.districtName = districtName;
		this.cityId = cityId;
	}

	public District() {
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	@Override
	public String toString() {
		return "District [districtId=" + districtId + ", districtName="
				+ districtName + ", cityId=" + cityId + "]";
	}

}
