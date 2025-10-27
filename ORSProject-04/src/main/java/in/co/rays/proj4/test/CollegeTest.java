package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.model.CollegeModel;

public class CollegeTest {

	public static void main(String[] args) {
		try {
			//int nextPk=0;
			//nextPk=testFindPk();
			//System.out.println(nextPk);
			
			CollegeBean bean=new CollegeBean();
			
			bean.setName("Vaishnav Institute");
			bean.setAddress("Gram Baroli, Sanwer Road");
			bean.setState("Madhya Pradesh");
			bean.setCity("Indore");
			bean.setPhone_no("7753936274");
			bean.setCreatedBy("Nidhi");
			bean.setModifiedBy("Nidhi");
			bean.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
			bean.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
			//testAdd(bean);
			
			//testDelete(13);
			
			//bean.setId(13);
			//bean.setName("IIT Indore");
			//testUpdate(bean);
			
			//bean.setName("IIT");
			//bean.setAddress("Knowledge Village, Rajendra Nagar");
			//bean.setState("Madhya Pradesh");
			//bean.setCity("Indore");
			//bean.setPhone_no("9229498055");
			//bean.setCreatedBy("Nidhi");
			//bean.setModifiedBy("Krishna");
			//bean.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
			//bean.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
			//List list=new ArrayList();
			//list=testsearch(bean, 1, 5);
			//Iterator it=list.iterator();
			//CollegeBean bean1=new CollegeBean();
			//while(it.hasNext()) {
				//bean1=(CollegeBean)it.next();
				//System.out.print(bean1.getId());
				//System.out.print("\t"+bean1.getName());
				//System.out.println("\t"+"\t"+bean1.getAddress());

			//}
			
			//CollegeBean bean1=new CollegeBean();
			//bean1=testfindByPk(3);
			//System.out.println(bean1.getName());
			
			testfindByName("II");
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Long testFindPk() {
		try {
			long nextpk=0;
			CollegeModel model=new CollegeModel();
			nextpk=model.nextPk();
			return nextpk;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void testAdd(CollegeBean bean) {
		try {
			CollegeModel model=new CollegeModel();
			model.add(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testDelete(int id) {
		try {
			CollegeModel model=new CollegeModel();
			model.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void testUpdate(CollegeBean bean1) {
		try {
			CollegeModel model=new CollegeModel();
			model.update(bean1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static List testsearch(CollegeBean bean,int pageNo,int pageSize) {
		try {
			CollegeModel model=new CollegeModel();
			List list=new ArrayList();
			list=model.search(bean, pageNo, pageSize);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static CollegeBean testfindByPk(long id) {
		try {
			CollegeModel model=new CollegeModel();
			CollegeBean bean=new CollegeBean();
			bean=model.findByPk(id);
			System.out.println(bean.getName());
			return bean;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public static void testfindByName(String name) {
		try {
			CollegeModel model=new CollegeModel();
			CollegeBean bean=new CollegeBean();
			bean=model.findByName(name);
			if(bean!=null) {
				System.out.println(bean.getAddress());
			}else {
				System.out.println("DAta not found");
			}
			
			//System.out.println(bean.getName());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
	}
	
}
