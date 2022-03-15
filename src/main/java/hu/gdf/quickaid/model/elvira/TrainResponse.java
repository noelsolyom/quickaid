package hu.gdf.quickaid.model.elvira;

public class TrainResponse {

	private String startTime;
	private String start;
	private String destinationTime;
	private String destination;
	private String type;
	private String className;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDestinationTime() {
		return destinationTime;
	}

	public void setDestinationTime(String destinationTime) {
		this.destinationTime = destinationTime;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "TrainResponse [startTime=" + startTime + ", start=" + start + ", destinationTime=" + destinationTime
				+ ", destination=" + destination + ", type=" + type + ", className=" + className + "]";
	}

}
