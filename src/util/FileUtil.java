package util;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtil {
	String configTxtPath="PortConfig.txt";
	public byte[] readFile(String filePath) {
		byte buff[]=null;
		try {
			// ������Ӧ���ĸ�ʽ����
			File file = new File(filePath);
			DataInputStream writer = new DataInputStream(new FileInputStream(file));
			buff = new byte[(int) file.length()];
			writer.readFully(buff); // ��ȡ�����ݵ�buf������
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buff;
	}
	
	public String readPort() {
		return new String(readFile(configTxtPath));
	}
}
