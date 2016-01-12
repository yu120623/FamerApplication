package com.project.farmer.famerapplication.http;

/**
 * Created by Administrator on 2015/12/22.
 */
public class API {
    public static final String URL = "http://121.40.91.182:8080/mycff/api/";
    //public static final String URL = "http://192.168.1.15:8080/mycff/api/";
    public static final String PARAM_STR = "jsonStr";

    public static class API_URL {
        public static final String FARM_TOPIC_LIST = "farmTopicList";//首页精选列表
        public static final String FARM_TOPIC_INFO = "farmTopicQ";//专题介绍;
        public static final String FARM_TOPIC_PANICBUYING_LIST = "farmTopicPanicBuyingList";// 抢购
        public static final String FARM_TOPIC_COMMENT_LIST = "farmSetCommentList";//专题评论列表
        public static final String FARM_TOPIC_KONW = "farmStatementList";//专题须知
        public static final String FARM_AROUND_LIST = "farmAroundList";//首页周边列表
        public static final String CITY_LIST = "intoCitySearch";//城市列表
        public static final String FARMSET_LIST = "intoFarmTopic";//套餐列表
        public static final String FARM_INFO = "clickFarm";//农庄详细
        public static final String SEARCH_KEY_LIST = "showSearchKey";//搜索页面
        public static final String SEARCH_RESULT_LIST = "keySearch";//搜索结果
    }
}
