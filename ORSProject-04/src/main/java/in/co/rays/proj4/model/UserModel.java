package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.EmailBuilder;
import in.co.rays.proj4.util.EmailMessage;
import in.co.rays.proj4.util.EmailUtility;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * 
 * JDBC implementation of UserModel
 * Performs CRUD operations of UserBean.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class UserModel {
	
	public long registerUser(UserBean bean) throws ApplicationException, DuplicateRecordException, Exception {
		long pk=add(bean);
		
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		
		String message=EmailBuilder.getUserRegistrationMessage(map);
		
		EmailMessage msg=new EmailMessage();
		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successful for ORSProject-04");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		
		EmailUtility.sendMail(msg);
		
		return pk;
	}
	
	
	public boolean forgetPassword(String login) throws RecordNotFoundException, ApplicationException {

		UserBean userData = findByLogin(login);
		boolean flag = false;

		if (userData == null) {
			throw new RecordNotFoundException("Email ID does not exists..!!");
		}

		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("login", userData.getLogin());
			map.put("password", userData.getPassword());
			map.put("firstName", userData.getFirstName());
			map.put("lastName", userData.getLastName());

			String message = EmailBuilder.getForgetPasswordMessage(map);

			EmailMessage msg = new EmailMessage();
			msg.setTo(login);
			msg.setSubject("ORSProject-04 Password Reset");
			msg.setMessage(message);
			msg.setMessageType(EmailMessage.HTML_MSG);

			EmailUtility.sendMail(msg);
			flag = true;
		} catch (Exception e) {
			throw new ApplicationException("Please check your internet connection..!!");
		}
		return flag;
	}
	
	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {

		boolean flag = false;

		UserBean beanExist = findByPk(id);

		if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
			beanExist.setPassword(newPassword);
			try {
				update(beanExist);
				flag = true;
			} catch (DuplicateRecordException e) {
				throw new ApplicationException("Login Id already exist");
			}
		} else {
			throw new RecordNotFoundException("Old Password is Invalid");
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", beanExist.getLogin());
		map.put("password", beanExist.getPassword());
		map.put("firstName", beanExist.getFirstName());
		map.put("lastName", beanExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();
		//System.out.println(beanExist.getLogin());
		//msg.setTo("nidhi.agrawal1703@yahoo.com");
		msg.setTo(beanExist.getLogin());
		msg.setSubject("ORSProject-04 Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return flag;
	}

	public static UserBean authenticate(String login, String password) throws ApplicationException {
		Connection conn = null;
		UserBean bean = null;
		try {
			conn = JDBCDataSource.getconnection();
			PreparedStatement pst = conn.prepareStatement("select * from st_user where login=? and password=?");
			pst.setString(1, login);
			pst.setString(2, password);
			ResultSet set = pst.executeQuery();

			while (set.next()) {
				bean = new UserBean();
				bean.setId(set.getLong(1));
				bean.setFirstName(set.getString(2));
				bean.setLastName(set.getString(3));
				bean.setLogin(set.getString(4));
				bean.setPassword(set.getString(5));
				bean.setConfirmPassword(set.getString(6));
				bean.setDob(set.getDate(7));
				bean.setMobileNo(set.getString(8));
				bean.setRoleId(set.getLong(9));
				bean.setGender(set.getString(10));
				bean.setCreatedBy(set.getString(11));
				bean.setModifiedBy(set.getString(12));
				bean.setCreatedDateTime(set.getTimestamp(13));
				bean.setModifiedDateTime(set.getTimestamp(14));

			}
			set.close();
			pst.close();

		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException("Exception : Exception in get roles");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public static Long nextPk() throws Exception {
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getconnection();
			PreparedStatement pst = conn.prepareStatement("select max(id) from st_user");
			ResultSet set = pst.executeQuery();
			while (set.next()) {
				pk = set.getLong(1);
			}
			set.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			throw new DatabaseException("Exception : Exception in getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public static UserBean findByLogin(String login) throws ApplicationException {

		UserBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getconnection();
			PreparedStatement pst = conn.prepareStatement("select * from st_user where login=?");
			pst.setString(1, login);
			ResultSet set = pst.executeQuery();

			while (set.next()) {
				bean = new UserBean();
				bean.setId(set.getLong(1));
				bean.setFirstName(set.getString(2));
				bean.setLastName(set.getString(3));
				bean.setLogin(set.getString(4));
				bean.setPassword(set.getString(5));
				bean.setConfirmPassword(set.getString(6));
				bean.setDob(set.getDate(7));
				bean.setMobileNo(set.getString(8));
				bean.setRoleId(set.getLong(9));
				bean.setGender(set.getString(10));
				bean.setCreatedBy(set.getString(11));
				bean.setModifiedBy(set.getString(12));
				bean.setCreatedDateTime(set.getTimestamp(13));
				bean.setModifiedDateTime(set.getTimestamp(14));
			}
			set.close();
			pst.close();

		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException("Exception : Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public static UserBean findByPk(long id) throws ApplicationException {

		UserBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getconnection();
			PreparedStatement pst = conn.prepareStatement("select * from st_user where id=?");
			pst.setLong(1, id);
			ResultSet set = pst.executeQuery();

			while (set.next()) {
				bean = new UserBean();
				bean.setId(set.getLong(1));
				bean.setFirstName(set.getString(2));
				bean.setLastName(set.getString(3));
				bean.setLogin(set.getString(4));
				bean.setPassword(set.getString(5));
				bean.setConfirmPassword(set.getString(6));
				bean.setDob(set.getDate(7));
				bean.setMobileNo(set.getString(8));
				bean.setRoleId(set.getLong(9));
				bean.setGender(set.getString(10));
				bean.setCreatedBy(set.getString(11));
				bean.setModifiedBy(set.getString(12));
				bean.setCreatedDateTime(set.getTimestamp(13));
				bean.setModifiedDateTime(set.getTimestamp(14));
			}
			set.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public static Long add(UserBean bean) throws ApplicationException, DuplicateRecordException, Exception {
		Connection conn = null;
		UserBean bean1 = findByLogin(bean.getLogin());
		if (bean1 != null) {
			System.out.println(bean1.getLogin());
			throw new DuplicateRecordException("Login Id already exists");
		}
		long nextPk = nextPk();
		try {
			SimpleDateFormat date = new SimpleDateFormat();
			conn = JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst = conn.prepareStatement("insert into st_user values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setLong(1, nextPk);
			pst.setString(2, bean.getFirstName());
			pst.setString(3, bean.getLastName());
			pst.setString(4, bean.getLogin());
			pst.setString(5, bean.getPassword());
			pst.setString(6, bean.getConfirmPassword());
			pst.setDate(7, new java.sql.Date(bean.getDob().getTime()));
			pst.setString(8, bean.getMobileNo());
			pst.setLong(9, bean.getRoleId());
			pst.setString(10, bean.getGender());
			pst.setString(11, bean.getCreatedBy());
			pst.setString(12, bean.getModifiedBy());
			pst.setTimestamp(13, bean.getCreatedDateTime());
			pst.setTimestamp(14, bean.getModifiedDateTime());
			int i = pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("Data inserted: " + i);

		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return nextPk;
	}

	public static void update(UserBean bean) throws DuplicateRecordException, ApplicationException {
		Connection conn = null;
		UserBean existBean = findByLogin(bean.getLogin());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Login Id already exists");
		}
		try {
			conn = JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst = conn.prepareStatement(
					"update st_user set first_name=?,last_name=?,login=?,password=?,confirm_password=?,dob=?,mobile_no=?,role_id=?,gender=?,modified_by=?,modified_timestamp=? where id=?");
			pst.setString(1, bean.getFirstName());
			pst.setString(2, bean.getLastName());
			pst.setString(3, bean.getLogin());
			pst.setString(4, bean.getPassword());
			pst.setString(5, bean.getConfirmPassword());
			pst.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			pst.setString(7, bean.getMobileNo());
			pst.setLong(8, bean.getRoleId());
			pst.setString(9, bean.getGender());
			pst.setString(10, bean.getModifiedBy());
			pst.setTimestamp(11, bean.getModifiedDateTime());
			pst.setLong(12, bean.getId());

			int i = pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("DAta updated: " + i);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : Delete rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception in updating User ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public static void delete(int id) throws Exception {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst = conn.prepareStatement("delete from st_user where id=?");
			pst.setInt(1, id);
			int i = pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("Data deleted: " + i);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : Delete rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public void delete(UserBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_user where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public List<RoleBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public static List search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_user where 1=1");
		if (bean != null) {
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" and first_name like '" + bean.getFirstName() + "%'");
			}
			if (bean.getId() > 0) {
				sql.append(" and id=" + bean.getId());
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" and last_name like '" + bean.getLastName() + "%'");
			}
			if (bean.getLogin() != null  && bean.getLogin().length() > 0) {
				sql.append(" and login='" + bean.getLogin() + "'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" and password='" + bean.getPassword() + "'");
			}
			if (bean.getConfirmPassword() != null && bean.getConfirmPassword().length() > 0) {
				sql.append(" and confirm_password='" + bean.getConfirmPassword() + "'");
			}
			if (bean.getDob() != null) {
				sql.append(" and dob=" + bean.getDob());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" and mobile_no=" + bean.getMobileNo());
			}
			if (bean.getRoleId() > 0) {
				sql.append(" and role_id=" + bean.getRoleId());
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" and gender='" + bean.getGender() + "'");
			}
			if (bean.getCreatedBy() != null && bean.getCreatedBy().length() > 0) {
				sql.append(" and created_by='" + bean.getCreatedBy() + "'");
			}
			if (bean.getModifiedBy() != null && bean.getModifiedBy().length() > 0) {
				sql.append(" and modified_by='" + bean.getModifiedBy() + "'");
			}
		}
		if (pageNo > 0 && pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		System.out.println("SQL query: " + sql.toString());

		try {
			conn = JDBCDataSource.getconnection();
			PreparedStatement pst = conn.prepareStatement(sql.toString());
			ResultSet set = pst.executeQuery();

			while (set.next()) {
				bean = new UserBean();
				bean.setId(set.getLong(1));
				bean.setFirstName(set.getString(2));
				bean.setLastName(set.getString(3));
				bean.setLogin(set.getString(4));
				bean.setPassword(set.getString(5));
				bean.setConfirmPassword(set.getString(6));
				bean.setDob(set.getDate(7));
				bean.setMobileNo(set.getString(8));
				bean.setRoleId(set.getLong(9));
				bean.setGender(set.getString(10));
				bean.setCreatedBy(set.getString(11));
				bean.setModifiedBy(set.getString(12));
				bean.setCreatedDateTime(set.getTimestamp(13));
				bean.setModifiedDateTime(set.getTimestamp(14));
				list.add(bean);

			}
			set.close();
			pst.close();

		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}
