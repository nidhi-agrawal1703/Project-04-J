package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.model.RoleModel;

public class RoleTest {
	
	public static void main(String[] args) throws Exception {
		
		//long nextPk=0;
		//nextPk=nextPk();
		//System.out.println(nextPk);
		
		//RoleBean rb=new RoleBean();
		//rb.setName("Kiosk");
		//rb.setDescription("Access to information of the organization");
		//rb.setCreatedBy("Nidhi");
		//rb.setModifiedBy("Nidhi");
		//rb.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		//rb.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
		//testAdd(rb);
		//testFindByName(rb.getName());
		//testFindByPk(12);
		
		//RoleBean bean=new RoleBean();
		//bean.setId(12);
		//bean.setName("kiosk");
		//bean.setDescription("Access to information of the organization");
		//bean.setCreatedBy("Nidhi");//Need not modify created by details
		//bean.setModifiedBy("Nidhi");
		//rb.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));//Need not modify creation datetime details
		//bean.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
		//testUpdate(bean);
		
		//testDelete(12);
		
		RoleBean bean=new RoleBean();
		bean.setName("Student");
		List list=new ArrayList();
		list=testSearch(bean,1,5);
		Iterator it=list.iterator();
		while(it.hasNext()) {
			bean=(RoleBean)it.next();
			System.out.print(bean.getId());
			System.out.print("\t"+bean.getName());
			System.out.println("\t"+"\t"+bean.getDescription());			
		}
		
	}
	
	public static Long nextPk() throws Exception {
		RoleModel rm=new RoleModel();
		long nextPk=rm.nextPk();
		return nextPk;
	}
	
	public static void testAdd(RoleBean rb)throws Exception{
		RoleModel rm=new RoleModel();
		rm.add(rb);
	}
	
	public static void testFindByName(String name){
		try {
			RoleModel rm=new RoleModel();
			RoleBean bean=rm.findByName(name);
			System.out.println(bean.getName());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			}
		
	}
	public static void testFindByPk(long id){
		try {
			RoleModel rm=new RoleModel();
			RoleBean bean=rm.findByPk(id);
			System.out.println(bean.getName());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			}
		
	}
	
	
	public static void testUpdate(RoleBean bean)throws Exception{
		RoleModel model=new RoleModel();
		model.update(bean);
	}
	
	public static void testDelete(int id)throws Exception {
		RoleModel model=new RoleModel();
		model.delete(id);
	}
	
	public static List testSearch(RoleBean bean,int pageNo,int pageSize)throws Exception{
		RoleModel model=new RoleModel();
		//RoleBean bean1=new RoleBean();
		List list=new ArrayList();
		list=model.search(bean, pageNo, pageSize);
		return list;
	}
}
