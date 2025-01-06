package com.rays.proj4.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.MedicationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.MedicationModel;

public class MedicationTest {

	public static void main(String[] args) throws DuplicateRecordException, Exception  {

	

     // testInsert();
	// testUpdate();
	//testDelete();
	 testSearch();
	}

	public static void testInsert() {
		try {
			MedicationBean bean = new MedicationBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			bean.setId(4);
			bean.setFullName("suman");
			bean.setIllness(4);
			bean.setPrescriptionDate(sdf.parse("11-07-2024"));
			bean.setDosage(100);
			

			MedicationModel model = new MedicationModel();

			long pk = model.add(bean);
			MedicationBean addedbean = model.findByPK(pk);
			System.out.println("Test add succ");

			if (addedbean == null) {
				System.out.println("Test add fail");
			}

			System.out.println("record insert");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testUpdate() throws DuplicateRecordException, Exception {
		try {
			MedicationBean bean = new MedicationBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			bean.setId(3);
			bean.setFullName("sikha");
			bean.setIllness(3);
			bean.setPrescriptionDate(sdf.parse("15-09-2024"));
			bean.setDosage(50);
			MedicationModel model = new MedicationModel();
			model.update(bean);

			System.out.println("test update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws ApplicationException {
		MedicationBean bean = new MedicationBean();
		bean.setId(1L);
		MedicationModel model = new MedicationModel();
		model.delete(bean);

	}

	private static void testSearch() {
		try {
			MedicationBean bean = new MedicationBean();
			MedicationModel model = new MedicationModel();
			List list = new ArrayList();
			// bean.setFirstName("Roshani");
			// bean.setLastName("Bandhiye");
			// bean.setLogin("banti@gmail.com");
			// bean.setId(8L);
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MedicationBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFullName());
				System.out.println(bean.getIllness());
				System.out.println(bean.getPrescriptionDate());
				System.out.println(bean.getDosage());
				
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
