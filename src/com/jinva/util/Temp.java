package com.jinva.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


import org.apache.commons.io.IOUtils;

public class Temp {

    
    public static void temp01() throws Exception{
        Temp.class.getClassLoader().getResourceAsStream("");
        URL url = Temp.class.getClassLoader().getResource("");
       
        File file = new File(url.toURI());
        String webinf = file.getParent();
        String webroot = file.getParentFile().getParent();
        String lib = webinf + File.separator + "lib";
        
        String jar = lib + File.separator + "hehe.jar";
        
        ZipFile zipFile = new ZipFile(jar);
        
        ZipInputStream in = new ZipInputStream(new FileInputStream(jar));
        
        
        for(ZipEntry zipEntry = in.getNextEntry(); zipEntry != null; zipEntry = in.getNextEntry()){
            if(zipEntry.getName().startsWith("view")){
                InputStream viewIn = zipFile.getInputStream(zipEntry);
                File outFile = new File(webroot + File.separator + zipEntry.getName());
                
                File dir = zipEntry.isDirectory() ? outFile : outFile.getParentFile();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                    
                if(!zipEntry.isDirectory()){
                    FileOutputStream viewOut = new FileOutputStream(outFile, false);
                    IOUtils.copy(viewIn, viewOut);
                    IOUtils.closeQuietly(viewOut);
                }
                IOUtils.closeQuietly(viewIn);
            }
        }
        
        in.close();
        zipFile.close();
        
        
        
        System.out.println(webroot);
    }
    
}
