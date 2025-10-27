package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.model.MarksheetModel;

public class MarksheetTest {
	public static void main(String[] args) {
		try {
			//long nextPk=0;
			//nextPk=testNextPk();
			//System.out.println(nextPk);
			
			//String name;
			//name=testFindName(1);
			//System.out.println(name);
			
			MarksheetBean bean=new MarksheetBean();
			bean.setRollNo("108");
			bean.setStudent_id(11);
			bean.setPhysics(76);
			bean.setChemistry(43);
			bean.setMaths(98);
			bean.setCreatedBy("Nidhi");
			bean.setModifiedBy("Krishna");
			bean.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
			bean.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
			//testAdd(bean);
			//bean.setId(8);
			//testUpdate(bean);
			
			//testDelete(8);
			
			//bean=testFindByRollNo("101");
			System.out.print(bean.getId());
			System.out.print("\t"+bean.getRollNo());
			System.out.print("\t"+bean.getStudentId());
			System.out.print("\t"+bean.getName());
			System.out.print("\t"+bean.getPhysics());
			System.out.print("\t"+bean.getChemistry());
			System.out.print("\t"+bean.getMaths());
			System.out.print("\t"+bean.getCreatedBy());
			System.out.print("\t"+bean.getModifiedBy());
			System.out.print("\t"+bean.getCreatedDateTime());
			System.out.print("\t"+bean.getModifiedDateTime());
			
			List list=new ArrayList();
			MarksheetBean bean1=new MarksheetBean();
			//bean1.setRollNo("107");
			//bean1.setStudent_id(9);
			//bean1.setPhysics(99);
			bean1.setChemistry(78);
			//bean1.setMaths(56);
			//bean.setCreatedBy("Nidhi");
			//bean1.setModifiedBy("Krishna");
			//bean.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
			//bean1.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
			//testAdd(bean);
			//bean1.setId(7);
			//testUpdate(bean);
			
			list=testSearch(bean1,1,5);
			
			Iterator it=list.iterator();
			while(it.hasNext()) {
				bean1=(MarksheetBean)it.next();
				System.out.print(bean1.getId());
				System.out.print("\t"+bean1.getRollNo());
				System.out.print("\t"+bean1.getStudentId());
				System.out.print("\t"+bean1.getName());
				System.out.print("\t"+bean1.getPhysics());
				System.out.print("\t"+bean1.getChemistry());
				System.out.print("\t"+bean1.getMaths());
				System.out.print("\t"+bean1.getCreatedBy());
				System.out.print("\t"+bean1.getModifiedBy());
				System.out.print("\t"+bean1.getCreatedDateTime());
				System.out.println("\t"+bean1.getModifiedDateTime());

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Long testNextPk() {
		try {
			MarksheetModel model=new MarksheetModel();
			long nextPk=model.nextPk();
			return nextPk;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String testFindName(long id) {
		try {
			MarksheetModel model=new MarksheetModel();
			String name=model.findName(id);
			return name;	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void testAdd(MarksheetBean bean) {
		try {
			MarksheetModel model=new MarksheetModel();
			model.add(bean);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public static void testUpdate(MarksheetBean bean) {
		try {
			MarksheetModel model=new MarksheetModel();
			model.update(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void testDelete(long id) {
		try {
			MarksheetModel model=new MarksheetModel();
			model.delete(id);;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static MarksheetBean testFindByRollNo(String rollno) {
		try {
			MarksheetModel model=new MarksheetModel();
			MarksheetBean bean=model.findByRollNo(rollno);
			return bean;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public static List testSearch(MarksheetBean bean,int pageNo,int pageSize) {
		try {
			MarksheetModel model=new MarksheetModel();
			List list=new ArrayList();
			list=model.search(bean,pageNo,pageSize);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
