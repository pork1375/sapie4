package egovframework.example.sample.service;

public class MemberVO {

	private int offno; // 순번
	private String usedate; // 사용일
	private String history; // 사용내역
	private String amount; // 사용금액
	private String authorization; // 승인금액
	private String processingstatus; // 처리상태(접수,승인)
	private String registration; // 등록일
	private String amountsum; // 사용금액합계
	private String authorizationsum; // 승인금액합계
	private String receipt; // 영수증사진
	public int getOffno() {
		return offno;
	}
	public void setOffno(int offno) {
		this.offno = offno;
	}
	public String getUsedate() {
		return usedate;
	}
	public void setUsedate(String usedate) {
		this.usedate = usedate;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	public String getProcessingstatus() {
		return processingstatus;
	}
	public void setProcessingstatus(String processingstatus) {
		this.processingstatus = processingstatus;
	}
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public String getAmountsum() {
		return amountsum;
	}
	public void setAmountsum(String amountsum) {
		this.amountsum = amountsum;
	}
	public String getAuthorizationsum() {
		return authorizationsum;
	}
	public void setAuthorizationsum(String authorizationsum) {
		this.authorizationsum = authorizationsum;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	
	

}
