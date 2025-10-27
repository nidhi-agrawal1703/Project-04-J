package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * JDBC implementation of CourseModel.
 * Perform CRUD operations of CourseModel.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class CourseModel {
	
	public static Long nextPk() throws ApplicationException{
		Connection conn=null;
		long pk=0;
		try {			
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst=conn.prepareStatement("select max(id) from st_course");
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				pk=rs.getInt(1);
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in finding next pk");			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}
	
	public static CourseBean findByName(String name) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_course where name = ?");
		CourseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));

			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Course by Course Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	public static Long add(CourseBean bean) throws ApplicationException,DuplicateRecordException {
		Connection conn=null;
		CourseBean duplicateBean=findByName(bean.getName());
		if(duplicateBean!=null) {
			throw new DuplicateRecordException("Course name already exists");
		}
		long nextPk=nextPk();
		try {			
			
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("insert into st_course values(?,?,?,?,?,?,?,?)");
			pst.setLong(1, nextPk);
			pst.setString(2, bean.getName());
			pst.setString(3, bean.getDuration());
			pst.setString(4, bean.getDescription());
			pst.setString(5, bean.getCreatedBy());
			pst.setString(6, bean.getModifiedBy());
			pst.setTimestamp(7, bean.getCreatedDateTime());
			pst.setTimestamp(8, bean.getModifiedDateTime());
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("Data inserted: "+i);
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception in add rollback");
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in adding course");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return nextPk;
	}
	
	public static CourseBean findByPk(long id) throws ApplicationException {
		Connection conn=null;
		CourseBean bean=null;
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst=conn.prepareStatement("select * from st_course where id=?");
			pst.setLong(1, id);
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				bean=new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in finding course by pk");
			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}	
		return bean;
	}
	
	public static void update(CourseBean bean) throws ApplicationException,DuplicateRecordException {
		Connection conn=null;
		//CourseBean duplicateBean=findByName(bean.getName());
		//if(duplicateBean!=null && duplicateBean.getId()!=bean.getId()) {
			//throw new DuplicateRecordException("Course name already exists");
		//}
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("update st_course set name=?,duration=?,description=?,modified_by=?,modified_datetime=? where id=?");
			pst.setString(1, bean.getName());
			pst.setString(2, bean.getDuration());
			pst.setString(3, bean.getDescription());
			pst.setString(4, bean.getModifiedBy());
			pst.setTimestamp(5, bean.getModifiedDateTime());
			pst.setLong(6, bean.getId());
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("Data updated: "+i);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in updating course");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public static void delete(long id) throws ApplicationException{
		Connection conn=null;
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("delete from st_course where id=?");
			pst.setLong(1, id);
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("Data deleted: "+i);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in deleting course");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	public void delete(CourseBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_course where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	
	public static List search(CourseBean bean,int pageNo,int pageSize) throws ApplicationException {
		Connection conn=null;
		List list=new ArrayList();
		try {
			conn=JDBCDataSource.getconnection();
			StringBuffer sql=new StringBuffer("select * from st_course where 1=1");
			if(bean!=null) {
				if(bean.getId()>0) {
					sql.append(" and id="+bean.getId());
				}
				if(bean.getName()!=null && bean.getName().length()>0) {
					sql.append(" and name='"+bean.getName()+"'");
				}
				if(bean.getDuration()!=null && bean.getDuration().length()>0) {
					sql.append(" and duration='"+bean.getDuration()+"'");
				}
				if(bean.getDescription()!=null && bean.getDescription().length()>0) {
					sql.append(" and description='"+bean.getDescription()+"'");
					
				}
				if(bean.getCreatedBy()!=null && bean.getCreatedBy().length()>0) {
					sql.append(" and created_by='"+bean.getCreatedBy()+"'");
				}
				if(bean.getModifiedBy()!=null && bean.getModifiedBy().length()>0) {
					sql.append(" and modified_by='"+bean.getModifiedBy()+"'");
				}
			}
			if(pageNo>0 && pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				sql.append(" limit "+pageNo+","+pageSize);
			}
			PreparedStatement pst=conn.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()) {
				bean=new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in searching data through course table");
		}
		return list;
	}
	
	public List<CourseBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}
}
