package com.rays.proj4.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.CustomerBean;
import com.rays.pro4.Bean.UserBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.CustomerModel;
import com.rays.pro4.Model.UserModel;

public class CustomerTest {
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		//testInsert();
		//testUpdate();
		//testDelete();	
		testSearch();
		}

	public static void testInsert() {
		try {
			CustomerBean bean = new CustomerBean();

			bean.setId(3);
			bean.setClientName("sreya");
			bean.setLocation("telephoneNager");
			bean.setContactNumber(734350891);
			bean.setImportance(14);

			CustomerModel model = new CustomerModel();

			long pk = model.add(bean);
			CustomerBean addedbean = model.findByPK(pk);
			System.out.println("Test add succ");

			if (addedbean == null) {
				System.out.println("Test add fail");
			}

			System.out.println("record insert");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testUpdate() throws DuplicateRecordException {
		try {
			CustomerBean bean = new CustomerBean();
			bean.setId(3);
			bean.setClientName("riya");
			bean.setLocation("indore");
			bean.setContactNumber(734355891);
			bean.setImportance(15);

			CustomerModel model = new CustomerModel();
			model.update(bean);

			System.out.println("test update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
}
	public static void testDelete() throws ApplicationException {
		CustomerBean bean = new CustomerBean();
		bean.setId(1L);
		CustomerModel model = new CustomerModel();
		model.delete(bean);

}
	private static void testSearch() {
		try {
			CustomerBean  bean = new CustomerBean ();
			CustomerModel model = new CustomerModel();
			List list = new ArrayList();
			// bean.setFirstName("Roshani");
			// bean.setLastName("Bandhiye");
			// bean.setLogin("banti@gmail.com");
			// bean.setId(8L);
			bean.setLocation("indore");
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CustomerBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getClientName());
				System.out.println(bean.getLocation());
				System.out.println(bean.getContactNumber());
				System.out.println(bean.getImportance());
			
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
}
}