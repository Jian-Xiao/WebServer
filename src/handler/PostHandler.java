package handler;

import entity.RequestMessage;
import entity.ResponseMessage;
import util.FileUtil;

public class PostHandler extends MethodHandler {

	@Override
	public ResponseMessage handle(RequestMessage message) {
		FileUtil fileHandler = new FileUtil();
		// 1 获取文件路径
		String filePath = "html" + message.getUrl();
		// 2 获得提交数据
		String data = message.getEntityBody();
		// 3 读取文件
		byte[] fileBuf = fileHandler.readFile(filePath);
		// 4 获得文件类型
		String fileType = handleType(filePath.substring(filePath.indexOf(".") + 1));

		// 5 验证数据，并生成报文
		ResponseMessage rpMsg;

		// 5.1 输入信息有误，302重定向
		if (!verifyData(data) && !verifyContent(data)) {
			fileBuf = fileHandler.readFile("html/errorInfo.html");
			rpMsg = generateRedirectionMsg("text/html", fileBuf);
			System.out.println("  输入信息错误: POST finish");
		}
		// 5.2 输入信息正确
		else {
			rpMsg = generateSuccessMsg(fileType, fileBuf);
			System.out.println("  文件传输完成: POST finish");
		}
		return rpMsg;
	}

	public boolean verifyData(String data) {
		String user = "^[a-z0-9A-Z]+$";
		String pwd = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
		String email = "^[a-zA-Z0-9_-]+%40[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$";
		String[] paras = data.split("&|=");
		if (paras.length == 6 && paras[1].matches(user) && paras[3].matches(email) && paras[5].matches(pwd))
			return true;
		return false;
	}

	public boolean verifyContent(String searchContent) {
		String content = "^[a-z0-9A-Z]+$";
		searchContent = searchContent.substring(searchContent.indexOf("=") + 1);
		if (searchContent.matches(content))
			return true;
		return false;
	}

	private String handleType(String type) {
		if (type.equals("html") || type.equals("css"))
			type = "text/" + type;
		else if (type.equals("png") || type.equals("gif"))
			type = "image/" + type;
		else if (type.equals("jpg"))
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

	private ResponseMessage generateRedirectionMsg(String fileType, byte[] fileBuf) {
		ResponseMessage rpMsg = new ResponseMessage();
		rpMsg.setVersion("HTTP/1.0");
		rpMsg.setStatusCode("302");
		rpMsg.setStatusWord("Temporarily Moved");
		rpMsg.setHeader("Content_Type", fileType);
		rpMsg.setHeader("Content_Length", "" + fileBuf.length);
		rpMsg.setEntityBody(fileBuf);
		return rpMsg;
	}
}
