package com.project.farmer.famerapplication.http;

/**
 * Created by Administrator on 2015/12/22.
 */
public class API {
    public static final String URL = "http://192.168.1.19:8080/mycff/api/";
    public static final String PARAM_STR = "jsonStr";
    public static class API_URL{
        public static final String FARM_TOPIC_LIST = "farmTopicList";//首页精选列表
        public static final String FARM_TOPIC_INFO = "farmTopicCheap";//专题介绍;
        public static final String FARM_TOPIC_PANICBUYING_LIST="farmTopicPanicBuyingList";//套餐列表
        public static final String FARM_TOPIC_COMMENT_LIST = "farmSetCommentList";//专题评论列表
        public static final String FARM_TOPIC_KONW = "farmStatementList";//专题须知
    }
}
