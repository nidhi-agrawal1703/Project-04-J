package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * 
 * JDBC implementation of StudentModel.
 * Performs CRUD operations of StudentBean.
 * 
 * @author Nidhi
 * @version 1.0
 *
 */
public class StudentModel {

	public static Long nextPk() {
		long pk = 0;
		Connection conn=null;
		try {			
			conn=JDBCDataSource.getconnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_student");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}
	
	
	
	public static String getCollegeName(long collegeId) throws Exception {
		
		Connection conn=JDBCDataSource.getconnection();
		PreparedStatement pst=conn.prepareStatement("select name from st_college where id=?");
		pst.setLong(1, collegeId);
		ResultSet rs=pst.executeQuery();
		String collegeName = null;
		while(rs.next()) {
			collegeName=rs.getString(1);
		}
		return collegeName;
	}

	public static StudentBean findByPk(long id) throws Exception{
		Connection conn=null;
		StudentBean bean=null;
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst=conn.prepareStatement("select * from st_student where id=?");
			pst.setLong(1, id);
			ResultSet rs=pst.executeQuery();
			bean=new StudentBean();
			while(rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
			}
			rs.close();
			pst.close();
	
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
			}
	
	public static Long add(StudentBean bean) throws ApplicationException,DuplicateRecordException {

		Connection conn=null;
		StudentBean existbean=findByEmail(bean.getEmail());
		if(existbean!=null) {
			throw new DuplicateRecordException("Email id already exists");
		}
		long pk = nextPk();
		try {
			CollegeModel cmodel=new CollegeModel();
			CollegeBean cbean=new CollegeBean();
			cbean=cmodel.findByPk(bean.getCollegeId());
			String collegeName=cbean.getName();
			
			
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst = conn.prepareStatement("insert into st_student values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setLong(1, pk);
			pst.setString(2, bean.getFirstName());
			pst.setString(3, bean.getLastName());
			pst.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pst.setString(5, bean.getGender());
			pst.setString(6, bean.getMobileNo());
			pst.setString(7, bean.getEmail());
			pst.setLong(8, bean.getCollegeId());
			//String collegeName=null;
			//collegeName=getCollegeName(bean.getCollegeId());
			pst.setString(9, collegeName);
			pst.setString(10, bean.getCreatedBy());
			pst.setString(11, bean.getModifiedBy());
			pst.setTimestamp(12, bean.getCreatedDateTime());
			pst.setTimestamp(13, bean.getModifiedDateTime());
			int i = pst.executeUpdate();
			conn.commit();
			System.out.println("Data inserted: " + i);
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : add rollback exception " + e2.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Student");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public static void update(StudentBean bean) throws ApplicationException,DuplicateRecordException {
		Connection conn=null;
		
		StudentBean existBean=findByEmail(bean.getEmail());
		if(existBean!=null && bean.getId()!=existBean.getId()) {
			throw new DuplicateRecordException("Email id already exists");
		}
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst = conn.prepareStatement(
					"update st_student set first_name=?,last_name=?,dob=?,gender=?,mobile_no=?,email=?,college_id=?,college_name=?,modified_by=?,modified_datetime=? where id=?");

			pst.setString(1, bean.getFirstName());
			pst.setString(2, bean.getLastName());
			pst.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pst.setString(4, bean.getGender());
			pst.setString(5, bean.getMobileNo());
			pst.setString(6, bean.getEmail());
			pst.setLong(7, bean.getCollegeId());
			pst.setString(8, getCollegeName(bean.getCollegeId()));
			pst.setString(9, bean.getModifiedBy());
			pst.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
			pst.setLong(11, bean.getId());

			int i = pst.executeUpdate();
			conn.commit();
			System.out.println("Data updated " + i);
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : Update rollback exception " + e2.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in updating Student");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}

	public static void delete(long id) throws ApplicationException {
		Connection conn=null;
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst = conn.prepareStatement("delete from st_student where id=?");
			pst.setLong(1, id);
			int i = pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("DAta deleted " + i);
	
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : Delete rollback exception " + e2.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in delete Student");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}
	public void delete(StudentBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getconnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("delete from st_student where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public static List search(StudentBean bean, int pageNo, int pageSize) throws ApplicationException {
		Connection conn=null;
		List list = new ArrayList();
		// StudentBean bean=null;
		try {
			conn=JDBCDataSource.getconnection();
			StringBuffer sql = new StringBuffer("select * from st_student where 1=1");

			if (bean != null) {
				if (bean.getFirstName() != null && bean.getFirstName().length()>0) {
					sql.append(" and first_name like '" + bean.getFirstName() + "%'");
				}
				if (bean.getLastName() != null && bean.getLastName().length()>0) {
					sql.append(" and last_name like '" + bean.getLastName() + "%'");
				}
				if (bean.getDob() != null) {
					sql.append(" and dob=" + bean.getDob());
				}
				if (bean.getGender() != null && bean.getGender().length()>0) {
					sql.append(" and gender like'" + bean.getGender() + "%'");
				}
				if (bean.getMobileNo() != null && bean.getMobileNo().length()>0) {
					sql.append(" and mobile_no='" + bean.getMobileNo() + "'");
				}
				if (bean.getEmail() != null && bean.getEmail().length()>0) {
					sql.append(" and email='" + bean.getEmail() + "'");
				}
				if (bean.getCollegeName() != null && bean.getCollegeName().length()>0) {
					sql.append(" and college_name='" + bean.getCollegeName() + "'");
				}

				
			}
			if (pageNo > 0 && pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" limit " + pageNo + "," + pageSize);
			}
			System.out.println("SQL Query :" + sql);
			PreparedStatement pst = conn.prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				bean = new StudentBean();
				
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				
				list.add(bean);
			}
			rs.close();
			pst.close();
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception : Delete rollback exception " + e.getMessage());
			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
			}

	public static StudentBean findByEmail(String email) throws ApplicationException {
		Connection conn=null;
		StudentBean bean=null;
		try {
			conn=JDBCDataSource.getconnection();
			StringBuffer sql = new StringBuffer("select * from st_student where email=?");
			
			PreparedStatement pst = conn.prepareStatement(sql.toString());
			pst.setString(1, email);
			
			ResultSet rs = pst.executeQuery();
			System.out.println(sql);
			
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeName(rs.getString(8));

			}		
			rs.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
}
	public List<StudentBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

}
