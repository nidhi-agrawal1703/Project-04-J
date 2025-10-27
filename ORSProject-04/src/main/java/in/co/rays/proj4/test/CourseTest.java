package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.model.CourseModel;

public class CourseTest {
	public static void main(String[] args) {
		
		try {
			//long nextPk=testNextPk();
			//System.out.println(nextPk);
			
			CourseBean bean=new CourseBean();
			bean.setName("M.Tech(CS/IT)");//M.Tech(CS/IT)
			bean.setDuration("2.5 years");
			bean.setDescription("Program that focuses on advanced knowledge, research, and specialization in CS/IT ");
			bean.setCreatedBy("Nidhi");
			bean.setModifiedBy("Nidhi");
			bean.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
			bean.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
			//testAdd(bean);
			bean.setId(8);
			//testUpdate(bean);
			
			//CourseBean bean1=new CourseBean();
			//CourseBean bean1=testFindByPk(1);
			//System.out.print(bean1.getId());
			//System.out.print("\t"+bean1.getName());
			//System.out.print("\t"+bean1.getDuration());
			//System.out.print("\t"+bean1.getDescription());
			//System.out.print("\t"+bean1.getCreatedBy());
			//System.out.print("\t"+bean1.getModifiedBy());
			//System.out.print("\t"+bean1.getCreatedDateTime());
			//System.out.println("\t"+bean1.getModifiedDateTime());
			
			//testDelete(6);
			
			//CourseBean bean1=new CourseBean();
			//bean1.setId(5);
			//List list=new ArrayList();
			//list=testsearch(bean1, 1, 5);
			//Iterator it=list.iterator();
			//while(it.hasNext()) {
				//bean1=(CourseBean)it.next();
				//System.out.print(bean1.getId());
				//System.out.print("\t"+bean1.getName());
				//System.out.print("\t"+bean1.getDuration());
				//System.out.print("\t"+bean1.getDescription());
				//System.out.print("\t"+bean1.getCreatedBy());
				//System.out.print("\t"+bean1.getModifiedBy());
				//System.out.print("\t"+bean1.getCreatedDateTime());
				//System.out.println("\t"+bean1.getModifiedDateTime());
			//}
			

		} catch (Exception e) {
			// TODO: handle exception
		}
		
			}

	public static Long testNextPk() {
		try {
			CourseModel model=new CourseModel();
			long nextPk=model.nextPk();
			return nextPk;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public static void testAdd(CourseBean bean) {
		try {
			CourseModel model=new CourseModel();
			long pk=model.add(bean);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static CourseBean testFindByPk(long id) {
		try {
			CourseModel model=new CourseModel();
			CourseBean bean=new CourseBean();
			bean=model.findByPk(id);
			return bean;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public static void testUpdate(CourseBean bean) {
		try {
			CourseModel model=new CourseModel();
			model.update(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void testDelete(long id) {
		try {
			CourseModel model=new CourseModel();
			model.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static List testsearch(CourseBean bean,int pageNo,int pageSize) {
		try {
			CourseModel model=new CourseModel();
			List list=new ArrayList();
			list=model.search(bean, pageNo, pageSize);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
