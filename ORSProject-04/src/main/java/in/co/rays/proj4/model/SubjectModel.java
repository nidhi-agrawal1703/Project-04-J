package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * JDBC implementation of SubjectModel.
 * Performs CRUD operations of SubjectBean.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class SubjectModel {

	public static Long nextPk() throws DatabaseException {
		Connection conn=null;
		long pk=0;
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst=conn.prepareStatement("select max(id) from st_subject");
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				pk=rs.getInt(1);
			}			
			rs.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			throw new DatabaseException("Exception in getting pk");
			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}
	
	public List<SubjectBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	
	public static SubjectBean findByPk(long id) throws ApplicationException {
		Connection conn=null;
		SubjectBean bean=null;
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst=conn.prepareStatement("select * from st_subject where id=?");
			pst.setLong(1, id);
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				bean=new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception in finding subject by pk");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	} 
	
	public static SubjectBean findByName(String name) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_subject where name = ?");
		SubjectBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong("id"));
	            bean.setName(rs.getString("name"));
	            bean.setCourseId(rs.getLong("course_id"));
	            bean.setCourseName(rs.getString("course_name"));
	            bean.setDescription(rs.getString("description"));
	            bean.setCreatedBy(rs.getString("created_by"));
	            bean.setModifiedBy(rs.getString("modified_by"));
	            bean.setCreatedDateTime(rs.getTimestamp("created_datetime"));
	            bean.setModifiedDateTime(rs.getTimestamp("modified_datetime"));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Subject by Subject Name"+e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	public static long add(SubjectBean bean) throws ApplicationException,DuplicateRecordException, DatabaseException {
		Connection conn=null;
		
		CourseModel courseModel=new CourseModel();
		CourseBean cBean=courseModel.findByPk(bean.getCourseId());
		String courseName=cBean.getName();
		//System.out.println(cBean.getName());
		
		SubjectBean duplicateBean=findByName(bean.getName());
		if(duplicateBean!=null) {
			throw new DuplicateRecordException("Subject name already exists");
		}
		long nextPk=nextPk();
		try {
						
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("insert into st_subject values(?,?,?,?,?,?,?,?,?)");
			pst.setLong(1, nextPk);
			pst.setString(2, bean.getName());
			pst.setLong(3, bean.getCourseId());
			pst.setString(4, courseName);
			pst.setString(5, bean.getDescription());
			pst.setString(6, bean.getCreatedBy());
			pst.setString(7, bean.getModifiedBy());
			pst.setTimestamp(8, bean.getCreatedDateTime());
			pst.setTimestamp(9, bean.getModifiedDateTime());
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("Data inserted: "+i);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : add rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception in inserting subject into database");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return nextPk;
	} 
	
	public static void update(SubjectBean bean) throws ApplicationException,DuplicateRecordException {
		Connection conn=null;
		
		SubjectBean duplicateBean=findByName(bean.getName());
		if(duplicateBean!=null && duplicateBean.getId()!=bean.getId()) {
			throw new DuplicateRecordException("Subject name already exists");
		}
		CourseModel cModel=new CourseModel();
		CourseBean courseBean=cModel.findByPk(bean.getCourseId());
		String courseName=courseBean.getName();

		try {						
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("update st_subject set name=?,course_id=?,course_name=?,description=?,modified_by=?,modified_datetime=? where  id=?");
			pst.setString(1, bean.getName());
			pst.setLong(2, bean.getCourseId());
			pst.setString(3, courseName);
			pst.setString(4, bean.getDescription());
			pst.setString(5, bean.getModifiedBy());
			pst.setTimestamp(6, bean.getModifiedDateTime());
			pst.setLong(7, bean.getId());
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("Data updated: "+i);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : Delete rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception in updating database ");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		
	}
	
	public static void delete(long id)throws ApplicationException {
		Connection conn=null;
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("delete from st_subject where id=?");
			pst.setLong(1, id);
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("DAta deleted: "+i);
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : Delete rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception in deletion of data");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	public void delete(SubjectBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getconnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("delete from st_subject where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Subject");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public static List search(SubjectBean bean,int pageNo,int pageSize) throws ApplicationException{
		Connection conn=null;
		List list=new ArrayList();
		try {
			conn=JDBCDataSource.getconnection();
			StringBuffer sql=new StringBuffer("select * from st_subject where 1=1");
			if(bean!=null) {
				if(bean.getId()>0) {
					sql.append(" and id="+bean.getId());
				}
				if(bean.getName()!=null && bean.getName().length()>0) {
					sql.append(" and name like '"+bean.getName()+"%'");
				}
				if(bean.getCourseId()>0) {
					sql.append(" and course_id="+bean.getCourseId());
				}
				if(bean.getCourseName()!=null && bean.getCourseName().length()>0) {
					sql.append(" and course_name='"+bean.getCourseName()+"'");
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
			System.out.println(sql.toString());
			PreparedStatement pst=conn.prepareStatement(sql.toString());
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				bean=new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(8));
				list.add(bean);	
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException("Exception : Exception in search Subject");
			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	
	
}
