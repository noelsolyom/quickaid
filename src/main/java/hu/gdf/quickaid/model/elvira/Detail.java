package hu.gdf.quickaid.model.elvira;

import java.util.ArrayList;

public class Detail {

	public String from;
	public String dep;
	public String dep_real;
	public String platform;
	public TrainInfo train_info;
	public ArrayList<Service> services;
	public ArrayList<String> original_way;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getDep_real() {
		return dep_real;
	}

	public void setDep_real(String dep_real) {
		this.dep_real = dep_real;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public TrainInfo getTrain_info() {
		return train_info;
	}

	public void setTrain_info(TrainInfo train_info) {
		this.train_info = train_info;
	}

	public ArrayList<Service> getServices() {
		return services;
	}

	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}

	public ArrayList<String> getOriginal_way() {
		return original_way;
	}

	public void setOriginal_way(ArrayList<String> original_way) {
		this.original_way = original_way;
	}

	@Override
	public String toString() {
		return "Detail [from=" + from + ", dep=" + dep + ", dep_real=" + dep_real + ", platform=" + platform
				+ ", train_info=" + train_info + ", services=" + services + ", original_way=" + original_way + "]";
	}

}