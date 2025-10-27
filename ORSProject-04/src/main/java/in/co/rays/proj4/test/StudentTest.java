package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.model.StudentModel;

public class StudentTest {
	public static void main(String[] args)throws Exception {
			
		//long nextPk=testNextPk();
		//System.out.println(nextPk);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		StudentBean bean=new StudentBean();
		bean.setFirstName("Ram");
		bean.setLastName("Agrawal");
		bean.setDob(sdf.parse("2008-08-23"));
		bean.setGender("male");
		bean.setMobileNo("9876543210");
		bean.setEmail("ram@gmail.com");
		bean.setCollegeId(3);
		bean.setCreatedBy("Nidhi");
		bean.setModifiedBy("Nidhi");
		bean.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
		testAdd(bean);
		
		//SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		//StudentBean bean1=new StudentBean();
		//bean1.setId(10);
		//bean1.setFirstName("Ishika");
		//bean1.setLastName("Nair");
		//bean1.setDob(sdf.parse("2002-12-03"));
		//bean1.setGender("female");
		//bean1.setMobileNo("9954321786");
		//bean1.setEmail("ishika@gmail.com");
		//bean1.setCollegeId(9);
		//bean.setCreatedBy("Nidhi");
		//bean1.setModifiedBy("Nidhi");
		//bean.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		//bean1.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
		//testUpdate(bean1);
		
		//testDelete(10);
		
		//List list=new ArrayList();
		//bean1.setCollegeName("SGSITS");
		//list=testSearch(bean1,1,5);
		
		//StudentBean bean2=new StudentBean();
		//bean2=testFindByPk(3);
		//System.out.println(bean2.getFirstName()+bean2.getLastName());
		
		//testFindByPk(9);
		
	}
	
	public static Long testNextPk()throws Exception{
		StudentModel model=new StudentModel();
		long nextPk=model.nextPk();
		return nextPk;
	}
	
	public static void testAdd(StudentBean bean) throws Exception {
		
		StudentModel model=new StudentModel();
		model.add(bean);
	}
	
	public static void testUpdate(StudentBean bean)throws Exception{
		StudentModel model=new StudentModel();
		model.update(bean);
	}
	
	public static void testDelete(long id)throws Exception{
		StudentModel model=new StudentModel();
		model.delete(id);
	}
	
	public static List testSearch(StudentBean bean,int pageNo,int pageSize) throws Exception{
		StudentModel model=new StudentModel();
		List list=new ArrayList();
		list=model.search(bean, pageNo, pageSize);
		Iterator it=list.iterator();
		
		while(it.hasNext()) {
			bean=(StudentBean)it.next();
			System.out.print(bean.getId());
			System.out.print("\t"+bean.getFirstName());
			System.out.print("\t"+bean.getLastName());
			System.out.print("\t"+bean.getDob());
			System.out.print("\t"+bean.getGender());
			System.out.print("\t"+bean.getMobileNo());
			System.out.print("\t"+bean.getEmail());
			System.out.println("\t"+bean.getCollegeName());
		}
	
		return list;
	}
	
	public static void testFindByPk(long pk) throws Exception{
		StudentModel model=new StudentModel();
		StudentBean bean=new StudentBean();
		bean=model.findByPk(pk);		
		System.out.println(bean.getFirstName()+" "+bean.getLastName());
	}
}
