package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.model.TimetableModel;

public class TimetableTest {
	
	public static void main(String[] args) {
		testAdd();
	}
	
	public static void testAdd() {
		try {
			TimetableModel model=new TimetableModel();
			TimetableBean bean=new TimetableBean();
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String d="1995-05-21";
			bean.setSemester("5");
			bean.setDescription("DBMS fifth semester");
			bean.setExamDate(sdf.parse(d));
			bean.setExamTime("8:00");
			bean.setCourseId(1);
			bean.setSubjectId(1);
			bean.setCreatedBy("Nidhi");
			bean.setModifiedBy("Nidhi");
			bean.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
			bean.setModifiedDateTime(new Timestamp(System.currentTimeMillis()));
			
			long i=model.add(bean);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
