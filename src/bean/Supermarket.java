package bean;

import java.io.Serializable;

public class Supermarket implements Serializable {
	private int id;
	private int districtId;
	private String name;
	private String address;
	private String phone;
	private String email;
	private String website;
	private String longitude;
	private String lagtitude;

	public Supermarket() {
	}

        public Supermarket(int districtId, String name, String address,
			String phone, String email, String website, String longitude,
			String lagtitude) {
		this.districtId = districtId;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.website = website;
		this.longitude = longitude;
		this.lagtitude = lagtitude;
	}
        
	public Supermarket(int id, int districtId, String name, String address,
			String phone, String email, String website, String longitude,
			String lagtitude) {
		this.id = id;
		this.districtId = districtId;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.website = website;
		this.longitude = longitude;
		this.lagtitude = lagtitude;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLagtitude() {
		return lagtitude;
	}

	public void setLagtitude(String lagtitude) {
		this.lagtitude = lagtitude;
	}

	@Override
	public String toString() {
		return "Supermarket [id=" + id + ", districtId=" + districtId
				+ ", name=" + name + ", address=" + address + ", phone="
				+ phone + ", email=" + email + ", website=" + website
				+ ", longitude=" + longitude + ", lagtitude=" + lagtitude + "]";
	}

}
