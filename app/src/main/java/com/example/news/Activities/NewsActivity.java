package com.example.news.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.Adapters.NewsAdapter;
import com.example.news.Models.News;
import com.example.news.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class NewsActivity extends AppCompatActivity {
    RecyclerView recyclerViewNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerViewNews = findViewById(R.id.recycler_view_news);
       new AsyncTask<String, Void, List<News>>() {
            @Override
            protected List<News> doInBackground(String... url) {
                List<News> newsList = null;
                Log.e("gggggg", "error: ");
                try {
                   newsList =  parse(getInputStreamFromUrl(url[0]));
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                return newsList;
            }

            @Override
            protected void onPostExecute(List<News> news) {
                super.onPostExecute(news);
                Log.e("ffffffff", "onPostExecute: ");
                lastThing(news);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"https://feeds.alwatanvoice.com/ar/palestine.xml");
    }

    private InputStream getInputStreamFromUrl(String url) throws IOException {
        Log.e("rrrrrrrrr", "error: ");
        try {
            Log.e("rrrrrrrrr", "error: ");
            URL newsUrl = new URL(url);
            URLConnection urlConnection = newsUrl.openConnection();
            urlConnection.connect();
            return urlConnection.getInputStream();
        }catch (Exception e){
            Log.e("rrrrrrrrr", "error: ");
            return null;
        }

    }

    private List<News> parse(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        List<News> newsList = new ArrayList<>();
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(inputStream);
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getElementsByTagName("item");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String title = "", link = "", pubDate = "",imageUrl = "";
            NodeList childNodeList = node.getChildNodes();
            for (int j = 0; j < childNodeList.getLength(); j++) {
                Node childNode = childNodeList.item(j);
                String childNodeName = childNode.getNodeName();
                switch (childNodeName) {
                    case "title":
                        title = childNode.getTextContent();
                        break;
                    case "link":
                        link = childNode.getTextContent();
                        break;
                    case "pubDate":
                        pubDate = childNode.getTextContent();
                        break;
                    case "media:content":
                        NamedNodeMap namedNodeMap = childNode.getAttributes();
                        imageUrl =  namedNodeMap.getNamedItem("url").getTextContent();
                        break;
                }
            }
            News news = new News(title,imageUrl,pubDate,link);
            newsList.add(news);
        }
        return newsList;
    }

    private void lastThing(List<News> news){
        NewsAdapter newsAdapter = new NewsAdapter(getApplicationContext(),news);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerViewNews.setAdapter(newsAdapter);
    }

}
