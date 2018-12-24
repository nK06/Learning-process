package com.panther.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {


    public static void main(String[] args){
        CopyFile copyFile = new CopyFile();

        String pathOld = "c:/a.txt";

        String pathNew = "c:/b.txt";

        copyFile.copyFile(pathOld,pathNew);
    }


    /**
     * 文件复制
     * @param filePath_old
     * @param filePath_new
     */
    public void copyFile(String filePath_old, String filePath_new){
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {

            // 根据path路径实例化一个输入流的对象
            fileInputStream = new FileInputStream(filePath_old);

            //2. 返回这个输入流中可以被读的剩下的bytes字节的估计值；
            int size = fileInputStream.available();

            //3. 根据输入流中的字节数创建byte数组；
            byte[] bytes = new byte[size];

            //4.把数据读取到数组中；
            fileInputStream.read(bytes);

            fileOutputStream = new FileOutputStream(filePath_new);

            //5、把byte数组输出；
            fileOutputStream.write(bytes);

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
