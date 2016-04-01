package com.egceo.app.myfarm.http;

public class API {
    public static final String URL = "http://121.41.112.28:8080/wdnz/api/";//正式
    //public static final String URL = "http://121.40.91.182:8080/mycff/api/";
    //public static final String URL = "http://192.168.1.110:8080/wdnz/api/";
    public static final String PARAM_STR = "jsonStr";

    public static class API_URL {
        public static final String FARM_TOPIC_LIST = "farmTopicList";//首页精选列表
        public static final String FARM_TOPIC_INFO = "farmTopicQ";//专题介绍;
        public static final String FARM_TOPIC_PANICBUYING_LIST = "farmTopicPanicBuyingList";// 抢购
        public static final String FARM_TOPIC_COMMENT_LIST = "farmSetCommentList";//专题评论列表
        public static final String FARM_TOPIC_KONW = "farmStatementList";//专题须知
        public static final String FARM_AROUND_LIST = "farmAroundList";//首页周边列表
        public static final String FARM_HOT_LIST = "farmRecommend";//首页农庄推荐
        public static final String CITY_LIST = "intoCitySearch";//城市列表
        public static final String FARMSET_LIST = "intoFarmTopic";//套餐列表
        public static final String FARM_INFO = "clickFarm";//农庄详细
        public static final String SEARCH_KEY_LIST = "showSearchKey";//搜索页面
        public static final String SEARCH_RESULT_LIST = "keySearch";//搜索结果
        public static final String ORDER_CHOOSE_TIME = "orderTimeChoose";//下单选择时间
        public static final String ORDER_CHOOSE_INFO = "orderInfoChoose";//下单选择信息
        public static final String ORDER_CHANGE_FARM_ITEM_DATE = "changeFarmItemDate";//下单选择订单项目时间
        public static final String PERSON_ORDER = "personOrder";//我的订单
        public static final String GEN_ORDER = "placeAnOrder";//下单
        public static final String CONTACT_LIST = "chooseContact";//联系人列表
        public static final String ORDER_INFO = "orderInfo";//订单详细
        public static final String PAY_MENT = "payment";//支付
        public static final String DEL_ORDER = "deleteOrder";//删除订单
        public static final String ADD_CONTACT = "saveContact";//增加联系人
        public static final String DELETE_CONTACT = "deleteContact";//删除联系人
        public static final String BACK_ORDER = "chargeback";//退单
        public static final String BACK_ORDER_REASON = "examine";//退单理由
        public static final String ORDER_CODE = "qrCode";//订单消费码
        public static final String SEND_SMS = "sendVCode";//发送验证码
        public static final String REGISTER_USER = "register";//注册
        public static final String LOGIN = "appLogin";//登陆
        public static final String FORGET_PWD = "forgetPassword";//重置密码
        public static final String WECHAT_ORDER = "weChatPayData";//微信付款
        public static final String PAY_BANK = "bankTn";//银联支付
        public static final String SYS_INFO = "sysInfoList";//系统消息
        public static final String FUND_LIST = "consumptionInfo";//基金列表
        public static final String MY_FAVOURITE = "collectInfo";//收藏列表
        public static final String CANCEL_REFUND = "cancelChargeback";//取消退单
        public static final String FAVOURITE = "collectAdd";//收藏
        public static final String COLLECT_CANCEL = "collectCancel";//取消收藏
        public static final String QUICK_PAY = "quickPaySn";//快速买单
        public static final String USER_EDIT = "personEdit";//用户修改信息
        public static final String DEL_MSG = "deleteSysInfo";//删除用户信息
        public static final String SEND_COMMENT ="saveComment";//评论
        public static final String SAVE_RES_COMMENT ="saveResource";//评论图片
        public static final String READ_PACKAGES = "redPackets";//查找红包
        public static final String GET_PACKAGES = "grabRedEnvelope";//抢红包
        public static final String UPDATE = "appUpdata";//更新
    }
}
