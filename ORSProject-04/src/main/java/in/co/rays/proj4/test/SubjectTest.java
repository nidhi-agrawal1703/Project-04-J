package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.model.SubjectModel;

public class SubjectTest {
	public static void main(String[] args) {
		try {
			
			//long nextPk=testNextPk();
			//System.out.println(nextPk);
			SubjectBean bean =new SubjectBean();
			bean.setName("Advanced Data Structures & Algorithms");
			bean.setCourseId(8);
			bean.setDescription("It illustrates how to analyze the complexity of algorithms, apply suitable data structures for different problem domains.");
			bean.setCreatedBy("Nidhi");
			bean.setModifiedBy("Nidhi");
			bean.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
			bean.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
			//testAdd(bean);
			bean.setId(4);
			//testUpdate(bean);
			//testFindByName("Computer Networks");
			//testDelete(5);
			//bean=testFindByPk(3);
			//System.out.println("NAme: "+bean.getName());
			testSearch(bean, 1, 5);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static Long testNextPk() {
		try {
			SubjectModel model=new SubjectModel();
			long nextPk=model.nextPk();
			return nextPk;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public static void testAdd(SubjectBean bean) {
		try {
			SubjectModel model=new SubjectModel();
			model.add(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void testUpdate(SubjectBean bean) {
		try {
			SubjectModel model=new SubjectModel();
			model.update(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void testDelete(long id) {
		try {
			SubjectModel model=new SubjectModel();
			model.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static SubjectBean testFindByPk(long id) {
		try {
			SubjectModel model=new SubjectModel();
			SubjectBean bean=model.findByPk(id);
			return bean;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public static void testFindByName(String name) {
		try {
			SubjectModel model=new SubjectModel();
			SubjectBean bean=model.findByName(name);
			if(bean!=null) {
				System.out.println(bean.getName());
			}else {
				System.out.println("Data not found in find by name method");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void testSearch(SubjectBean bean,int pageNo,int pageSize) {
		try {
			SubjectModel model=new SubjectModel();
			List list= new ArrayList();
			list=model.search(bean, pageNo, pageSize);
			Iterator it=list.iterator();
			while(it.hasNext()) {
				SubjectBean bean1=(SubjectBean)it.next();
				System.out.println(bean1.getName());
				System.out.println(bean1.getDescription());
				System.out.println(bean1.getCourseName());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
