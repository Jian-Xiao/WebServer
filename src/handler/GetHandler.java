package handler;
import entity.*;
import util.FileUtil;

public class GetHandler extends MethodHandler {

	private String handleType(String type) {
		if (type.equals("html") || type.equals("css"))
			type = "text/" + type;
		else if (type.equals("png") || type.equals("gif"))
			type = "image/" + type;
		else if (type.equals("jpg") )
			type = "image/" + "jpeg";
		return type;
	}

	private ResponseMessage generateSuccessMsg(String fileType, byte[] fileBuf) {
		ResponseMessage rpMsg = new ResponseMessage();
		rpMsg.setVersion("HTTP/1.0");
		rpMsg.setStatusCode("200");
		rpMsg.setStatusWord("OK");
		rpMsg.setHeader("MIME_version", "1.0");
		rpMsg.setHeader("Content_Type", fileType);
		rpMsg.setHeader("Content_Length", "" + fileBuf.length);
		rpMsg.setEntityBody(fileBuf);
		return rpMsg;
	}

	private ResponseMessage generateNotFoundMsg(String fileType, byte[] fileBuf) {
		ResponseMessage rpMsg = new ResponseMessage();
		rpMsg.setVersion("HTTP/1.0");
		rpMsg.setStatusCode("404");
		rpMsg.setStatusWord("NOT FOUND");
		rpMsg.setHeader("Content_Type", fileType);
		rpMsg.setHeader("Content_Length", "" + fileBuf.length);
		rpMsg.setEntityBody(fileBuf);
		return rpMsg;
	}

	@Override
	public ResponseMessage handle(RequestMessage message) {
		//��������Get
		String url =message.getUrl();
		if (url.contains("?")) {
			message.setUrl(url.substring(0, url.indexOf("?")));
			message.setEntityBody(url.substring(url.indexOf("?")+1));
			return new PostHandler().handle(message);
		}
		else if (url.equals("/")) {
			url="/index.html";//Ĭ��ֵ
		}
		
		FileUtil fileHandler = new FileUtil();
		//1 ��ȡ�ļ�·��
		String filePath = "html"+url;
		//2 ��ȡ�ļ�
		byte[] fileBuf = fileHandler.readFile(filePath);
		//3 ����ļ�����
		String fileType = handleType(filePath.substring(filePath.indexOf(".") + 1));
		//4 ���ɱ���
		ResponseMessage rpMsg;		
			//4.1����ļ�����
		if (fileBuf != null) {
			rpMsg = generateSuccessMsg(fileType, fileBuf);
			System.out.println("  �ļ��������: GET finish");
		} 
			//4.2����ļ�������,404����
		else {
			fileBuf = fileHandler.readFile("html/404.html");
			rpMsg = generateNotFoundMsg("text/html", fileBuf);
			System.out.println("  δ֪�ļ�: GET finish");
		}	
		//5 ���ر���
		return rpMsg;
	}
}
