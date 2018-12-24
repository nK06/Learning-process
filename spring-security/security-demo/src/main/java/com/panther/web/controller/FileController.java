package com.panther.web.controller;

import com.panther.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController {

    private String folder = "C:/devSoftware/IDEA/IDEA_workspace/github_Repository/spring-security/security-demo/src/main/java/com/panther/web";

    @PostMapping                   // 参数名需要匹配
    public FileInfo upload(MultipartFile file) throws IOException {
        
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        File localFile = new File(folder, System.currentTimeMillis()+".txt");

        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id , HttpServletRequest servletRequest, HttpServletResponse servletResponse){
        try(InputStream inputStream = new FileInputStream(new File(folder,id+".txt"));
                OutputStream outputStream = servletResponse.getOutputStream();){
            servletResponse.setContentType("application/x-download");
            // 定义下载之后的文件名
            servletResponse.addHeader("Content-Disposition","attachment;filename=test.txt");

            // 将输入流复制到输出流
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
