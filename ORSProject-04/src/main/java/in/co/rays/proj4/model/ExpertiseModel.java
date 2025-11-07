package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ExpertiseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ExpertiseModel {
	
	public static Long nextPk() throws DatabaseException {
		
		Connection conn=null;
		long nextPk;
		long pk=0;
		
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pstmt=conn.prepareStatement("select max(id) from st_expertise");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				pk=rs.getLong(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			throw new DatabaseException("Exception: Exception in getting PK");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		nextPk=pk+1;
		return nextPk;
	}
	
	public static void add(ExpertiseBean bean) throws ApplicationException, DuplicateRecordException {
		
		Connection conn=null;
		ExpertiseBean existBean=findByName(bean.getName());
		if(existBean!=null) {
			throw new DuplicateRecordException("Expertise Name already exists");
		}
		try {
			long nextPk=nextPk();
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("Insert into st_expertise values(?,?,?,?,?,?,?)");
			pstmt.setLong(1, nextPk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4,"root");
			pstmt.setString(5, "root");
			pstmt.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
			pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			
			int i=pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			System.out.println("Data Inserted: "+i);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception : add rollback exception " + e2.getMessage());
				
			}
			throw new ApplicationException("Exception in adding expertise bean ");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public static void update(ExpertiseBean bean) throws Exception {
		Connection conn=null;
		ExpertiseBean duplicateRole=findByName(bean.getName());
		if(duplicateRole!=null && duplicateRole.getId()!=bean.getId()) {
			throw new Exception("Expertise name already exists");
		}
		
		
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pstmt=conn.prepareStatement("update st_expertise set name=?,description=? where id=?");
			conn.setAutoCommit(false);
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setLong(3, bean.getId());
			int i=pstmt.executeUpdate();
			conn.commit();
			System.out.println("DAta updated: "+i);
			
			pstmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception in rollback of update"+e2.getMessage());
			}
			throw new ApplicationException("Exception in updating data");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public static void delete(long id) throws ApplicationException {
		Connection conn=null;
		
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from  st_expertise where id=?");
			pstmt.setLong(1, id);
			int i=pstmt.executeUpdate();
			conn.commit();
			System.out.println("Data deleted :"+i);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception in rollback of deletion of data"+e2.getMessage());
			}
			throw new ApplicationException("Exception in deletion of data");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public static void delete(ExpertiseBean bean) throws ApplicationException {
		Connection conn=null;
		
		try {
			conn=JDBCDataSource.getconnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_expertise where id=?");
			pstmt.setLong(1, bean.getId());
			int i=pstmt.executeUpdate();
			conn.commit();
			System.out.println("Data deleted :"+i);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("Exception in rollback of deletion of data"+e2.getMessage());
			}	
			throw new ApplicationException("Exception in deletion of data");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public static List search(ExpertiseBean bean,int pageNo,int pageSize) {
		Connection conn=null;
		List list=new ArrayList();
		try {
			conn=JDBCDataSource.getconnection();
			StringBuffer sql=new StringBuffer("select * from st_expertise where 1=1 ");
			if(bean!=null) {
				if(bean.getId()>0) {sql.append("and id="+bean.getId());}
				if(bean.getName()!=null && bean.getName().length()>0) {sql.append("and name like'"+bean.getName()+"%'");}
				if(bean.getDescription()!=null && bean.getDescription().length()>0) {sql.append("and description like'"+bean.getDescription()+"%'");}
			}
			if(pageNo>0 && pageSize>0) {
				int index=(pageNo-1)*pageSize;
				sql.append(" limit "+index+","+pageSize);
			}
			System.out.println("SQL query: "+sql.toString());
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean=new ExpertiseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				list.add(bean);
			}
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return list;
	}
	
	public static List list() {
		return search(null,0,0);
	}
	
	public static ExpertiseBean findByName(String name) throws ApplicationException {
		Connection conn=null;
		ExpertiseBean bean=null;
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from st_expertise where name=?");
			pstmt.setString(1, name);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				bean=new ExpertiseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting User by Role");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
		
	}
	public static ExpertiseBean findByPk(long id) throws ApplicationException {
		Connection conn=null;
		ExpertiseBean bean=null;
		try {
			conn=JDBCDataSource.getconnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from st_expertise where id=?");
			pstmt.setLong(1, id);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				bean=new ExpertiseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting User by Role");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
		
	}
}
