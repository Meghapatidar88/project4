package com.rays.proj4.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.ClientModel;

public class ClientTest {

	public static void main(String[] args) throws DuplicateRecordException, Exception  {

	

      //testInsert();
	 //testUpdate();
	// testDelete();
	 testSearch();
	}

	public static void testInsert() {
		try {
			ClientBean bean = new ClientBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			bean.setId(3);
			bean.setFullName("himanshi");
			bean.setAppointmentDate(sdf.parse("09-09-2024"));
			bean.setPhone("7344568989");
			bean.setIllness(3);

			ClientModel model = new ClientModel();

			long pk = model.add(bean);
			ClientBean addedbean = model.findByPK(pk);
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
			ClientBean bean = new ClientBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			bean.setId(1);
			bean.setFullName("vinjal");
			bean.setAppointmentDate(sdf.parse("10-02-2024"));
			bean.setPhone("7343508910");
			bean.setIllness(1);

			ClientModel model = new ClientModel();
			model.update(bean);

			System.out.println("test update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws ApplicationException {
		ClientBean bean = new ClientBean();
		bean.setId(1L);
		ClientModel model = new ClientModel();
		model.delete(bean);

	}

	private static void testSearch() {
		try {
			ClientBean bean = new ClientBean();
			ClientModel model = new ClientModel();
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
				bean = (ClientBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFullName());
				System.out.println(bean.getAppointmentDate());
				System.out.println(bean.getPhone());
				System.out.println(bean.getIllness());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
