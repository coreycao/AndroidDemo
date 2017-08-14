package com.ucar.sycao.retrofit.model;

import java.util.List;

/**
 * Created by sycao on 2017/8/14.
 */

public class GankRandom {

    /**
     * error : false
     * results : [{"_id":"56cc6d29421aa95caa708237","createdAt":"2016-01-29T06:48:22.240Z","desc":"可折叠/展开布局与平滑的动画","publishedAt":"2016-03-01T12:09:30.687Z","type":"Android","url":"https://github.com/ubdc/CollapseLayout","used":true,"who":"Jason"},{"_id":"57456ead6776594b08b2d705","createdAt":"2016-05-25T17:21:49.734Z","desc":"Java 依赖注入标准（JSR-330）简介","publishedAt":"2016-05-26T11:52:42.430Z","source":"chrome","type":"Android","url":"http://blog.csdn.net/DL88250/article/details/4838803#_Inject_6802068962399098_56394_26207626942120477","used":true,"who":"LHF"},{"_id":"584a2164421aa963efd90daf","createdAt":"2016-12-09T11:13:40.718Z","desc":"完成度很高的一款 ePub Android 阅读器","images":["http://img.gank.io/807a2383-1388-47f1-97b9-d804681d5671","http://img.gank.io/b94efe6c-1a6e-4cc5-bd99-2283b1835110"],"publishedAt":"2016-12-09T11:33:12.481Z","source":"chrome","type":"Android","url":"https://github.com/FolioReader/FolioReader-Android","used":true,"who":"代码家"},{"_id":"5775c21b421aa97a566cc16a","createdAt":"2016-07-01T09:06:35.743Z","desc":"Float Button 圆形进度条效果","images":["https://cloud.githubusercontent.com/assets/2931932/14588785/b15cb84a-04da-11e6-9771-b0e54b6a6201.png"],"publishedAt":"2016-07-01T11:06:20.244Z","source":"chrome","type":"Android","url":"https://github.com/DmitryMalkovich/circular-with-floating-action-button","used":true,"who":"代码家"},{"_id":"56cc6d26421aa95caa707e62","createdAt":"2015-11-17T02:43:32.371Z","desc":" Android 热补丁动态修复框架小结","publishedAt":"2015-11-17T04:00:01.757Z","type":"Android","url":"http://blog.csdn.net/lmj623565791/article/details/49883661","used":true,"who":"MVP"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 56cc6d29421aa95caa708237
         * createdAt : 2016-01-29T06:48:22.240Z
         * desc : 可折叠/展开布局与平滑的动画
         * publishedAt : 2016-03-01T12:09:30.687Z
         * type : Android
         * url : https://github.com/ubdc/CollapseLayout
         * used : true
         * who : Jason
         * source : chrome
         * images : ["http://img.gank.io/807a2383-1388-47f1-97b9-d804681d5671","http://img.gank.io/b94efe6c-1a6e-4cc5-bd99-2283b1835110"]
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private String source;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
