package com.rays.proj4.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.ClientModel;
import com.rays.pro4.Model.FollowUpModel;

public class FollowUpTest {

	public static void main(String[] args) throws DuplicateRecordException, Exception  {

	

     // testInsert();
	 //testUpdate();
	// testDelete();
	 testSearch();
	}

	public static void testInsert() {
		try {
			FollowUpBean bean = new FollowUpBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			bean.setId(3);
			bean.setClient(3);
			bean.setPhysician(4);
			bean.setAppointmentDate(sdf.parse("03-10-2024"));
			bean.setCharges("500rs");
			FollowUpModel model = new FollowUpModel();
			model.update(bean);


		

			long pk = model.add(bean);
			FollowUpBean addedbean = model.findByPK(pk);
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
			FollowUpBean bean = new FollowUpBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			bean.setId(1);
			bean.setClient(1);
			bean.setPhysician(1);
			bean.setAppointmentDate(sdf.parse("09-09-2024"));
			bean.setCharges("200rs");
			FollowUpModel model = new FollowUpModel();
			model.update(bean);

			System.out.println("test update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws ApplicationException {
		FollowUpBean bean = new FollowUpBean();
		bean.setId(1L);
		FollowUpModel model = new FollowUpModel();
		model.delete(bean);

	}

	private static void testSearch() {
		try {
			FollowUpBean bean = new FollowUpBean();
			FollowUpModel model = new FollowUpModel();
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
				bean = (FollowUpBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getClient());
				System.out.println(bean.getPhysician());
				System.out.println(bean.getAppointmentDate());
				System.out.println(bean.getCharges());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
