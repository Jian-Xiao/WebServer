package server;
import java.net.ServerSocket;
import java.net.Socket;

import util.FileUtil;

public class WebServer {
	public static void main(String[] args) throws Exception {
		 //1 从配置文件读取端口号
		 int port = Integer.parseInt((new FileUtil()).readPort());
		 
		 //2 初始化服务端套接字
		 ServerSocket server = new ServerSocket(port);
		 System.out.println("服务器启动");
		 
		 //3 客户端套接字监听
		 Socket client= null;
		 while(true) {
			 //4 建立连接
			 client = server.accept();
			 System.out.println("客户接入成功 IP:"+client.getInetAddress());
		     new SocketThread(client).start();//5 数据传输处理
		 }
	}
}
