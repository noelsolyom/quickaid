package hu.gdf.quickaid.model;

public class AidLocationDto {

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
