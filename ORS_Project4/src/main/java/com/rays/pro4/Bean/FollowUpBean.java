package com.rays.pro4.Bean;

import java.util.Date;

public class FollowUpBean extends BaseBean{
	private int client;
	private int physician;
	private Date appointmentDate;
	private String charges;
	public int getClient() {
		return client;
	}
	public void setClient(int client) {
		this.client = client;
	}
	public int getPhysician() {
		return physician;
	}
	public void setPhysician(int physician) {
		this.physician = physician;
	}
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getCharges() {
		return charges;
	}
	public void setCharges(String charges) {
		this.charges = charges;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
