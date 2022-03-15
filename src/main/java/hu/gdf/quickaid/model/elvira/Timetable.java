package hu.gdf.quickaid.model.elvira;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Timetable {
	public String type;
	public String starttime;
	public String start;
	public String destinationtime;
	public String destination;
	public String change;
	public String totaltime;
	public String distance;
	public String cost1st;
	public String cost2nd;
	@JsonProperty("class")
	public String myclass;
	public String class_name;
	public ArrayList<Detail> details;
	public TrainInfo train_info;
	public List<Service> services;

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public TrainInfo getTrain_info() {
		return train_info;
	}

	public void setTrain_info(TrainInfo train_info) {
		this.train_info = train_info;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDestinationtime() {
		return destinationtime;
	}

	public void setDestinationtime(String destinationtime) {
		this.destinationtime = destinationtime;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getTotaltime() {
		return totaltime;
	}

	public void setTotaltime(String totaltime) {
		this.totaltime = totaltime;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCost1st() {
		return cost1st;
	}

	public void setCost1st(String cost1st) {
		this.cost1st = cost1st;
	}

	public String getCost2nd() {
		return cost2nd;
	}

	public void setCost2nd(String cost2nd) {
		this.cost2nd = cost2nd;
	}

	public String getMyclass() {
		return myclass;
	}

	public void setMyclass(String myclass) {
		this.myclass = myclass;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public ArrayList<Detail> getDetails() {
		return details;
	}

	public void setDetails(ArrayList<Detail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Timetable [type=" + type + ", starttime=" + starttime + ", start=" + start + ", destinationtime="
				+ destinationtime + ", destination=" + destination + ", change=" + change + ", totaltime=" + totaltime
				+ ", distance=" + distance + ", cost1st=" + cost1st + ", cost2nd=" + cost2nd + ", myclass=" + myclass
				+ ", class_name=" + class_name + ", details=" + details + "]";
	}

}
