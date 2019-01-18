package com.panther.socket;

import java.io.*;
import java.net.Socket;

/**
 * 客户端
 */
public class SocketCommunicationClient {
            //1、创建客户端Socket，指定服务器地址和端口

            public static void main(String[] args){
                try {
                    Socket socket = new Socket("localhost", 12580);
                    //2、获取输出流，向服务器端发送信息
                    OutputStream outputStream = socket.getOutputStream();

                    //将输出流包装成打印流
                    PrintWriter printWriter = new PrintWriter(outputStream);
                    printWriter.write("用户名：admin 密码：123");
                    printWriter.flush();
                    socket.shutdownOutput();

                    //3、获取输入流，并读取服务器端的响应信息
                    InputStream inputStream = socket.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    String info = null;
                    while ((info = bufferedReader.readLine())!=null){
                        System.out.println("服务器端响应："+info);
                    }

            bufferedReader.close();
            inputStream.close();
            printWriter.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
