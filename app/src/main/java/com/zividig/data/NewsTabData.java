package com.zividig.data;

import java.util.ArrayList;

/**
 * 新闻页签的数据
 * Created by Administrator on 2016-03-05.
 */
public class NewsTabData {

    public int retcode;
    public NewsDetailList data;

    public class NewsDetailList{
        public String countcommenturl;
        public String more;
        public  ArrayList<NewsData> news;
        public String title;
        public  ArrayList<TopicData> topic;
        public  ArrayList<TopnewsData> topnews;

        @Override
        public String toString() {
            return "NewsDetailList{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    public class NewsData{
        public boolean comment;
        public String commentlist;
        public String commenturl;
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "NewsData{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public class TopicData{
        public String id;
        public String listimage;
        public String title;
        public String url;

        @Override
        public String toString() {
            return "TopicData{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public class TopnewsData{
        public boolean comment;
        public String commentlist;
        public String commenturl;
        public String id;
        public String pubdate;
        public String title;
        public String topimage;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "TopnewsData{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", topimage='" + topimage + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsTabData{" +
                "retcode=" + retcode +
                ", data=" + data +
                '}';
    }
}
