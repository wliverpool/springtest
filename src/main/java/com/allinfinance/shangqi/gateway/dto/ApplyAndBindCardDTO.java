package com.allinfinance.shangqi.gateway.dto;

import java.util.List;
import java.util.Map;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.PageQueryDTO;

public class ApplyAndBindCardDTO extends PageQueryDTO implements java.io.Serializable{
	
	
	/**
	 */
	private static final long serialVersionUID = 2800521416045395554L;
	/**
	 */
	private String userId; 
	private String productId;//产品类型号
	private String stockStatus;//在库状态
	private String entityId;//机构号
	private int    count;
	
	/**
     * 登录用户ID
     */
    private String loginUserId; //默认

    
    /***
     * 默认实体ID
     * @return
     */
    
    private String defaultEntityId;//默认
	private String cardNo;//卡号
	private String serviceFee;//服务费
	
	/*持卡人信息*/
	private String firstName;//持卡人名称
	private String lastName;
	private String idType;//证件类型
	private String idNo;//证件号
	private String cardholderMobile;
	private String cardholderEmail;
	private String externalId;//外部系统号
	private String departmentId; //部门ID
	private String cardholderBirthday;//持卡人生日
	private String cardholderGender;//持卡人性别
	private String cardholderSalutation;//持卡人称谓
	private String cardholderFunction;//持卡人职位
	private String cardholderSegment;//持卡人分类
	private String cardholderState;//持卡人状态
	private String closeDate;//关闭日期
	private String cardholderComment;//备注
	/*邮寄信息*/
	private String  recipient_name;//收件人名称
	private String  recipient_addr;//收件人地址
	private String   recipient_phone;//收件人电话
	private String   post_code;//邮编
	private String  channel;//渠道
	
	private String applyDateSeconds;//申请时间 精确到秒
	private String startDate;
	private String endDate;
	/*下载excel文件的map*/
	private Map<String, List<byte[]>> fileData;
	private String cvn2;//卡片cvn2
	
	/*车信息*/
	private String vId; //车架号
	private String plateNumber ; //车牌号
	private String driverLicence ;//驾驶证号
	
	
	/*中石油字段*/
	private String province;//所在省份
	private String city;//所在城市
	private String source;//0 微信 1PC 渠道
	private String oldCardNo;//已绑过的卡号
	
	//是否已有客户信息
	private String isCustomerInfo;
	//是否已发过卡
	private String isIssue;
	
	
	
	
	
	
	

	public String getOldCardNo() {
		return oldCardNo;
	}
	public void setOldCardNo(String oldCardNo) {
		this.oldCardNo = oldCardNo;
	}
	public String getIsCustomerInfo() {
		return isCustomerInfo;
	}
	public void setIsCustomerInfo(String isCustomerInfo) {
		this.isCustomerInfo = isCustomerInfo;
	}
	public String getIsIssue() {
		return isIssue;
	}
	public void setIsIssue(String isIssue) {
		this.isIssue = isIssue;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getVId() {
		return vId;
	}
	public void setVId(String vId) {
		this.vId = vId;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getDriverLicence() {
		return driverLicence;
	}
	public void setDriverLicence(String driverLicence) {
		this.driverLicence = driverLicence;
	}
	public String getCvn2() {
		return cvn2;
	}
	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}
	public Map<String, List<byte[]>> getFileData() {
		return fileData;
	}
	public void setFileData(Map<String, List<byte[]>> fileData) {
		this.fileData = fileData;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	//卡申请状态查询 
	private String applyStatus;
	
	
	
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	public String getDefaultEntityId() {
		return defaultEntityId;
	}
	public void setDefaultEntityId(String defaultEntityId) {
		this.defaultEntityId = defaultEntityId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getRecipient_name() {
		return recipient_name;
	}
	public void setRecipient_name(String recipient_name) {
		this.recipient_name = recipient_name;
	}
	public String getRecipient_addr() {
		return recipient_addr;
	}
	public void setRecipient_addr(String recipient_addr) {
		this.recipient_addr = recipient_addr;
	}
	public String getRecipient_phone() {
		return recipient_phone;
	}
	public void setRecipient_phone(String recipient_phone) {
		this.recipient_phone = recipient_phone;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getStockStatus() {
		return stockStatus;
	}
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCardholderMobile() {
		return cardholderMobile;
	}
	public void setCardholderMobile(String cardholderMobile) {
		this.cardholderMobile = cardholderMobile;
	}
	public String getCardholderEmail() {
		return cardholderEmail;
	}
	public void setCardholderEmail(String cardholderEmail) {
		this.cardholderEmail = cardholderEmail;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getCardholderBirthday() {
		return cardholderBirthday;
	}
	public void setCardholderBirthday(String cardholderBirthday) {
		this.cardholderBirthday = cardholderBirthday;
	}
	public String getCardholderGender() {
		return cardholderGender;
	}
	public void setCardholderGender(String cardholderGender) {
		this.cardholderGender = cardholderGender;
	}
	public String getCardholderSalutation() {
		return cardholderSalutation;
	}
	public void setCardholderSalutation(String cardholderSalutation) {
		this.cardholderSalutation = cardholderSalutation;
	}
	public String getCardholderFunction() {
		return cardholderFunction;
	}
	public void setCardholderFunction(String cardholderFunction) {
		this.cardholderFunction = cardholderFunction;
	}
	public String getCardholderSegment() {
		return cardholderSegment;
	}
	public void setCardholderSegment(String cardholderSegment) {
		this.cardholderSegment = cardholderSegment;
	}
	public String getCardholderState() {
		return cardholderState;
	}
	public void setCardholderState(String cardholderState) {
		this.cardholderState = cardholderState;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public String getCardholderComment() {
		return cardholderComment;
	}
	public void setCardholderComment(String cardholderComment) {
		this.cardholderComment = cardholderComment;
	}
	public String getApplyDateSeconds() {
		return applyDateSeconds;
	}
	public void setApplyDateSeconds(String applyDateSeconds) {
		this.applyDateSeconds = applyDateSeconds;
	}
	
	
}
