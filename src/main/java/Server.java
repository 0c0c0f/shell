import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * @author Binxu - Silic
 */
public class Server {
    public static void main(String[] args) throws Exception {
        //开放服务端10000端口
        ServerSocket ss = new ServerSocket(1234);
        //阻塞等待
        Socket s = ss.accept();
        //读取客户端传过来的数据
        BufferedReader bufIn = new BufferedReader(new InputStreamReader(s
                .getInputStream()));
        //向客户端传送数据
        BufferedWriter bufOut = new BufferedWriter(new OutputStreamWriter(s
                .getOutputStream()));
        String line = null;
        //向客户端返回执行命令后结果
        while ((line = bufIn.readLine()) != null) {
            bufOut.write(ToServerName(line));
            bufOut.newLine();
            bufOut.flush();
        }
        s.close();
        ss.close();
    }

    private static String ToServerName(String cmd) throws IOException {
        StringBuffer sb = new StringBuffer();
        //判断系统是否是windows
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            //执行命令
            Process p = Runtime.getRuntime().exec("cmd.exe  /c " + cmd);
            //读取命令正确执行后的流
            BufferedReader buff = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            //读取错误命令执行后的流
            BufferedReader beff = new BufferedReader(new InputStreamReader(p
                    .getErrorStream()));
            String Me;
            //添加到一个StringBuffer 中以便一次性返回
            while ((Me = buff.readLine()) != null || (Me = beff.readLine()) != null) {
                sb.append(Me + "@@");
            }
        } else {
            //执行命令过程同上理
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader buff = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            BufferedReader beff = new BufferedReader(new InputStreamReader(p
                    .getErrorStream()));
            String Me;
            while ((Me = buff.readLine()) != null || (Me = beff.readLine()) != null) {
                sb.append(Me + "@@");
            }
        }
        //返回命令执行后结果。
        return sb.toString();
    }
}