package hu.gdf.quickaid.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class AidLocationDto {

	@JsonInclude(value = Include.NON_NULL)
	private String name;

	private AidLocationDetail details;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AidLocationDetail getDetails() {
		return details;
	}

	public void setDetails(AidLocationDetail details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "AidLocationDto [name=" + name + ", details=" + details + "]";
	}

}
