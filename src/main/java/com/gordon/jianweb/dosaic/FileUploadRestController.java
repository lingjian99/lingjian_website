package com.gordon.jianweb.dosaic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;
 
@Controller
public class FileUploadRestController {
 
    private String jpegFile;
    public void setImageFile(String file){
        jpegFile=file; 
    }
    public String getImageFile(){
        return jpegFile;
    }


    /**
     * Location to save uploaded files on server
     */
 
 
    /*
     * single file upload in a request
     */
    @RequestMapping("/fileupload")
    public void uploadFile(@RequestParam("multipartFile") MultipartFile uploadfile) {
 
        if (uploadfile.isEmpty()) {
            System.out.println("please select a file!!!");
        }
 
        try {
 

            byte[] bytes = uploadfile.getBytes();
            
            jpegFile = uploadfile.getOriginalFilename();
            
            Path path = Paths.get(getRealPath() + jpegFile);
            Files.write(path, bytes);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String getRealPath()
    {
        //C:\Users\Jian Ling\source\repos\Dosaic\dosaic\src\main\resources\static\images
        String b_path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";

        File dir = new File(b_path);
            if(!dir .exists()) dir.mkdirs();
        
        return b_path;
    }
//"c:\Users\Jian Ling\source\repos\Dosaic\dosaic\src\main\resources\static\images\2.jpg"
    String getJpegMeta()
    {
        String str;
        File file = new File(getRealPath() + jpegFile);

        StringBuilder jpegExif=new StringBuilder();
        try{
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                   str = tag.toString();
                   jpegExif.append(str);   
                       
                }
                break;
            }

        }
        catch (Exception e){
            
        }
        str=jpegExif.toString();
        return str;    
    }

       
     @RequestMapping(value = "/exif")
	public void JpegExif(HttpServletRequest request, HttpServletResponse response)throws IOException {
        
        
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        try{
            BufferedReader reader = request.getReader();
            while((line=reader.readLine())!=null){
                stringBuffer.append(line);

                break;
            }
        }
        catch(Exception e){
        
        }
        
        //String str1 = (String)request.getHeader("1st");
        //String str2 = (String)request.getHeader("2nd");
        
        PrintWriter out = response.getWriter();

        out.write("{\"MetaDate\": \"" + getJpegMeta() + "\", \n\r\"File\": \"./images/" + jpegFile +".jpg\"}");
        out.flush();
        dosaic(out, getRealPath() + jpegFile);
        out.close();

    }
    void dosaic(PrintWriter out, String file)
    {
        DosaicImage di = new DosaicImage(file);
        di.dosaic();

    }



}