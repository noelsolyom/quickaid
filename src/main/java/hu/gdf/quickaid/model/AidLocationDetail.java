package hu.gdf.quickaid.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class AidLocationDetail {

	private String address;
	private BigDecimal latitude;
	private BigDecimal longitude;
	@JsonInclude(value = Include.NON_NULL)
	private String key;

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "AidLocationDetail [address=" + address + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", key=" + key + "]";
	}

}
