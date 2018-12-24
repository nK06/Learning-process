package com.panther.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于TCP 协议的Socket 通信，实现用户登录。
 */
public class SocketCommunicationServer {


    public static void main(String[] args){
        try {
            //1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
            ServerSocket serverSocket = new ServerSocket(12580);

            //2、调用accept()方法开始监听，等待客户端的连接
            Socket socket = serverSocket.accept();

            //3、获取输入流，并读取客户端信息
            InputStream inputStream = socket.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String info = null;
            while ((info = bufferedReader.readLine()) != null){
                System.out.println("服务端收到了客户端消息："+info);
            }
            //关闭输入流
            socket.shutdownInput();

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);

            printWriter.write("欢迎");
            printWriter.flush();


            printWriter.close();
            outputStream.close();
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
            serverSocket.close();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
