package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.model.FacultyModel;

public class FacultyTest {
	
	public static void main(String[] args) {
	try {
		//long nextPk=testNextPk();
		//System.out.println(nextPk);
				
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		FacultyBean bean=new FacultyBean();
		bean.setFirstName("Nitesh");
		bean.setLastName("Tiwari");
		bean.setDob(sdf.parse("2000-10-19"));
		bean.setGender("male");
		bean.setMobileNo("7894561231");
		bean.setEmail("nitesh@gmail.com");
		bean.setCollegeId(7);
		bean.setCourseId(7);
		bean.setSubjectId(2);
		bean.setCreatedBy("Nidhi");
		bean.setModifiedBy("Nidhi");
		bean.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
		//testAdd(bean);
		//bean.setId(3);
		//testUpdate(bean);
		
		//testDelete(3);
		testSearch();
				
	} catch (Exception e) {
		// TODO: handle exception
	}
		
	}
	
	public static Long testNextPk() {
		try {
			FacultyModel model=new FacultyModel();
			long nextPk=model.nextPk();
			return nextPk;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}		
	}
	
	public static void testAdd(FacultyBean bean) {
		try {
			FacultyModel model=new FacultyModel();
			model.add(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void testUpdate(FacultyBean bean) {
		try {
			FacultyModel model=new FacultyModel();
			model.update(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void testDelete(long id) {
		try {
			FacultyModel model=new FacultyModel();
			model.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void testSearch() {
		try {
			FacultyModel model=new FacultyModel();
			int pageNo=1;
			int pageSize=5;
			//FacultyBean bean=null;
			List list=model.search(null, pageNo, pageSize);
			Iterator<FacultyBean> it=list.iterator();
			while(it.hasNext()) {
				FacultyBean bean=(FacultyBean)it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
			}
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
				
	}
}
