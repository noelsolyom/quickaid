package hu.gdf.quickaid.model;

import java.math.BigDecimal;

public class AidLocationDetail {

	private String address;
	private BigDecimal latitude;
	private BigDecimal longitude;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "AidLocationDetail [address=" + address + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
