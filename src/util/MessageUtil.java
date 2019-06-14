package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import entity.RequestMessage;
import entity.ResponseMessage;

public class MessageUtil {
	public RequestMessage readMessage(BufferedReader in) throws IOException {
		// fetch requestLine
		RequestMessage rqMsg = new RequestMessage();
		rqMsg.setRequestLine(in.readLine());

		// fetch headers
		String line = "";
		while (!(line = in.readLine()).equals("")) {
			String header = line.substring(0, line.indexOf(" ") - 1);
			String value = line.substring(line.indexOf(" ") + 1);
			rqMsg.setHeader(header, value);
		}

		// fetch entityBody
		String entityBody = "";
		if (rqMsg.getHeader("Content-Length") != null) {
			int len = Integer.parseInt((rqMsg.getHeader("Content-Length")));
			char[] buf = new char[len];
			in.read(buf, 0, len);
			entityBody = new String(buf);
		} else if (rqMsg.getHeader("Transfer-Encoding") != null
				&& rqMsg.getHeader("Transfer-Encoding").equals("chunked")) {
			byte[] data = new byte[1024];
			int i = 0;
			int curByte = in.read();
			while (curByte != -1) {
				data[i] = (byte) curByte;
				i++;
				curByte = in.read();
			}
			entityBody = new String(data, 0, i);
		}
		rqMsg.setEntityBody(entityBody);
		return rqMsg;
	}

	public void sendMessage(ResponseMessage rpMsg, PrintStream out) throws IOException {
			out.print(rpMsg.getTotal());
			if(rpMsg.getEntityBody()!=null)
				out.write(rpMsg.getEntityBody());
			out.flush();
	}
}
