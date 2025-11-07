package in.co.rays.proj4.test;

import in.co.rays.proj4.bean.ExpertiseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ExpertiseModel;

public class ExpertiseTest {
	public static void main(String[] args) throws Exception {
		
		//testNextPk();
		//testAdd();
		//testUpdate();
		//testDelete();
		//testFindByName();
	}
	
	public static void testNextPk() throws DatabaseException {
		ExpertiseModel model=new ExpertiseModel();
		long nextPk=model.nextPk();
		System.out.println(nextPk);
	}
	public static void testAdd() throws ApplicationException, DuplicateRecordException {
		ExpertiseModel model=new ExpertiseModel();
		ExpertiseBean bean=new ExpertiseBean();
		bean.setName("Neurologist");
		bean.setDescription("Heart and blood vessel diseases");
		model.add(bean);
	}
	public static void testUpdate() throws ApplicationException{
		ExpertiseModel model=new ExpertiseModel();
		ExpertiseBean bean=new ExpertiseBean();
		bean.setId(2);
		bean.setName("Neurologist");
		bean.setDescription("Specialist of brain, spinal cord, nerves");
		try {
			model.update(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testDelete()throws ApplicationException{
		ExpertiseModel model=new ExpertiseModel();
		//ExpertiseBean bean=new ExpertiseBean();
		model.delete(2);
	}
	public static void testFindByName()throws ApplicationException{
		ExpertiseModel model=new ExpertiseModel();
		ExpertiseBean bean=model.findByName("Neurologist");
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
	}
	
}
