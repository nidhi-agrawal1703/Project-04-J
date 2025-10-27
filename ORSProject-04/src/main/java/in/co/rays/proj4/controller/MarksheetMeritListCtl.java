package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;


/**
 *
 *Marksheet merit list functionality controller.
 *Performance operation of Marksheet merit list
 *
 * @author Nidhi
 * @version 1.0
 */
@WebServlet(name="MarksheetMeritListCtl",urlPatterns= {"/ctl/MarksheetMeritListCtl"})
public class MarksheetMeritListCtl extends BaseCtl {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNo=1;
		int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		MarksheetModel model=new MarksheetModel();
		
		try {
			List<MarksheetBean> list=model.getMeritList(pageNo, pageSize);
			
			if(list==null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			
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
		String op=DataUtility.getString(request.getParameter("operation"));
		if(OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.WELCOME_CTL,request,response);
			return;
		}
	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}

}
