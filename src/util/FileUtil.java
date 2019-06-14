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
			// 根据响应报文格式设置
			File file = new File(filePath);
			DataInputStream writer = new DataInputStream(new FileInputStream(file));
			buff = new byte[(int) file.length()];
			writer.readFully(buff); // 读取文内容到buf数组中
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
