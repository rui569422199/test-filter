package personal.wrui.test.test_fillters.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;


public class ServerConnect {
    //定义IP和端口号是常量
    private static final String IP = "172.16.2.171";
    
    private static final int PORT = 20108;
    public static void main(String []args)
    { OutputStream stream =null;
    	ServerSocket socket=null;
		try {
//			InetSocketAddress address=InetSocketAddress.createUnresolved("127.0.0.1", 21345);
			socket = new ServerSocket(21345);
//			socket.bind(address);
			Socket client=socket.accept();
			stream = client.getOutputStream();
			stream.write("server".getBytes(Charset.forName("UTF-8")));
			stream.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
        }
        //进行流关闭处理，先进行判断，然后再关闭
        finally
        {
            try
            {
            	if(stream!=null) {
            		stream.close();
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