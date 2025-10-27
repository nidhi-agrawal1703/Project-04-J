package in.co.rays.proj4.model;


import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implmentation of RoleModel.
 * Performs CRUD operations of RoleBean.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class RoleModel {
	
	public static Long nextPk() throws DatabaseException {
		Connection conn=null;
		long nextPk;
		long pk=0;
		try {
			
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst =conn.prepareStatement("select max(id) from st_role");
			ResultSet rs=pst.executeQuery();
			while (rs.next()) {
				pk=rs.getInt(1);
			}
			rs.close();
			pst.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new DatabaseException("Exception: Exception in getting PK");
			}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		nextPk=pk+1;
		return nextPk;	
	}
	
	public static RoleBean findByName(String name) throws ApplicationException{
		Connection conn=null;
		RoleBean bean=null;
		try {
			
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst =conn.prepareStatement("select * from st_role where name=?");
			pst.setString(1, name);
			ResultSet rs=pst.executeQuery();
			while (rs.next()) {
				bean=new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
			}
				rs.close();
				pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException("Exception : Exception in getting User by Role");
		
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;

	}
	public List<RoleBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public static RoleBean findByPk(long id) throws ApplicationException {
		Connection conn=null;
		RoleBean bean=null;
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pst =conn.prepareStatement("select * from st_role where id=?");
			pst.setLong(1, id);
			ResultSet rs=pst.executeQuery();
			while (rs.next()) {
				bean=new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
			}	
			rs.close();
			pst.close();

		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException("Exception : Exception in getting User by pk");
			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	public static void add(RoleBean rb) throws ApplicationException, DuplicateRecordException{
		Connection conn=null;
		
		RoleBean existBean=findByName(rb.getName());
		//System.out.println(rb.getName());
		if(existBean!=null) {
			throw new DuplicateRecordException("Role already exists");
		}
		
		try {
			long nextPk=nextPk();
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
			pst.setLong(1, nextPk);
			pst.setString(2, rb.getName());
			pst.setString(3, rb.getDescription());
			pst.setString(4,rb.getCreatedBy());
			pst.setString(5, rb.getModifiedBy());
			pst.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
			pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("DAta inserted :"+i);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : add rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Role");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
			}
	
	public static void update(RoleBean bean) throws Exception{
		Connection conn=null;
		RoleBean duplicateRole=findByName(bean.getName());
		if(duplicateRole!=null && duplicateRole.getId()!=bean.getId()) {
			throw new Exception("Role already exists");
		}
		
		try {
			
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("update st_role set name=?,description=?,modified_by=?,modified_datetime=? where id=?");
			pst.setString(1, bean.getName());
			pst.setString(2, bean.getDescription());
			//pst.setString(3, bean.getCreatedBy());
			pst.setString(3, bean.getModifiedBy());
			pst.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
			pst.setLong(5, bean.getId());		
			int i=pst.executeUpdate();
			conn.commit();
			pst.close();
			System.out.println("Data updated: "+i);
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : Update rollback exception " + e2.getMessage());	
			}
			throw new ApplicationException("Exception in updating Role");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
			}
	
		
	public static void delete(int id) throws ApplicationException{
		Connection conn=null;
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pst=conn.prepareStatement("delete from st_role where id=?");
			pst.setLong(1, id);
			int i=pst.executeUpdate();
			System.out.println("Data deleted: "+i);
			conn.commit();
			pst.close();
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : Delete rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Role");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}	
	}
	
	public void delete(RoleBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_role where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	public static List search(RoleBean bean,int pageNo,int pageSize) throws ApplicationException{
		Connection conn=null;
		List list=new ArrayList();
		try {
			conn=JDBCDataSource.getconnection();
			StringBuffer sql=new StringBuffer("select * from st_role where 1=1");
			if(bean!=null) {
				if(bean.getId()>0) {
					sql.append(" and id="+bean.getId());
				}
				if(bean.getName()!=null && bean.getName().length()>0) {
					sql.append(" and name like'"+bean.getName()+"%'");
				}
				if(bean.getDescription()!=null && bean.getDescription().length()>0) {
					sql.append(" and description like'"+bean.getDescription()+"%'");
				}
				if(bean.getCreatedBy()!=null && bean.getCreatedBy().length()>0) {
					sql.append(" and created_by='"+bean.getCreatedBy()+"'");
				}
				if(bean.getModifiedBy()!=null && bean.getModifiedBy().length()>0) {
					sql.append(" and modified_by='"+bean.getModifiedBy()+"'");
				}
				if(bean.getCreatedDateTime()!=null) {
					sql.append(" and created_datetime='"+bean.getCreatedDateTime()+"'");
				}
				if(bean.getModifiedDateTime()!=null) {
					sql.append(" and modified_datetime='"+bean.getModifiedDateTime()+"'");
				}
			}
			if(pageNo>0 && pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				sql.append(" limit "+pageNo+","+pageSize);
			}
			System.out.println("SQL query: "+sql.toString());
			PreparedStatement pst=conn.prepareStatement(sql.toString());
			ResultSet set=pst.executeQuery();
			
			while(set.next()) {
				bean=new RoleBean();
				bean.setId(set.getLong(1));
				bean.setName(set.getString(2));
				bean.setDescription(set.getString(3));
				bean.setCreatedBy(set.getString(4));
				bean.setModifiedBy(set.getString(5));
				bean.setCreatedDateTime(set.getTimestamp(6));
				bean.setModifiedDateTime(set.getTimestamp(7));
				list.add(bean);
			}
			set.close();
			pst.close();
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
			}
}
