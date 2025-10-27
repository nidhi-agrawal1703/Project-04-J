package in.co.rays.proj4.model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * JDBC implementation of FacultyModel
 * Perform CRUD operation of FacultyBean.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class FacultyModel {
	
	public static Long nextPk() {
		Connection conn=null;
		long pk=0;
		try {			
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst=conn.prepareStatement("select max(id) from st_faculty");
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				pk=rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}
	
	public static FacultyBean findByEmail(String email) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_faculty where email = ?");
		FacultyBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCourseId(rs.getLong(10));
				bean.setCourseName(rs.getString(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Faculty by Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	public List<FacultyBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public FacultyBean findByPk(long pk) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_faculty where id = ?");
		FacultyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getconnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCourseId(rs.getLong(10));
				bean.setCourseName(rs.getString(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Faculty by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	
	public static Long add(FacultyBean bean)throws ApplicationException, DuplicateRecordException {
		FacultyBean existBean=findByEmail(bean.getEmail());
		if(existBean!=null) {
			throw new DuplicateRecordException("Email id already exists");
		}
		
		Connection conn=null;
		CollegeModel collegeModel=new CollegeModel();
		CollegeBean collegeBean=new CollegeBean();
		collegeBean=collegeModel.findByPk(bean.getCollegeId());
		String collegeName=collegeBean.getName();
		
		CourseModel courseModel=new CourseModel();
		CourseBean courseBean=new CourseBean();
		courseBean=courseModel.findByPk(bean.getCourseId());
		String courseName=courseBean.getName();
		
		SubjectModel subjectModel=new SubjectModel();
		SubjectBean subjectBean=new SubjectBean();
		subjectBean=subjectModel.findByPk(bean.getSubjectId());
		String subjectName=subjectBean.getName();
		long nextPk=nextPk();
		try {
								
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("insert into st_faculty values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			pst.setLong(1, nextPk);
			pst.setString(2, bean.getFirstName());
			pst.setString(3, bean.getLastName());
			pst.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pst.setString(5, bean.getGender());
			pst.setString(6, bean.getMobileNo());
			pst.setString(7, bean.getEmail());
			pst.setLong(8, bean.getCollegeId());
			pst.setString(9, collegeName);
			pst.setLong(10, bean.getCourseId());
			pst.setString(11, courseName);
			pst.setLong(12, bean.getSubjectId());
			pst.setString(13, subjectName);
			pst.setString(14, bean.getCreatedBy());
			pst.setString(15, bean.getModifiedBy());
			pst.setTimestamp(16, bean.getCreatedDateTime());
			pst.setTimestamp(17, bean.getModifiedDateTime());;
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("DAta inserted: "+i);
			
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception in rollback "+e2.getMessage());
			}
			throw new ApplicationException("Exception in inserting data in faculty table "+e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return nextPk;
	}
	
	public static void update(FacultyBean bean)throws ApplicationException {
		Connection conn=null;
		
		CollegeModel collegeModel=new CollegeModel();
		CollegeBean collegeBean=new CollegeBean();
		collegeBean=collegeModel.findByPk(bean.getCollegeId());
		String collegeName=collegeBean.getName();
		System.out.println(collegeName);
		
		CourseModel courseModel=new CourseModel();
		CourseBean courseBean=new CourseBean();
		courseBean=courseModel.findByPk(bean.getCourseId());
		String courseName=courseBean.getName();
		System.out.println(courseName);
		
		SubjectModel subjectModel=new SubjectModel();
		SubjectBean subjectBean=new SubjectBean();
		subjectBean=subjectModel.findByPk(bean.getSubjectId());
		String subjectName=subjectBean.getName();
		//System.out.println("Hi first");
		System.out.println(subjectName);
		
		try {			
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("update st_faculty set first_name=?,last_name=?,dob=?,gender=?,mobile_no=?,email=?,college_id=?,college_name=?,course_id=?,course_name=?,subject_id=?,subject_name=?,modified_by=?,modified_datetime=? where id=?");
			pst.setString(1, bean.getFirstName());
			//pst.setString(2, bean.getFirstName());
			pst.setString(2, bean.getLastName());			
			pst.setDate(3, new java.sql.Date(bean.getDob().getTime()));			
			pst.setString(4, bean.getGender());
			pst.setString(5, bean.getMobileNo());
			pst.setString(6, bean.getEmail());
			pst.setLong(7, bean.getCollegeId());
			pst.setString(8, collegeName);
			pst.setLong(9, bean.getCourseId());
			pst.setString(10, courseName);
			pst.setLong(11, bean.getSubjectId());
			pst.setString(12, subjectName);
			//pst.setString(13, bean.getCreatedBy());
			pst.setString(13, bean.getModifiedBy());			
			//pst.setTimestamp(15, bean.getCreatedDateTime());
			pst.setTimestamp(14, bean.getModifiedDateTime());
			//System.out.println("Hi middle");
			pst.setLong(15, bean.getId());
			//System.out.println("Hi last");
			conn.commit();
			pst.close();
			int i=pst.executeUpdate();
			System.out.println("DAta updated: "+i);

		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception in rollback update data "+e2.getMessage());
			}
			throw new ApplicationException("Exception in updating data "+e.getMessage());
		}
	} 
	public void delete(FacultyBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_faculty where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public static void delete(long id) throws ApplicationException {
		Connection conn=null;
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("delete from st_faculty where id=?");
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
				throw new ApplicationException("Exception in rollback delete data "+e2.getMessage());
			}
			throw new ApplicationException("Exception in deleting data "+e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public static List search(FacultyBean bean,int pageNo,int pageSize ) throws ApplicationException {
		Connection conn=null;
		List list=new ArrayList();
		try {
			conn=JDBCDataSource.getconnection();
			StringBuffer sql=new StringBuffer("select * from st_faculty where 1=1");
			if(bean!=null) {
				if(bean.getId()>0) {
					sql.append(" and id="+bean.getId());
				}
				if(bean.getFirstName()!=null && bean.getFirstName().length()>0) {
					sql.append(" and first_name like '"+bean.getFirstName()+"%'");
				}
				if(bean.getLastName()!=null && bean.getLastName().length()>0) {
					sql.append(" and last_name like '"+bean.getLastName()+"%'");
				}
				if(bean.getDob()!=null) {
					sql.append(" and dob="+bean.getDob());
				}
				if(bean.getGender()!=null && bean.getGender().length()>0) {
					sql.append(" and gender='"+bean.getGender()+"'");
				}
				if(bean.getMobileNo()!=null && bean.getMobileNo().length()>0) {
					sql.append(" and mobile_no='"+bean.getMobileNo()+"'");
				}
				if(bean.getEmail()!=null && bean.getEmail().length()>0) {
					sql.append(" and email='"+bean.getEmail()+"'");
				}
				if(bean.getCollegeId()>0) {
					sql.append(" and college_id="+bean.getCollegeId());
				}
				if(bean.getCollegeName()!=null && bean.getCollegeName().length()>0) {
					sql.append(" and college_name='"+bean.getCollegeName()+"'");}
				if(bean.getCourseId()<0) {
					sql.append(" and course_id="+bean.getCourseId());
				}
				if(bean.getCourseName()!=null && bean.getCourseName().length()>0) {
					sql.append(" and course_name='"+bean.getCourseName()+"'");
				}
				if(bean.getSubjectId()>0) {
					sql.append(" and subject_id="+bean.getSubjectId());
				}
				if(bean.getSubjectName()!=null && bean.getSubjectName().length()>0) {
					sql.append(" and subject_name='"+bean.getSubjectName()+"'");
				}
				//if() {}
				//if() {}
				//if() {}
			}
			if(pageNo>0 && pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				sql.append(" limit "+pageNo+","+pageSize);
			}
			
			PreparedStatement pst=conn.prepareStatement(sql.toString());
			System.out.println(sql);
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				bean=new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCourseId(rs.getLong(10));
				bean.setCourseName(rs.getString(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));
				list.add(bean);	
			}
			rs.close();
			pst.close();		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search Subject");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	
}
