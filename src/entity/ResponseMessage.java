package entity;

import java.util.HashMap;
import java.util.Map.Entry;

public class ResponseMessage {
	String version;
	String statusCode;
	String statusWord;
	HashMap<String, String> headers = new HashMap<>();
	byte[] entityBody;

	public void setVersion(String version) {
		this.version = version;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public void setStatusWord(String statusWord) {
		this.statusWord = statusWord;
	}

	public void setHeader(String header, String value) {
		headers.put(header, value);
	}

	public void setEntityBody(byte[] entityBody) {
		this.entityBody = entityBody;
	}
	
	public byte[] getEntityBody() {
		return entityBody;
	}

	public String getTotal() {
		String headerContent = "";
		for (Entry<String, String> entry : headers.entrySet()) {
			headerContent += entry.getKey() + ": " + entry.getValue() + "\n";
		}
		return version + " " + statusCode + " " + statusWord + "\n" + headerContent +"\n";
	}

}
