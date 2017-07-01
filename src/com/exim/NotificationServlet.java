package com.exim;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class NotificationServlet
 */
@WebServlet("/NotificationServlet")
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn = null;
	Statement stmtnotif = null;
	ResultSet rstnotif = null;
	JSONObject obj = new JSONObject();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotificationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("doPost");
		try {
			Context initContext = new InitialContext();
			if (initContext == null)
				throw new Exception("Boom - No Context");
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/wipo");
			if (ds != null) {
				conn = ds.getConnection();
				if (conn != null) {
					stmtnotif = conn.createStatement();
					conn.setAutoCommit(false);
					String option = request.getParameter("option") == null ? "" : request.getParameter("option");
					response.setCharacterEncoding("UTF-8");
					if(option.equalsIgnoreCase("getNotif")) {
						getNotif(request, response);
					} else {
						obj.put("Error", "No/False_Parameter_Selected");
					}
				}
			}
		} catch (Exception e) {
			obj.put("Error", "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			PrintWriter out = response.getWriter();
			out.println(obj);
			obj.clear();
			//System.out.println("--IN Finally--");
			if (rstnotif != null) {
				try {
					rstnotif.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				rstnotif = null;
			}
			if (stmtnotif != null) {
				try {
					stmtnotif.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				stmtnotif = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				conn = null;
			}
			if (!obj.isEmpty()) {
				obj.clear();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getNotif(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		try {
			if (!param.equals("")) {
				JSONObject innerObj = new JSONObject();
				query = "select count(*) from bi_complain_mst where status= 'NEW'";
				//System.out.println(query);
				rstnotif = stmtnotif.executeQuery(query);
				if (rstnotif.next()) {
					innerObj.put("NewReq",rstnotif.getString(1));
				}
				obj.put("Data", innerObj);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}

}
