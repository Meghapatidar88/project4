package com.rays.proj4.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.CustomerBean;
import com.rays.pro4.Bean.StockBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.CustomerModel;
import com.rays.pro4.Model.StockModel;

/**
 * Stock  Model Test classes.
 * @author Megha Patidar
 *
 */
public class StockTest {


	public static StockModel model= new StockModel();
	
	
	public static void main(String[] args) throws ParseException, DuplicateRecordException {
		//testAdd();
		//testDelete();
		// testUpdate();
		//testFindByPK();
	
		testSearch();
		//testList();
		
	}


	public static void testAdd() throws ParseException {
		
		try{
			StockBean bean=new StockBean();
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			
			//bean.setId(3L);
			bean.setQuantity(2);
			bean.setPurchasePrice(345.0);
			bean.setPurchaseDate(sdf.parse("24/12/2024"));
			bean.setOrderType(3);
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			
			StockBean addbean = model.findByPK(pk);
			if(addbean==null){
				System.out.println("Test add fail");
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}catch(DuplicateRecordException e){
			e.printStackTrace();
		}
		

	}
	
	public static void testDelete(){
		
		try{
			StockBean bean = new StockBean();
			long pk=20L;
			
			bean.setId(pk);
			model.delete(bean);
			StockBean deletebean = model.findByPK(pk);
			if(deletebean != null){
				System.out.println("Test Delete fail");
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	private static void testUpdate() throws DuplicateRecordException {
		try {
			StockBean bean = new StockBean();
			bean.setQuantity(1);
			bean.setPurchasePrice(888.0);
			//bean.setPurchaseDate(sdf.parse("22/12/2024"));
			bean.setOrderType(2);
			StockModel model = new StockModel();
			model.update(bean);

			System.out.println("test update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
    }
	public static void testFindByPK(){
		try{
			StockBean bean = new StockBean();
			Long pk=21L;
			bean=model.findByPK(pk);
			if(bean == null){
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getQuantity());
			System.out.println(bean.getPurchasePrice());
			System.out.println(bean.getPurchaseDate());
			System.out.println(bean.getOrderType());
		
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}

	    
	 private static void testSearch() {
			try {
				StockBean  bean = new StockBean ();
				StockModel model = new StockModel();
				List list = new ArrayList();
				// bean.setFirstName("Roshani");
				// bean.setLastName("Bandhiye");
				// bean.setLogin("banti@gmail.com");
				// bean.setId(8L);
				//bean.setLocation("indore");
				list = model.search(bean, 1, 10);
				if (list.size() < 0) {
					System.out.println("Test search fail");
				}
				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (StockBean) it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getQuantity());
					System.out.println(bean.getPurchasePrice());
					System.out.println(bean.getPurchaseDate());
					System.out.println(bean.getOrderType());
				
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
	
}}
