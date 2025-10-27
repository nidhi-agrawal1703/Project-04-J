package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.model.UserModel;

public class UserTest {
	public static void main(String[] args)throws Exception {
		
		//int nextPk=testNextPk();
		//System.out.println("Next pk: "+nextPk);
		
		//testFindByLogin("nidhi@gmail.com");
		//testFindByPk(3);
		
		//UserBean bean=new UserBean();
		//SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		//bean.setFirstName("Chayan");
		//bean.setLastName("Garg");
		//bean.setLogin("chayan@gmail.com");
		//bean.setPassword("123");
		//bean.setConfirmPassword("123");
		//bean.setDob(sdf1.parse("1950-07-17"));
		//bean.setMobileNo("8456123789");
		//bean.setRoleId(1);
		//bean.setGender("male");
		//bean.setCreatedBy("Nidhi");
		//bean.setModifiedBy("Nidhi");
		//bean.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		//bean.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
		//testAdd(bean);
		
		//testDelete(1);
		
		//UserBean bean1=new UserBean();
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//bean1.setId(6);
		//bean1.setFirstName("Mamta");
		//bean1.setLastName("Garg");
		//bean1.setLogin("mamta@gmail.com");
		//bean1.setPassword("123");
		//bean1.setConfirmPassword("123");
		//bean1.setDob(sdf.parse("1990-05-15"));
		//bean1.setMobileNo("8456123789");
		//bean1.setRoleId(4);
		//bean1.setGender("female");
		//bean1.setCreatedBy("Nidhi");
		//bean1.setModifiedBy("Nidhi");
		//bean1.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		//bean1.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
		//testUpdate(bean1);
		
		//UserBean bean=testFindByLogin("nidhi@gmail.com");
		//System.out.print(bean.getId());
		//System.out.print("\t"+bean.getFirstName());
		//System.out.print("\t"+bean.getLastName());
		//System.out.print("\t"+bean.getLogin());
		//System.out.print("\t"+bean.getPassword());
		//System.out.print("\t"+bean.getDob());
		//System.out.print("\t"+bean.getMobileNo());
		//System.out.print("\t"+bean.getRoleId());
		//System.out.print("\t"+bean.getGender());
		//System.out.print("\t"+bean.getCreatedBy());
		//System.out.print("\t"+bean.getModifiedBy());
		//System.out.print("\t"+bean.getCreatedDateTime());
		//System.out.println("\t"+bean.getModifiedDateTime());
		
		UserBean bean2=new UserBean();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//bean2.setId(4);
		//bean2.setFirstName("Nidhi");
		//bean2.setLastName("Agrawal");
				//bean2.setLogin("mamta@gmail.com");
				//bean1.setPassword("123");
				//bean1.setConfirmPassword("123");
				//bean1.setDob(sdf.parse("1990-05-15"));
				//bean1.setMobileNo("8456123789");
				//bean1.setRoleId(4);
				//bean1.setGender("female");
				//bean1.setCreatedBy("Nidhi");
				//bean1.setModifiedBy("Nidhi");
				//bean1.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
				//bean1.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));		
		List list=testSearch(bean2,1,5);
		Iterator it =list.iterator();
		while(it.hasNext()) {
			UserBean bean1=(UserBean)it.next();
			System.out.print(bean1.getId());
			System.out.print("\t"+bean1.getFirstName());
			System.out.print("\t"+bean1.getLastName());
			//System.out.print("\t"+bean1.getLogin());
			//System.out.print("\t"+bean1.getPassword());
			//System.out.print("\t"+bean1.getDob());
			//System.out.print("\t"+bean1.getMobileNo());
			//System.out.print("\t"+bean1.getRoleId());
			//System.out.print("\t"+bean1.getGender());
			//System.out.print("\t"+bean1.getCreatedBy());
			//System.out.print("\t"+bean1.getModifiedBy());
			//System.out.print("\t"+bean1.getCreatedDateTime());
			//System.out.println("\t"+bean1.getModifiedDateTime());
			
		}
		
		//UserBean bean2=null;
		//UserBean bean2=testAuthenticate("nidhi123@gmail.com", "12345");
		//System.out.println(bean2);
		
	//	if(bean2!=null) {
			
		//	System.out.println("Successfully logged in");
		//	}else 
		//	{
		//		System.out.println("Please enter login id and password correct");}
		
		

		
	}
	
	public static UserBean testAuthenticate(String login,String password) {
		try {
			UserModel model=new UserModel();
			//UserBean bean=new UserBean();
			UserBean bean=model.authenticate(login, password);
			return bean;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public static Long testNextPk() throws Exception {
		UserModel model=new UserModel();
		long nextPk=model.nextPk();
		return nextPk;
	}
	
	public static void testAdd(UserBean bean)throws Exception{
		UserModel model=new UserModel();
		model.add(bean);
	}
	
	public static void testUpdate(UserBean bean)throws Exception{
		UserModel model=new UserModel();
		model.update(bean);
	}
	
	public static void testDelete(int id)throws Exception{
		UserModel model=new UserModel();
		model.delete(id);
	}
public static void testFindByPk(long id) throws Exception{
		
		UserModel model=new UserModel();
		UserBean bean=model.findByPk(id);
		if(bean!=null) {
			System.out.println(bean.getLastName());
		}else {
			System.out.println("Data not found");
	}		
		}
	public static void testFindByLogin(String login) throws Exception{
		
		UserModel model=new UserModel();
		UserBean bean=model.findByLogin(login);
		if(bean!=null) {
			System.out.println(bean.getLastName());
		}else {
			System.out.println("Data not found");
	}		
		}
	public static List testSearch(UserBean bean,int pageNo,int pageSize) throws Exception{
		UserModel model=new UserModel();
		List list=(ArrayList)model.search(bean, pageNo,pageSize);
		return list;
	}
}
