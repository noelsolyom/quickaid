package hu.gdf.quickaid.model;

import org.springframework.http.HttpStatus;

public class BaseResponse {

	private Object data;
	private HttpStatus httpStatus;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	@Override
	public String toString() {
		return "BaseResponse [data=" + data + ", httpStatus=" + httpStatus + "]";
	}

}
