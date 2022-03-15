package hu.gdf.quickaid.model.elvira;

import java.util.ArrayList;

public class ElviraResponse {

	public ArrayList<Timetable> timetable;
	public String date;
	public String route;

	public ArrayList<Timetable> getTimetable() {
		return timetable;
	}

	public void setTimetable(ArrayList<Timetable> timetable) {
		this.timetable = timetable;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "Root [timetable=" + timetable + ", date=" + date + ", route=" + route + "]";
	}

}
