package com.ssmedia.rest;

public class KYCRequest {
	
	public String contactNo,status;

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public KYCRequest(String contactNo, String status) {
		super();
		this.contactNo = contactNo;
		this.status = status;
	}

}
