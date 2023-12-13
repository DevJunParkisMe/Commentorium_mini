package project.pnu.config;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class JsoupConnection {
	public Connection getJsoupConnection(String url) throws Exception {
		return Jsoup.connect(url);
	}

}
