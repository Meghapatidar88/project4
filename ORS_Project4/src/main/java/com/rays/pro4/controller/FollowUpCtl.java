package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.RoleModel;
import com.rays.pro4.Model.FollowUpModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
 * The Class FollowUpCtl.
 * 
 * @author Megha Patidar
 * 
 */
@WebServlet(name = "FollowUpCtl", urlPatterns = { "/ctl/FollowUpCtl" })
public class FollowUpCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(FollowUpCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		
		FollowUpModel model = new FollowUpModel();
		Map<Integer, String> map = new HashMap();

		map.put(1, "sushma");
		map.put(2, "jaya");
		map.put(3, "rekha");
		map.put(4, "sikha");
		
		request.setAttribute("followup", map);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");
		log.debug("FollowUpCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("client"))) {
			request.setAttribute("client", PropertyReader.getValue("error.require", "Client"));
			pass = false;
		}
			if (DataValidator.isNull(request.getParameter("physician"))) {
				request.setAttribute("physician", PropertyReader.getValue("error.require", "Physician"));
				pass = false;
			
			}
		if (DataValidator.isNull(request.getParameter("appointmentDate"))) {
			request.setAttribute("appointmentDate", PropertyReader.getValue("error.require", "AppointmentDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("appointmentDate"))) {
			request.setAttribute("appointmentDate", PropertyReader.getValue("error.date", "AppointmentDate"));
			pass = false;
		}
	

		if (DataValidator.isNull(request.getParameter("charges"))) {
			request.setAttribute("charges", PropertyReader.getValue("error.require", "Charges"));
			pass = false;
//			
		}

		log.debug("FollowUpCtl Method validate Ended");

		return pass;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */

	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println(" uctl Base bean P bean");
		log.debug("FollowUpCtl Method populatebean Started");

		FollowUpBean bean = new FollowUpBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setClient(DataUtility.getInt(request.getParameter("client")));
		
		bean.setPhysician(DataUtility.getInt(request.getParameter("physician")));

		bean.setAppointmentDate(DataUtility.getDate(request.getParameter("appointmentDate")));

		bean.setCharges(DataUtility.getString(request.getParameter("charges")));

	

		populateDTO(bean, request);

		log.debug("FollowUpCtl Method populatebean Ended");

		return bean;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("FollowUpCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		FollowUpModel model = new FollowUpModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("FollowUp Edit Id >= " + id);
		if (id != 0 && id > 0) {
			System.out.println("in id > 0  condition");
			FollowUpBean bean;
			try {
				bean = model.findByPK(id);
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("FollowUpCtl Method doGet Ended");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("uctl Do Post");

		log.debug("FollowUpCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		FollowUpModel model = new FollowUpModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FollowUpBean bean = (FollowUpBean) populateBean(request);
			System.out.println(" U ctl DoPost 11111111");

			try {
				if (id > 0) {

					// System.out.println("hi i am in dopost update");
					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					ServletUtility.setSuccessMessage("FollowUp is successfully Updated", request);

				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);
					
					bean = model.findByPK(pk);
					
					// bean.setId(pk);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("FollowUp is successfully Added", request);
				
				}
				/*
				 * ServletUtility.setBean(bean, request);
				 * ServletUtility.setSuccessMessage("FollowUp is successfully saved", request);
				 */

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" U ctl D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" U ctl D p 5555555");

			FollowUpBean bean = (FollowUpBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" u ctl D Post  6666666");
				ServletUtility.redirect(ORSView.FOLLOWUP_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.FOLLOWUP_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("FollowUpCtl Method doPostEnded");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.FOLLOWUP_VIEW;
	}

}
