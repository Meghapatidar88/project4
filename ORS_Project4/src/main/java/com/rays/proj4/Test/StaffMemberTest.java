package com.rays.proj4.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.StaffMemberBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.StaffMemberModel;

public class StaffMemberTest {

	public static void main(String[] args) throws DuplicateRecordException, Exception  {

	

     // testInsert();
	// testUpdate();
	// testDelete();
	 testSearch();
	}

	public static void testInsert() {
		try {
			StaffMemberBean bean = new StaffMemberBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			bean.setId(3);
			bean.setFullName("eisha");
			bean.setJoiningDate(sdf.parse("11-05-2024"));
			bean.setDivision(2);
			bean.setPreviousEmployer("aadi");

			StaffMemberModel model = new StaffMemberModel();

			long pk = model.add(bean);
			StaffMemberBean addedbean = model.findByPK(pk);
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
			StaffMemberBean bean = new StaffMemberBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			bean.setId(1);
			bean.setFullName("kinjal");
			bean.setJoiningDate(sdf.parse("10-02-2024"));
			bean.setDivision(1);
			bean.setPreviousEmployer("riya");
			

			StaffMemberModel model = new StaffMemberModel();
			model.update(bean);

			System.out.println("test update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws ApplicationException {
		StaffMemberBean bean = new StaffMemberBean();
		bean.setId(1L);
		StaffMemberModel model = new StaffMemberModel();
		model.delete(bean);

	}

	private static void testSearch() {
		try {
			StaffMemberBean bean = new StaffMemberBean();
			StaffMemberModel model = new StaffMemberModel();
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
				bean = (StaffMemberBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFullName());
				System.out.println(bean.getJoiningDate());
				System.out.println(bean.getDivision());
				System.out.println(bean.getPreviousEmployer());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
