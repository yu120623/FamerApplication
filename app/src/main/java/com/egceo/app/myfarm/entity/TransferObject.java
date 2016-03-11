package com.egceo.app.myfarm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class TransferObject implements Serializable {
    private Integer pageNumber;

    private String key;

    private Map<String,String> map;

    private List<FarmTopicModel> farmTopicModels;

    private List<FarmModel> farmModels;

    private ActivityModel activityModel;

    private List<ActivityModel> activityModels;

    private String activityId;

    private FarmModel farmModel;
    private List<String> UUIDs;

    private String LBSValue;

    private String farmTopicAliasId;

    private FarmSetModel farmSetModel;

    private List<FarmSetModel> farmSetModels;

    private String farmSetAliasId;

    private String commentCount;

    private List<Sysinfo> sysinfos;

    private String farmSetIsExistence;

    private List<CommentModel> commentModels;

    private String favorableRate;

    private String score;

    private List<FarmStatementModel> farmStatementModels;
    private List<aa53458768RecordModel> aa53458768RecordModels;
    private WalletModel walletModel;
    private String sysinfoId;

    private Double farmLatitude;

    private QRModel qrCodeModel;

    private Double farmLongitude;

    private String type;

    private String farmAliasId;

    private String cityCode;

    private String cityCodeName;

    private FarmQuickPayModel farmQuickPayModel;

    private List<CodeModel> codeModels;
    private List<CollectModel> collectModels;

    private String totalNum;

    private List<Contact> contacts;
    private String orderSn;

    private Integer copies;

    private String tn;

    private String contactId;

    private Contact contact;

    private List<Resource> resources;

    private CodeModel codeModel;
    private CommentModel commentModel;
    private Date date;
    private List<Date> dates;

    private String userAliasId;

    private UserProfile userProfile;

    private SMSObject smsObject;

    private AaSubjectLogin aaSubjectLogin;

    private List<OrderDateModel> orderDateModels;
    private String collectStatus;

    private RecommendTagModel recommendTagModel;
    private List<SearchModel> searchModels;
    private SearchModel searchModel;
    private String farmItemAliasId;
    private List<FarmStatement> farmStatements;
    private List<OrderModel> orderModels;
    private OrderModel orderModel;
    private Message message;
    private String name;
    private RefundRequestModel refundRequestModel;

    private String phoneNumber;
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<OrderModel> getOrderModels() {
        return orderModels;
    }

    public void setOrderModels(List<OrderModel> orderModels) {
        this.orderModels = orderModels;
    }

    public List<SearchModel> getSearchModels() {
        return searchModels;
    }

    public SearchModel getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(SearchModel searchModel) {
        this.searchModel = searchModel;
    }

    public void setSearchModels(List<SearchModel> searchModels) {
        this.searchModels = searchModels;
    }

    public RecommendTagModel getRecommendTagModel() {
        return recommendTagModel;
    }

    public void setRecommendTagModel(RecommendTagModel recommendTagModel) {
        this.recommendTagModel = recommendTagModel;
    }

    public CodeModel getCodeModel() {
        return codeModel;
    }

    public void setCodeModel(CodeModel codeModel) {
        this.codeModel = codeModel;
    }

    public FarmSetModel getFarmSetModel() {
        return farmSetModel;
    }

    public void setFarmSetModel(FarmSetModel farmSetModel) {
        this.farmSetModel = farmSetModel;
    }

    public List<FarmSetModel> getFarmSetModels() {
        return farmSetModels;
    }

    public void setFarmSetModels(List<FarmSetModel> farmSetModels) {
        this.farmSetModels = farmSetModels;
    }

    public String getFarmTopicAliasId() {
        return farmTopicAliasId;
    }

    public void setFarmTopicAliasId(String farmTopicAliasId) {
        this.farmTopicAliasId = farmTopicAliasId;
    }

    public List<FarmModel> getFarmModels() {
        return farmModels;
    }

    public void setFarmModels(List<FarmModel> farmModels) {
        this.farmModels = farmModels;
    }

    public List<FarmTopicModel> getFarmTopicModels() {
        return farmTopicModels;
    }

    public void setFarmTopicModels(List<FarmTopicModel> farmTopicModels) {
        this.farmTopicModels = farmTopicModels;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getFarmSetAliasId() {
        return farmSetAliasId;
    }

    public void setFarmSetAliasId(String farmSetAliasId) {
        this.farmSetAliasId = farmSetAliasId;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public List<CommentModel> getCommentModels() {
        return commentModels;
    }

    public void setCommentModels(List<CommentModel> commentModels) {
        this.commentModels = commentModels;
    }

    public String getFavorableRate() {
        return favorableRate;
    }

    public void setFavorableRate(String favorableRate) {
        this.favorableRate = favorableRate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<FarmStatement> getFarmStatements() {
        return farmStatements;
    }

    public void setFarmStatements(List<FarmStatement> farmStatement) {
        this.farmStatements = farmStatement;
    }

    public Double getFarmLatitude() {
        return farmLatitude;
    }

    public void setFarmLatitude(Double farmLatitude) {
        this.farmLatitude = farmLatitude;
    }

    public Double getFarmLongitude() {
        return farmLongitude;
    }

    public void setFarmLongitude(Double farmLongitude) {
        this.farmLongitude = farmLongitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityCodeName() {
        return cityCodeName;
    }

    public void setCityCodeName(String cityCodeName) {
        this.cityCodeName = cityCodeName;
    }

    public List<FarmStatementModel> getFarmStatementModels() {
        return farmStatementModels;
    }

    public void setFarmStatementModels(List<FarmStatementModel> farmStatementModels) {
        this.farmStatementModels = farmStatementModels;
    }

    public List<CodeModel> getCodeModels() {
        return codeModels;
    }

    public void setCodeModels(List<CodeModel> codeModels) {
        this.codeModels = codeModels;
    }


    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public String getFarmAliasId() {
        return farmAliasId;
    }

    public void setFarmAliasId(String farmAliasId) {
        this.farmAliasId = farmAliasId;
    }

    public FarmModel getFarmModel() {
        return farmModel;
    }

    public void setFarmModel(FarmModel farmModel) {
        this.farmModel = farmModel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<OrderDateModel> getOrderDateModels() {
        return orderDateModels;
    }

    public void setOrderDateModels(List<OrderDateModel> orderDateModels) {
        this.orderDateModels = orderDateModels;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserAliasId() {
        return userAliasId;
    }

    public void setUserAliasId(String userAliasId) {
        this.userAliasId = userAliasId;
    }

    public String getFarmItemAliasId() {
        return farmItemAliasId;
    }

    public void setFarmItemAliasId(String farmItemAliasId) {
        this.farmItemAliasId = farmItemAliasId;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public RefundRequestModel getRefundRequestModel() {
        return refundRequestModel;
    }

    public void setRefundRequestModel(RefundRequestModel refundRequestModel) {
        this.refundRequestModel = refundRequestModel;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public SMSObject getSmsObject() {
        return smsObject;
    }

    public void setSmsObject(SMSObject smsObject) {
        this.smsObject = smsObject;
    }

    public AaSubjectLogin getAaSubjectLogin() {
        return aaSubjectLogin;
    }

    public void setAaSubjectLogin(AaSubjectLogin aaSubjectLogin) {
        this.aaSubjectLogin = aaSubjectLogin;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public QRModel getQrCodeModel() {
        return qrCodeModel;
    }

    public void setQrCodeModel(QRModel qrCodeModel) {
        this.qrCodeModel = qrCodeModel;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public List<Sysinfo> getSysinfos() {
        return sysinfos;
    }

    public void setSysinfos(List<Sysinfo> sysinfos) {
        this.sysinfos = sysinfos;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public List<CollectModel> getCollectModels() {
        return collectModels;
    }

    public void setCollectModels(List<CollectModel> collectModels) {
        this.collectModels = collectModels;
    }

    public void setCollectStatus(String collectStatus) {
        this.collectStatus = collectStatus;
    }

    public FarmQuickPayModel getFarmQuickPayModel() {
        return farmQuickPayModel;
    }

    public void setFarmQuickPayModel(FarmQuickPayModel farmQuickPayModel) {
        this.farmQuickPayModel = farmQuickPayModel;
    }

    public String getSysinfoId() {
        return sysinfoId;
    }

    public void setSysinfoId(String sysinfoId) {
        this.sysinfoId = sysinfoId;
    }

    public String getFarmSetIsExistence() {
        return farmSetIsExistence;
    }

    public void setFarmSetIsExistence(String farmSetIsExistence) {
        this.farmSetIsExistence = farmSetIsExistence;
    }

    public List<aa53458768RecordModel> getAa53458768RecordModels() {
        return aa53458768RecordModels;
    }

    public void setAa53458768RecordModels(List<aa53458768RecordModel> aa53458768RecordModels) {
        this.aa53458768RecordModels = aa53458768RecordModels;
    }

    public WalletModel getWalletModel() {
        return walletModel;
    }

    public void setWalletModel(WalletModel walletModel) {
        this.walletModel = walletModel;
    }

    public CommentModel getCommentModel() {
        return commentModel;
    }

    public void setCommentModel(CommentModel commentModel) {
        this.commentModel = commentModel;
    }

    public List<String> getUUIDs() {
        return UUIDs;
    }

    public void setUUIDs(List<String> UUIDs) {
        this.UUIDs = UUIDs;
    }

    public String getLBSValue() {
        return LBSValue;
    }

    public void setLBSValue(String LBSValue) {
        this.LBSValue = LBSValue;
    }

    public List<ActivityModel> getActivityModels() {
        return activityModels;
    }

    public void setActivityModels(List<ActivityModel> activityModels) {
        this.activityModels = activityModels;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public ActivityModel getActivityModel() {
        return activityModel;
    }

    public void setActivityModel(ActivityModel activityModel) {
        this.activityModel = activityModel;
    }
}
