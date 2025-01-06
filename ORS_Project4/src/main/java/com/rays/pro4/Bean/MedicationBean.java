package com.rays.pro4.Bean;

import java.util.Date;

public class MedicationBean extends BaseBean {
	private String fullName;
	private int illness;
	private Date prescriptionDate;
	private int dosage;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getIllness() {
		return illness;
	}

	public void setIllness(int illness) {
		this.illness = illness;
	}

	public Date getPrescriptionDate() {
		return prescriptionDate;
	}

	public void setPrescriptionDate(Date prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}

	public int getDosage() {
		return dosage;
	}

	public void setDosage(int dosage) {
		this.dosage = dosage;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return illness+"";
	}

}
