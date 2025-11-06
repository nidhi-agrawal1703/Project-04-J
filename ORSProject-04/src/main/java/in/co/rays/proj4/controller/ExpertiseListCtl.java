package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ExpertiseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.ExpertiseModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
@WebServlet(name="ExpertiseListCtl",urlPatterns= {"/ctl/ExpertiseListCtl"})
public class ExpertiseListCtl extends BaseCtl {
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		// TODO Auto-generated method stub
		ExpertiseBean bean=new ExpertiseBean();
		bean.setName(DataUtility.getString(request.getParameter("name")));
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int pageNo=1;
		int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		ExpertiseBean bean=(ExpertiseBean)populateBean(request);
		ExpertiseModel model=new ExpertiseModel();
		
		try {
			List list=model.search(bean, pageNo, pageSize);
			List next=model.search(bean, pageNo+1, pageSize);
			
			if(list==null && list.isEmpty()) {
				ServletUtility.setErrorMessage("No REcord Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());
			
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}	
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List list=null;
		List next=null;
		
		int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize=DataUtility.getInt(request.getParameter("pageSize"));
		
		pageNo=(pageNo==0)?1:pageNo;
		pageSize=(pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
		
		ExpertiseBean bean=(ExpertiseBean)populateBean(request);
		ExpertiseModel model=new ExpertiseModel();
		
		String op=DataUtility.getString(request.getParameter("operation"));
		String ids[]=request.getParameterValues("ids");
		
		try {
			if(OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				if(OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo=1;
				}else if(OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				}else if(OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				}
			}else if(OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.EXPERTISE_CTL, request, response);
				return;
			}else if(OP_DELETE.equalsIgnoreCase(op)) {
				pageNo=1;
				if(ids!=null &&ids.length>0) {
					ExpertiseBean deleteBean=new ExpertiseBean();
					for(String id:ids) {
						deleteBean.setId(DataUtility.getInt(id));
						model.delete(deleteBean);
						ServletUtility.setSuccessMessage("Data is deleted successfully", request);
					}
					
				}else {
					ServletUtility.setErrorMessage("Select Atleast One Record", request);
				}
			}else if(OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.EXPERTISE_LIST_CTL, request, response);
				return;
			}else if(OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.EXPERTISE_LIST_CTL, request, response);
				return;
			}
			list=model.search(bean, pageNo, pageSize);
			next=model.search(bean, pageNo+1, pageSize);
			
			if(list==null || list.size()==0) {
				ServletUtility.setErrorMessage("No Record Found", request);
				
			}
			ServletUtility.setList(list, request);
			System.out.println("List "+list);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			// TODO: handle exception
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.EXPERTISE_LIST_VIEW;
	}

}
