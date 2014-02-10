import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

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
    
    private static void test() throws IOException, DocumentException {
        URL url = new URL(baidu);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in = connection.getInputStream();

        SAXReader reader = new SAXReader();
        Document document = reader.read(in);

        RssChannel channel = build(document);
        
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(System.out, format);
        writer.write(document);
        
        System.out.println(channel.size());

        IOUtils.closeQuietly(in);
        IOUtils.close(connection);

    }

    @SuppressWarnings("unchecked")
    private static RssChannel build(Document document) {
        Element rss = document.getRootElement();
        Element channel = rss.element("channel");
        RssChannel rssChannel = buildChannel(channel);
        List<Element> items = channel.elements("item");
        for (int i = 0, size = items.size(); i < size; i++) {
            RssItem rssItem = buildItem(items.get(i));
            rssChannel.addItem(rssItem);
        }
        return rssChannel;
    }

    private static RssItem buildItem(Element item) {
        RssItem rssItem = new RssItem();
        rssItem.setTitle(item.element("title").getText());
        rssItem.setLink(item.element("link").getText());
        rssItem.setDescription(item.element("description").getText());
        return rssItem;
    }

    private static RssChannel buildChannel(Element channel) {
        RssChannel rssChannel = new RssChannel();
        rssChannel.setTitle(channel.element("title").getText());
        rssChannel.setLink(channel.element("link").getText());
        rssChannel.setDescription(channel.element("description").getText());
        return rssChannel;
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
