package com.pms.user.constant;

public enum AppointmentStatus {

	SCHEDULED(0),
	CANCELLED(-1),
	COMPLETED(1);
	
	private int status;
	
	AppointmentStatus(int status){
		this.status=status;
	}

	public int getStatus() {
		return status;
	}
}
