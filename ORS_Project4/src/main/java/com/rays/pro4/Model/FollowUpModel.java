package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class FollowUpModel {

		/**
		 * 
		 */
		private static Logger log = Logger.getLogger(FollowUpModel.class);

		/**
		 * @return
		 * @throws DatabaseException
		 */
		public int nextPK() throws DatabaseException {

			log.debug("Model nextPK Started");

			String sql = "SELECT MAX(ID) FROM ST_FOLLOWUP";
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
		 * Add a FollowUp
		 *
		 * @param bean
		 * @throws DatabaseException
		 *
		 */

		public long add(FollowUpBean bean) throws ApplicationException, DuplicateRecordException {
			log.debug("Model add Started");

			String sql = "INSERT INTO ST_FOLLOWUP VALUES(?,?,?,?,?)";

			Connection conn = null;
			int pk = 0;

			try {
				conn = JDBCDataSource.getConnection();
				pk = nextPK();

				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, pk);
				pstmt.setInt(2, bean.getClient());
				pstmt.setInt(3, bean.getPhysician());
				pstmt.setDate(4, new java.sql.Date(bean.getAppointmentDate().getTime()));
				pstmt.setString(5, bean.getCharges());
			
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
		 * Delete a FollowUp
		 *
		 * @param bean
		 * @throws DatabaseException
		 */
		public void delete(FollowUpBean bean) throws ApplicationException {
			log.debug("Model delete start");
			String sql = "DELETE FROM ST_FOLLOWUP WHERE ID=?";
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
		 * Find FollowUp by PK
		 *
		 * @param pk : get parameter
		 * @return bean
		 * @throws DatabaseException
		 */

		public FollowUpBean findByPK(long pk) throws ApplicationException {
			log.debug("Model findBy PK start");
			String sql = "SELECT * FROM ST_FOLLOWUP WHERE ID=?";
			FollowUpBean bean = null;
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, pk);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new FollowUpBean();
					bean.setId(rs.getLong(1));
					bean.setClient(rs.getInt(2));
					bean.setPhysician(rs.getInt(3));
					bean.setAppointmentDate(rs.getDate(4));
					bean.setCharges(rs.getString(5));
					

				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("DataBase Exception ", e);
				throw new ApplicationException("Exception : Exception in getting FollowUp by pk");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Method Find By PK end");
			return bean;
		}

		/**
		 * Update a followup
		 *
		 * @param bean
		 * @throws DatabaseException
		 */

		public void update(FollowUpBean bean) throws ApplicationException, DuplicateRecordException {
			log.debug("Model Update Start");
			String sql = "UPDATE ST_FOLLOWUP SET CLIENT=?,PHYSICIAN=?, APPOINTMENT_DATE=?,CHARGES=?  WHERE ID=?";
			Connection conn = null;

			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bean.getClient());
				pstmt.setInt(2, bean.getPhysician());
				pstmt.setDate(3, new java.sql.Date(bean.getAppointmentDate().getTime()));
				pstmt.setString(4, bean.getCharges());
				pstmt.setLong(5, bean.getId());
				int i = pstmt.executeUpdate();
				System.out.println("update followup>> " + i);
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

		public List search(FollowUpBean bean) throws ApplicationException {
			return search(bean, 0, 0);
		}

		/**
		 * Search FollowUp with pagination
		 *
		 * @return list : List of FollowUps
		 * @param bean     : Search Parameters
		 * @param pageNo   : Current Page No.
		 * @param pageSize : Size of Page
		 *
		 * @throws DatabaseException
		 */

		public List search(FollowUpBean bean, int pageNo, int pageSize) throws ApplicationException {
			log.debug("Model Search Start");
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_FOLLOWUP WHERE 1=1");
			if (bean != null) {

				if (bean.getClient() > 0) {
					sql.append(" AND CLIENT = " + bean.getClient());
				}
				if (bean.getPhysician() > 0) {
					sql.append(" AND PHYSICIAN = " + bean.getPhysician());
				}
				if (bean.getAppointmentDate() != null && bean.getAppointmentDate().getTime() > 0) {
					Date d = new Date(bean.getAppointmentDate().getTime());
					sql.append(" AND APPOINTMENTDATE = '" + d + "'");
					System.out.println("done");
				}
				
				if (bean.getCharges() != null && bean.getCharges().length() > 0) {
					sql.append(" AND CHARGES like '" + bean.getCharges() + "%'");
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
					bean = new FollowUpBean();
					bean.setId(rs.getLong(1));
					bean.setClient(rs.getInt(2));
					bean.setPhysician(rs.getInt(3));
					bean.setAppointmentDate(rs.getDate(4));
					bean.setCharges(rs.getString(5));
					

					list.add(bean);

				}
				rs.close();
			} catch (Exception e) {
				log.error("Database Exception", e);
				throw new ApplicationException("Exception: Exception in Search FollowUp");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model Search end");
			return list;

		}

		/**
		 * Get List of FollowUp
		 *
		 * @return list : List of FollowUp
		 * @throws DatabaseException
		 */

		public List list() throws ApplicationException {
			return list(0, 0);
		}

		/**
		 * Get List of FollowUp with pagination
		 *
		 * @return list : List of followups
		 * @param pageNo   : Current Page No.
		 * @param pageSize : Size of Page
		 * @throws DatabaseException
		 */

		public List list(int pageNo, int pageSize) throws ApplicationException {
			log.debug("Model list Started");
			ArrayList list = new ArrayList();
			StringBuffer sql = new StringBuffer("select * from ST_FOLLOWUP");

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
					FollowUpBean bean = new FollowUpBean();
					
					bean.setId(rs.getLong(1));
					bean.setClient(rs.getInt(2));
					bean.setPhysician(rs.getInt(3));
					bean.setAppointmentDate(rs.getDate(4));
					bean.setCharges(rs.getString(5));
					


					list.add(bean);

				}
				rs.close();
			} catch (Exception e) {
				log.error("Database Exception...", e);
				throw new ApplicationException("Exception : Exception in getting list of followups");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model list End");
			return list;
		}

	}


