package rss;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


/**
 * 
 * 
 * Title: Test.java<br>
 * Description: <br>
 * 
 */
public class Test {
    //http://www.163.com/rss/
    private static final String baidu = "http://news.baidu.com/n?cmd=1&class=civilnews&tn=rss";
    private static final String wangyi = "http://news.163.com/special/00011K6L/rss_newstop.xml";

    private static Map<String, String> wangyiChannel = new HashMap<String, String>();
    static{
        wangyiChannel.put("新闻", "http://news.163.com/special/00011K6L/");
        wangyiChannel.put("体育", "http://sports.163.com/special/00051K7F/");
        wangyiChannel.put("娱乐", "http://ent.163.com/special/00031K7Q/");
        wangyiChannel.put("视频", "http://v.163.com/special/008544NC/");
        wangyiChannel.put("科技", "http://tech.163.com/special/000944OI/");
        wangyiChannel.put("财经", "http://money.163.com/special/00252EQ2/");
        wangyiChannel.put("汽车", "http://auto.163.com/special/00081K7D/");
        wangyiChannel.put("数码", "http://tech.163.com/digi/special/00161K7K/");
        wangyiChannel.put("手机", "http://tech.163.com/mobile/special/001144R8/");
        wangyiChannel.put("女性", "http://lady.163.com/special/00261R8C/");
        wangyiChannel.put("房产", "http://sh.house.163.com/special/000741DO/");
        wangyiChannel.put("游戏", "http://game.163.com/special/003144N4/");
        wangyiChannel.put("读书", "http://book.163.com/special/0092451H/");
        wangyiChannel.put("媒体", "http://media.163.com/special/00762B70/");
        wangyiChannel.put("公益", "http://gongyi.163.com/special/009344MB/");
        wangyiChannel.put("校园", "http://daxue.163.com/special/00913J5N/");
        wangyiChannel.put("旅游", "http://travel.163.com/special/00061K7R/");
        wangyiChannel.put("教育", "http://edu.163.com/special/002944N7/");
        wangyiChannel.put("论坛", "http://bbs.163.com/special/001544OQ/");
        wangyiChannel.put("博客", "http://news.163.com/special/000144P0/");
        wangyiChannel.put("营销", "http://mkt.163.com/special/009044MP/");
    }
    

    
    
    private static void testJson() throws IOException, URISyntaxException{
        File f = new File("/rss/");
        System.out.println(f);
        System.out.println(f.exists());
//        Enumeration<URL> urls = Test.class.getClassLoader().getResources("");
//        JSONArray jsonArray = new JSONArray();
//        while(urls.hasMoreElements()){
//            URL url = urls.nextElement();
//            InputStream in = url.openStream();
//            
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            IOUtils.copy(in, out);
//            String jsonStr = new String(out.toByteArray(), Charsets.UTF_8);
//            IOUtils.closeQuietly(out);
//            IOUtils.closeQuietly(in);
//            JSONObject json = JSONObject.fromObject(jsonStr);
//            jsonArray.add(json);
//        }
//        System.out.println(jsonArray);
//        InputStream in = Test.class.getClassLoader().getResourceAsStream("rss/rss_163.json");
    }

    public static void main(String[] args) throws Exception {
//        test();
            
//            URL url = new URL("http://minecrack.fr.nf/mc/cloaksminecrackd/Jinngoo.png");
//            HttpURLConnection c = (HttpURLConnection) url.openConnection();
//            c.connect();
//            System.out.println("start");
//            InputStream in = c.getInputStream();
//            
//            FileOutputStream out = new FileOutputStream("e:/ab.png");
//            System.out.println("got");
//            IOUtils.copy(in, out);
        
//        readPng("C:/Users/Jinnan/Desktop/tett", "Jinngoo.png");
        
        
        BufferedImage in = ImageIO.read(new File("C:/Users/Jinnan/Desktop/tett/Jinngoo.png")); 
        BufferedImage out = getConvertedImage(in);
        
        ImageIO.write(out, "PNG", new File("C:/Users/Jinnan/Desktop/tett/output.png"));
    }
    
    /**
     * 将背景为黑色不透明的图片转化为背景透明的图片
     * @param image 背景为黑色不透明的图片（用555格式转化后的都是黑色不透明的）
     * @return 转化后的图片
     */
    private static BufferedImage getConvertedImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        // 采用带1 字节alpha的TYPE_4BYTE_ABGR，可以修改像素的布尔透明
        BufferedImage convertedImage = new BufferedImage(width * 8, height * 8, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = (Graphics2D) convertedImage.getGraphics();
//        g2D.drawImage(image, 0, 0, null);
        // 像素替换，直接把背景颜色的像素替换成0
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
//                convertedImage.setRGB(i, j, rgb);
                drawScale(convertedImage, i, j, 8, rgb);
            }
        }
        g2D.drawImage(convertedImage, 0, 0, null);
        return convertedImage;
    }
    
    private static void drawScale(BufferedImage image, int x, int y, int scale, int rgb){
        int startX = x * scale;
        int startY = y * scale;
        int endX = (x+1) * scale;
        int endY = (y+1) * scale;
        for(int i = startX; i < endX; i ++){
            for(int j = startY; j < endY; j ++){
//                System.out.println(i + "," + j + ":" + rgb);
                image.setRGB(i, j, rgb);
            }
        }
//        image.setRGB(x, y, rgb);
//        System.out.println(rgb);
    }
    
    public static void readPng(String path, String name) {  
        byte[] bs = getFileToByte(new File(path + "/" + name));  
        int start = 8;  
        int end = bs.length - 8;  
        int count = bs.length - 16;  
        for (int i = 0; i < count; i++) {  
            System.out.print(Integer.toHexString(bs[i]) + " ");  
            if (i != 0 && i % 27 == 0) {  
                System.out.println();  
            }  
        }  
        try {  
            File f = new File(path + "/output_" + name);  
            if (f.exists() == false) {  
                f.createNewFile();  
            }  
            FileOutputStream fos = new FileOutputStream(f);  
            fos.write(bs);  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException ie) {  
            ie.printStackTrace();  
        }  
    }  
    public static byte[] getFileToByte(File file) {  
        byte[] by = new byte[(int) file.length()];  
        try {  
            InputStream is = new FileInputStream(file);  
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();  
            byte[] bb = new byte[2048];  
            int ch;  
            ch = is.read(bb);  
            while (ch != -1) {  
                bytestream.write(bb, 0, ch);  
                ch = is.read(bb);  
                System.out.println("ch : " + ch);  
            }  
            by = bytestream.toByteArray();  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return by;  
    }  

}
