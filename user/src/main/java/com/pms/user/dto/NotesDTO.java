package com.pms.user.dto;

import com.pms.user.entity.UserData;

public class NotesDTO {
	
	private UserData receiverId;
	private String receiverName;
	private String receiverDesignation;
	private String message;
	private boolean priority;
	
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverDesignation() {
		return receiverDesignation;
	}
	public void setReceiverDesignation(String receiverDesignation) {
		this.receiverDesignation = receiverDesignation;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isPriority() {
		return priority;
	}
	public void setPriority(boolean priority) {
		this.priority = priority;
	}
	public UserData getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(UserData receiverId) {
		this.receiverId = receiverId;
	}
	

}
