package com.panther.test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Test {


    /**
     * 获取网页内容
     */
    @org.junit.Test
    public void GetHtmlTest(){
        try {
            URL url = new URL("https://www.baidu.com");
            InputStream is = url.openStream(); //通过openStream方法获取资源的字节输入流
            //将字节输入流转换为字符输入流,如果不指定编码，中文可能会出现乱码
            InputStreamReader inputStreamReader = new InputStreamReader(is,"UTF-8");
            //为字符输入流添加缓冲，提高读取效率
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            // 读取数据
            String data = bufferedReader.readLine();
            while (data != null){
                System.out.println(data);
                data = bufferedReader.readLine();
            }

            bufferedReader.close();
            inputStreamReader.close();
            is.close();


            /**
             * 将数据写入文件
             */
            FileOutputStream fileOutputStream = new FileOutputStream("d:/abc.txt");
            byte[] bytes = "asdb".getBytes();
            fileOutputStream.write(bytes);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
