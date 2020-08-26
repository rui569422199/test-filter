package personal.wrui.test.test_fillters.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ClientConnect {
    //定义IP和端口号是常量
    private static final String IP = "172.16.2.171";
    
    private static final int PORT = 20108;
    public static void main(String []args)
    {
    	Socket socket=null;
		try {
			socket = new Socket(IP,21345);
			socket.getOutputStream().write("client".getBytes(Charset.forName("UTF-8")));
//			socket.connect(InetSocketAddress.createUnresolved(IP,21345));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //从服务器端通过socket读取信息
        BufferedReader br = null;
        try
        {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //把读到的信息显示到TextArea中
            String str=null;
//            while (true)
//            {
                str = br.readLine();
                //建一个新的文本文档，用于存储从服务器读到的信息
                String date = sdf.format(new Date());;
                System.out.println(date+"\t"+str);
//            }
        } catch (IOException e){
            e.printStackTrace();
        }
        //进行流关闭处理，先进行判断，然后再关闭
        finally
        {
            try
            {
                if(br != null)
                {
                    br.close();
                    br =null;
                }
                if(socket != null)
                {
                    socket.close();
                    socket = null;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}