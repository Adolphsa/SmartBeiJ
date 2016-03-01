package com.zividig.data;

import java.util.ArrayList;

/**
 * 网络分类信息的封装
 * Created by Administrator on 2016-03-01.
 */
public class MenuTitleData {

    public int retcode;
    public ArrayList<SlidingMenuData> data;

    //侧边栏标题
    public class SlidingMenuData{
        public ArrayList<MainMenuData> children;
        public String id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "SlidingMenuData{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    //新闻中心的11个子标题
    public class MainMenuData{
        public String id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "MainMenuData{" +
                    "title='" + title + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MenuTitleData{" +
                "retcode=" + retcode +
                ", data=" + data +
                '}';
    }
}
