package com.qy.picupload;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 实现TCP图片上传客户端
 * 实现步骤:
 *      1.Socket套接字连接服务器
 *      2.通过Socket获取字节输出流,写图片
 *      3.使用自己的流对象,读取图片数据源
 *          FileInputStream
 *      4.读取图片,使用字节输出流,将图片写到服务器
 *          采用字节数组进行缓冲
 *      5.通过Socket套接字获取字节输入流
 *          读取服务器发回来的上传成功
 *      6.关闭资源
 * Created by Autumn on 2018/2/5.
 */
public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8000);
        //获取字节输出流,图片写到服务器
        OutputStream os = socket.getOutputStream();
        //创建字节输入流,读取本机上的数据源图片
        String filePath = "G:\\秋雨\\相机\\头像\\235301z2jhe8q63835h6z5.jpg";
        FileInputStream fis = new FileInputStream(filePath);
        //开始读写字节数组
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = fis.read(bytes)) != -1){
            os.write(bytes,0,len);
        }
        //文件读完时给服务器写终止序列
        socket.shutdownOutput();

        //获取字节输入流,读取服务器的上传成功
        InputStream inputStream = socket.getInputStream();
        len = inputStream.read(bytes);
        System.out.println(new String(bytes,0,len));
    }
}