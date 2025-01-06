package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.MedicationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class MedicationModel {

	/**
	 * 
	 */
	private static Logger log = Logger.getLogger(MedicationModel.class);

	/**
	 * @return
	 * @throws DatabaseException
	 */
	public int nextPK() throws DatabaseException {

		log.debug("Model nextPK Started");

		String sql = "SELECT MAX(ID) FROM ST_MEDICATION";
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK Started");
		return pk + 1;

	}

	/**
	 * Add a Medication
	 *
	 * @param bean
	 * @throws DatabaseException
	 *
	 */

	public long add(MedicationBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");

		String sql = "INSERT INTO ST_MEDICATION VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFullName());
			pstmt.setInt(3, bean.getIllness());
			pstmt.setDate(4, new java.sql.Date(bean.getPrescriptionDate().getTime()));
			pstmt.setInt(5, bean.getDosage());

			// date of birth caste by sql date

			int a = pstmt.executeUpdate();
			System.out.println(a);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception ...", e);
			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				// application exception
				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Add End");
		return pk;

	}

	/**
	 * Delete a Medication
	 *
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(MedicationBean bean) throws ApplicationException {
		log.debug("Model delete start");
		String sql = "DELETE FROM ST_MEDICATION WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Delete End");
	}

	/**
	 * Find Medication by PK
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public MedicationBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findBy PK start");
		String sql = "SELECT * FROM ST_MEDICATION WHERE ID=?";
		MedicationBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MedicationBean();
				bean.setId(rs.getLong(1));
				bean.setFullName(rs.getString(2));
				bean.setIllness(rs.getInt(3));
				bean.setPrescriptionDate(rs.getDate(4));
				bean.setDosage(rs.getInt(5));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception ", e);
			throw new ApplicationException("Exception : Exception in getting Medication by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method Find By PK end");
		return bean;
	}

	/**
	 * Update a medication
	 *
	 * @param bean
	 * @throws DatabaseException
	 */

	public void update(MedicationBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model Update Start");
		String sql = "UPDATE ST_MEDICATION SET FULL_NAME=?, ILLNESS=?, PRECRIPTION_DATE=?, DOSAGE=?  WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getFullName());
			pstmt.setInt(2, bean.getIllness());
			pstmt.setDate(3, new java.sql.Date(bean.getPrescriptionDate().getTime()));
			pstmt.setInt(4, bean.getDosage());
			pstmt.setLong(5, bean.getId());
			int i = pstmt.executeUpdate();
			System.out.println("update medication>> " + i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Update End ");
	}

	public List search(MedicationBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Medication with pagination
	 *
	 * @return list : List of Medications
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws DatabaseException
	 */

	public List search(MedicationBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model Search Start");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MEDICATION WHERE 1=1");
		if (bean != null) {

			if (bean.getFullName() != null && bean.getFullName().length() > 0) {
				sql.append(" AND FULL_NAME like '" + bean.getFullName() + "%'");
				
				if (bean.getIllness() > 0) {
					sql.append(" AND ILLNESS = " + bean.getIllness());
				}
			}
			if (bean.getPrescriptionDate() != null && bean.getPrescriptionDate().getTime() > 0) {
				Date d = new Date(bean.getPrescriptionDate().getTime());
				sql.append(" AND PRESCRIPTION_DATE = '" + d + "'");
				System.out.println("done");
			}
			if (bean.getDosage() > 0) {
				sql.append(" AND DOSAGE = " + bean.getDosage());
			}
			
			if (bean.getId() > 0) {
				sql.append(" AND ID = " + bean.getId());
			}

		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		System.out.println("sql query search >>= " + sql.toString());
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MedicationBean();
				bean.setId(rs.getLong(1));
				bean.setFullName(rs.getString(2));
				bean.setIllness(rs.getInt(3));
				bean.setPrescriptionDate(rs.getDate(4));
				bean.setDosage(rs.getInt(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new ApplicationException("Exception: Exception in Search Medication");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search end");
		return list;

	}

	/**
	 * Get List of Medication
	 *
	 * @return list : List of Medication
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Medication with pagination
	 *
	 * @return list : List of medications
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_MEDICATION");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MedicationBean bean = new MedicationBean();
				bean.setId(rs.getLong(1));
				bean.setFullName(rs.getString(2));
				bean.setIllness(rs.getInt(3));
				bean.setPrescriptionDate(rs.getDate(4));
				bean.setDosage(rs.getInt(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception...", e);
			throw new ApplicationException("Exception : Exception in getting list of medications");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}

}
