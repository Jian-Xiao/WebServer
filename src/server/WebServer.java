package server;
import java.net.ServerSocket;
import java.net.Socket;

import util.FileUtil;

public class WebServer {
	public static void main(String[] args) throws Exception {
		 //1 �������ļ���ȡ�˿ں�
		 int port = Integer.parseInt((new FileUtil()).readPort());
		 
		 //2 ��ʼ��������׽���
		 ServerSocket server = new ServerSocket(port);
		 System.out.println("����������");
		 
		 //3 �ͻ����׽��ּ���
		 Socket client= null;
		 while(true) {
			 //4 ��������
			 client = server.accept();
			 System.out.println("�ͻ�����ɹ� IP:"+client.getInetAddress());
		     new SocketThread(client).start();//5 ���ݴ��䴦��
		 }
	}
}
