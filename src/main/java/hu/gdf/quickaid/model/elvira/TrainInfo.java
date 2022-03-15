package hu.gdf.quickaid.model.elvira;

import java.util.List;

public class TrainInfo {

	public String href;
	public String url;
	public String get_url;
	public String code;
	public String text;
	public String info;
	public String type;
	public boolean is_local_transport;
	public Object delay_info;
	public Object havaria_reason;
	public String vsz_code;
	public List<Service> services;

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGet_url() {
		return get_url;
	}

	public void setGet_url(String get_url) {
		this.get_url = get_url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isIs_local_transport() {
		return is_local_transport;
	}

	public void setIs_local_transport(boolean is_local_transport) {
		this.is_local_transport = is_local_transport;
	}

	public Object getDelay_info() {
		return delay_info;
	}

	public void setDelay_info(Object delay_info) {
		this.delay_info = delay_info;
	}

	public Object getHavaria_reason() {
		return havaria_reason;
	}

	public void setHavaria_reason(Object havaria_reason) {
		this.havaria_reason = havaria_reason;
	}

	public String getVsz_code() {
		return vsz_code;
	}

	public void setVsz_code(String vsz_code) {
		this.vsz_code = vsz_code;
	}

	@Override
	public String toString() {
		return "TrainInfo [href=" + href + ", url=" + url + ", get_url=" + get_url + ", code=" + code + ", text=" + text
				+ ", info=" + info + ", type=" + type + ", is_local_transport=" + is_local_transport + ", delay_info="
				+ delay_info + ", havaria_reason=" + havaria_reason + ", vsz_code=" + vsz_code + "]";
	}

}
