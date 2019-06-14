package server;

import java.io.*;
import java.net.Socket;
import entity.*;
import handler.*;
import util.MessageUtil;

public class SocketThread extends Thread {
	Socket client;
	PrintStream out;
	BufferedReader in;
	MethodHandler handler;

	public SocketThread(Socket client) throws IOException {
		this.client = client;
		out = new PrintStream(client.getOutputStream());
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}

	
	@Override
	public void run() {	
		try {
			//1 读取RequestMessage
			MessageUtil util=new MessageUtil();	
			RequestMessage requestMessage= util.readMessage(in);
			//2 处理RequestMessage
			switch (requestMessage.getMethod()) {
			case "GET":
				handler = new GetHandler();		
				break;
			case "POST":
				handler = new PostHandler();
				break;
			default:
				break;
			}
			System.out.println("  用户请求: "+requestMessage.getUrl());
			ResponseMessage responseMessage=handler.handle(requestMessage);
			//3 发送ResponseMessage
			util.sendMessage(responseMessage, out);
			//4 关闭socket连接
			client.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	
}