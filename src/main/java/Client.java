import java.io.*;
import java.net.*;
/**
 * @author Binxu -Silic
 */
public class Client {
    //连接信息
    public static final String ip = "x";
    public static final int port = 1234;

    public static void main(String[] args) throws Exception {
        //连接目标
        Socket s = new Socket(ip, port);
        System.out.print("$:");
        //获取输入命令
        BufferedReader bufr =
                new BufferedReader(new InputStreamReader(System.in));
        //向服务端写数据
        BufferedWriter bufOut =
                new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        //读取服务端返回数据
        BufferedReader bufIn =
                new BufferedReader(new InputStreamReader(s.getInputStream()));

        String line = null;
        //接受服务端返回数据并显示
        while((line = bufr.readLine()) != null)
        {
            if("exit".equals(line))
                break;
            bufOut.write(line);
            bufOut.newLine();
            bufOut.flush();
            String str = bufIn.readLine().replaceAll("@@","\n");
            System.out.print(str);
            System.out.print("$:");
        }
        bufr.close();
        s.close();
    }
}