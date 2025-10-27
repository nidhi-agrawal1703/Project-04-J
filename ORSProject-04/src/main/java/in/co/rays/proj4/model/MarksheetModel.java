package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * 
 * JDBC implementation of MarksheetModel.
 * Perform CRUD operation of MarksheetBean.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class MarksheetModel {
	
	public static Long nextPk() throws ApplicationException{
		Connection conn=null;
		long pk=0;
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst=conn.prepareStatement("select max(id) from st_marksheet");
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				pk=rs.getLong(1);
			}			
			rs.close();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Marksheet getting PK");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}
	
	public MarksheetBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_marksheet where id = ?");
		MarksheetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudent_id(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDateTime(rs.getTimestamp(10));
				bean.setModifiedDateTime(rs.getTimestamp(11));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in getting marksheet by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}	
	
	public static String findName(long id) {
		try {
			String name=null;
			Connection conn=JDBCDataSource.getconnection();
			PreparedStatement pst=conn.prepareStatement("select concat(first_name,' ',last_name) as name from st_student where id=?;");
			pst.setLong(1, id);
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				name=rs.getString(1);
			}
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static MarksheetBean findByRollNo(String rollNo)throws ApplicationException {
		Connection conn=null;
		MarksheetBean bean=null;
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst=conn.prepareStatement("select * from st_marksheet where roll_no=?");
			pst.setString(1, rollNo);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()) {
				bean=new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudent_id(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDateTime(rs.getTimestamp(10));
				bean.setModifiedDateTime(rs.getTimestamp(11));
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in finding student by roll number");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	
	public static Long add(MarksheetBean bean) throws ApplicationException,DuplicateRecordException{
		Connection conn=null;
		MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());

		if (duplicateMarksheet != null) {
			throw new DuplicateRecordException("Roll Number already exists");
		}
		long nextPk=nextPk();
		try {
			
			StudentModel smodel=new StudentModel();
			StudentBean studentBean=new StudentBean();
			studentBean=smodel.findByPk(bean.getStudentId());
			String studentName=studentBean.getFirstName()+" "+studentBean.getLastName();
			System.out.println(studentName);
			
			
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("insert into st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");
			pst.setLong(1, nextPk);
			pst.setString(2, bean.getRollNo());
			pst.setLong(3, bean.getStudentId());
			pst.setString(4, studentName);
			pst.setInt(5, bean.getPhysics());
			pst.setInt(6, bean.getChemistry());
			pst.setInt(7, bean.getMaths());
			pst.setString(8, bean.getCreatedBy());
			pst.setString(9, bean.getModifiedBy());
			pst.setTimestamp(10, bean.getCreatedDateTime());
			pst.setTimestamp(11, bean.getModifiedDateTime());
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("Data inserted: "+i);

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception in add student rollback"+e2.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in add student");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return nextPk;
	}
	
	public static void update(MarksheetBean bean) throws ApplicationException,DuplicateRecordException {
		Connection conn=null;
		
		MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());

		if (duplicateMarksheet != null && duplicateMarksheet.getId() != bean.getId()) {
			throw new DuplicateRecordException("Roll Number already exists");
		}

		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("update st_marksheet set roll_no=?,student_id=?,name=?,physics=?,chemistry=?,maths=?,modified_by=?,modified_datetime=? where id=?");
			pst.setString(1, bean.getRollNo());
			pst.setLong(2, bean.getStudentId());
			pst.setString(3, findName(bean.getStudentId()));
			pst.setInt(4, bean.getPhysics());
			pst.setInt(5, bean.getChemistry());
			pst.setInt(6, bean.getMaths());
			pst.setString(7, bean.getModifiedBy());
			pst.setTimestamp(8, bean.getModifiedDateTime());
			pst.setLong(9, bean.getId());
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("DAta updated: "+i);

		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Update rollback exception "+e2.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in updating marksheet");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	public void delete(MarksheetBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getconnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("delete from st_marksheet where id = ?");
			pstmt.setLong(1, bean.getId());
			System.out.println("Deleted Marksheet");
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	public static void delete(long id) throws ApplicationException {
		Connection conn=null;
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("delete from st_marksheet where id=?");
			pst.setLong(1, id);
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("Data deleted: "+i);
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception in delete rollback "+e2.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in deletion of marksheet");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	public List<MarksheetBean> getMeritList(int pageNo,int pageSize) throws ApplicationException {
		ArrayList<MarksheetBean> list =new ArrayList<MarksheetBean>();
		StringBuffer sql=new StringBuffer("select id,roll_no,physics,chemistry,maths,(physics+chemistry+maths) as total from st_marksheet where physics>33 and chemistry>33 and maths>33 order by total desc");
		
		if(pageSize>0) {
			pageNo=(pageNo-1)*pageSize;
			sql.append(" limit "+pageNo+","+pageSize);
		}
		Connection conn=null;
		
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				MarksheetBean bean=new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				list.add(bean);				
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException("Exception in getting merit list of Marksheet"+e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	
	public static List search(MarksheetBean bean,int pageNo,int pageSize) throws ApplicationException,Exception{
		Connection conn=null;
		List list=new ArrayList();
		
		StringBuffer sql=new StringBuffer("select * from st_marksheet where 1=1");
		if(bean!=null) {
			if(bean.getId()>0) {
				sql.append(" and id="+bean.getId());
			}
			if(bean.getRollNo()!=null && bean.getRollNo().length()>0) {
				sql.append(" and roll_no='"+bean.getRollNo()+"'");
			}
			if(bean.getStudentId()>0) {
				sql.append(" and student_id="+bean.getStudentId());
			}
			if(bean.getName()!=null && bean.getName().length()>0) {
				sql.append(" and name like '"+bean.getName()+"%'");
			}
			if(bean.getPhysics()!=null && bean.getPhysics()>0 ) {
				sql.append(" and physics="+bean.getPhysics());
			}
			if(bean.getChemistry()!=null && bean.getChemistry()>0) {
				sql.append(" and chemistry="+bean.getChemistry());
			}
			if(bean.getMaths()!=null && bean.getMaths()>0) {
				sql.append(" and maths="+bean.getMaths());
			}
			if(bean.getCreatedBy()!=null) {
				sql.append("and created_by='"+bean.getCreatedBy()+"'");
			}
			if(bean.getModifiedBy()!=null) {
				sql.append(" and modified_by='"+bean.getModifiedBy()+"'");
			}
		}
		if(pageNo>0 && pageSize>0) {
			pageNo=(pageNo-1)*pageSize;
			sql.append(" limit "+pageNo+","+pageSize);
		}
		System.out.println(sql.toString());
		try {
			conn=JDBCDataSource.getconnection();			
			PreparedStatement pst=conn.prepareStatement(sql.toString());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()) {
				bean=new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudent_id(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDateTime(rs.getTimestamp(10));
				bean.setModifiedDateTime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in searching marksheet");
			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	
	

}
