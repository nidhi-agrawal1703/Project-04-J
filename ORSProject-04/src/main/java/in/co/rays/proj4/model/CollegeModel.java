package in.co.rays.proj4.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 *JDBC implementation of CollegeModel
 *Perform CRUD operation of college bean.
 *
 * @author Nidhi
 * @version 1.0
 */
public class CollegeModel {
	
	
	public static Long nextPk() throws DatabaseException {
		Connection conn=null;
		long pk=0;
		try {
			
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst=conn.prepareStatement("select max(id) from st_college");
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				pk=rs.getInt(1);
			}
			rs.close();
			pst.close();			
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}
	
	public static CollegeBean findByPk(long id) throws ApplicationException {
		Connection conn=null;
		CollegeBean bean=null;
		try {
			conn=JDBCDataSource.getconnection();
			StringBuffer sql=new StringBuffer("select * from st_college where id=?");
			PreparedStatement pst=conn.prepareStatement(sql.toString());
			pst.setLong(1, id);
			System.out.println(sql.toString());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()) {
				bean=new CollegeBean();
				bean.setId(rs.getLong(1));
				//System.out.println(rs.getString(2));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhone_no(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException("Exception : Exception in getting College by pk");			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	public static CollegeBean findByName(String name) throws ApplicationException {
		Connection conn=null;
		CollegeBean bean=null;
		try {
			conn=JDBCDataSource.getconnection();
			StringBuffer sql=new StringBuffer("select * from st_college where name=?");
			PreparedStatement pst=conn.prepareStatement(sql.toString());
			pst.setString(1, name);
			//System.out.println(sql.toString());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()) {
				bean=new CollegeBean();
				bean.setId(rs.getLong(1));
				//System.out.println(rs.getString(2));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhone_no(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException("Exception : Exception in getting College by Name");			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		if(bean!=null) {System.out.println(bean.getPhone_no());}
		
		return bean;
	}

	
	public static Long add(CollegeBean bean) throws Exception {
		Connection conn=null;
		//CollegeBean bean1=null;
		CollegeBean existBean=findByName(bean.getName());
		if(existBean!=null) {
			throw new DuplicateRecordException("College name already exists");
		}
		long nextPk=0;
		nextPk=nextPk();
		try {
			
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("insert into st_college values(?,?,?,?,?,?,?,?,?,?)");
			pst.setLong(1, nextPk);
			pst.setString(2,bean.getName());
			pst.setString(3,bean.getAddress());
			pst.setString(4,bean.getState());
			pst.setString(5,bean.getCity());
			pst.setString(6,bean.getPhone_no());
			pst.setString(7,bean.getCreatedBy());
			pst.setString(8,bean.getModifiedBy());
			pst.setTimestamp(9, bean.getCreatedDateTime());
			pst.setTimestamp(10, bean.getModifiedDateTime());
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("Data inserted: "+i);
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return nextPk;
	}
	
	public static void delete(int id) throws ApplicationException {
		Connection conn=null;
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("delete from st_college where id=?");
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
				throw new ApplicationException("Exception : Delete rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete college");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public void delete(CollegeBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_college where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public static void update(CollegeBean bean) throws DuplicateRecordException,ApplicationException {
		Connection conn=null;
		CollegeBean existBean=findByName(bean.getName());
		if(existBean!=null && existBean.getId()!=bean.getId()) {
			throw new DuplicateRecordException("College name already exists");
		}
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("update st_college set name=?,address=?,state=?,city=?,phone_no=?,modified_by=?,modified_datetime=? where id=?");
			pst.setString(1, bean.getName());
			pst.setString(2, bean.getAddress());
			pst.setString(3, bean.getState());
			pst.setString(4,bean.getCity());
			pst.setString(5,bean.getPhone_no());
			pst.setString(6, bean.getModifiedBy());
			pst.setTimestamp(7, bean.getModifiedDateTime());
			pst.setLong(8, bean.getId());
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
			throw new ApplicationException("Exception in updating College ");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public static List search(CollegeBean bean,int pageNo,int pageSize) throws ApplicationException {
		Connection conn=null;
		List list=new ArrayList();
		try {
			conn=JDBCDataSource.getconnection();
			StringBuffer sql=new StringBuffer("select * from st_college where 1=1");
			if(bean!=null) {
				if(bean.getId()>0) {
					sql.append(" and id="+bean.getId());
				}
				if(bean.getName()!=null && bean.getName().length()>0) {
					sql.append(" and name like'"+bean.getName()+"%'");
				}
				if(bean.getAddress()!=null && bean.getAddress().length()>0) {
					sql.append(" and address='"+bean.getAddress()+"'");
				}
				if(bean.getState()!=null && bean.getState().length()>0) {
					sql.append(" and state='"+bean.getState()+"'");
				}
				if(bean.getCity()!=null && bean.getCity().length()>0) {
					sql.append(" and city='"+bean.getCity()+"'");
				}
				if(bean.getPhone_no()!=null && bean.getPhone_no().length()>0) {
					sql.append(" and phone_no='"+bean.getPhone_no()+"'");
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
			ResultSet rs= pst.executeQuery();
			
			while(rs.next()) {
				bean=new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhone_no(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException("Exception : Exception in search college");		
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	
	public List<CollegeBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}


}
