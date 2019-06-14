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
		//带参数的Get
		String url =message.getUrl();
		if (url.contains("?")) {
			message.setUrl(url.substring(0, url.indexOf("?")));
			message.setEntityBody(url.substring(url.indexOf("?")+1));
			return new PostHandler().handle(message);
		}
		else if (url.equals("/")) {
			url="/index.html";//默认值
		}
		
		FileUtil fileHandler = new FileUtil();
		//1 获取文件路径
		String filePath = "html"+url;
		//2 读取文件
		byte[] fileBuf = fileHandler.readFile(filePath);
		//3 获得文件类型
		String fileType = handleType(filePath.substring(filePath.indexOf(".") + 1));
		//4 生成报文
		ResponseMessage rpMsg;		
			//4.1如果文件存在
		if (fileBuf != null) {
			rpMsg = generateSuccessMsg(fileType, fileBuf);
			System.out.println("  文件传输完成: GET finish");
		} 
			//4.2如果文件不存在,404界面
		else {
			fileBuf = fileHandler.readFile("html/404.html");
			rpMsg = generateNotFoundMsg("text/html", fileBuf);
			System.out.println("  未知文件: GET finish");
		}	
		//5 返回报文
		return rpMsg;
	}
}
