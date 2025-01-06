package com.rays.pro4.Bean;

import java.util.Date;

public class ClientBean extends BaseBean {

	private String fullName;
	private Date appointmentDate;
	private String phone;
	private int illness;

	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getIllness() {
		return illness;
	}

	public void setIllness(int illness) {
		this.illness = illness;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return null ;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
