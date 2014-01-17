package rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class RssLoader {

	public static RssChannel loadRss(String url) {
		InputStream in = null;
		HttpURLConnection connection = null;
		Document document = null;
		try {
			URL rss = new URL(url);
			connection = (HttpURLConnection) rss.openConnection();
			connection.connect();
			in = connection.getInputStream();
			SAXReader reader = new SAXReader();
			document = reader.read(in);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.close(connection);
		}

		return document == null ? null : build(document);
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
}
