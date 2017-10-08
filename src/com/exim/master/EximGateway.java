package com.exim.master;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.parser.JSONParser;

import com.exim.util.RuntimeConf;

/**
 * Servlet implementation class BiplLogin
 */
@WebServlet("/EximGateway")
public class EximGateway extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static JSONObject obj=new JSONObject();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EximGateway() {
		super();
	}
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestDispatcher rd;
			System.out.println("Exim Gateway");
			String module=request.getParameter("module")==null?"":request.getParameter("module");
			String option=request.getParameter("option")==null?"":request.getParameter("option");
			if(module.equals("Login")) {
				rd=request.getRequestDispatcher("/LoginMst");
				rd.forward(request,response);
			} else if(checkSession(request,response)) {
				if(module.equals("Master")){
					rd=request.getRequestDispatcher("/BuyerSupplierMaster");
					rd.forward(request,response);
				}
			} else {
				PrintWriter out = response.getWriter();
				obj.put("Session", "Session Timed Out..!");
				out.println(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//System.out.println("Finally");
		}
	}
	
	public boolean checkSession(HttpServletRequest request,HttpServletResponse response){
		boolean flag=false;
		System.out.println("Session: "+request.getSession().getAttribute("LoginId"));
		if(request.getSession(false).getAttribute("LoginId")!=null) {
			flag=true;
		}
		
		return flag;
	}
	
}
