package entity;

import java.util.HashMap;

public class RequestMessage {
	String method;
	String url;
	String version;
	HashMap<String, String> headers = new HashMap<>();
	String entityBody;


	public void setRequestLine(String requestLine) {
		// fetch firstLine
		String[] requestline = requestLine.split(" ");
		method = requestline[0];
		url = requestline[1];
		version = requestline[2];
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVersion() {
		return version;
	}

	public String getHeader(String header) {
		return headers.get(header);
	}

	public void setHeader(String header, String value) {
		headers.put(header, value);
	}

	public String getEntityBody() {
		return entityBody;
	}

	public void setEntityBody(String entityBody) {
		this.entityBody = entityBody;
	}
}
