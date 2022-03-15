package hu.gdf.quickaid.model.elvira;

public class Service {

	public String icon;
	public int code;
	public String key;
	public String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Service [icon=" + icon + ", code=" + code + ", key=" + key + "]";
	}

}