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
@WebServlet("/LoginMst")
public class LoginMst extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn = null;
	Statement stmt = null;
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	ResultSet rst = null;
	ResultSet rst1 = null;
	ResultSet rst2 = null;
	ResultSet rst3 = null;
	private JSONObject obj;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginMst() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			obj = new JSONObject();
			Context initContext = new InitialContext();
			if (initContext == null)
				throw new Exception("Boom - No Context");
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup(RuntimeConf.database_exim);
			if (ds != null) {
				conn = ds.getConnection();
				if (conn != null) {
					stmt = conn.createStatement();
					stmt1 = conn.createStatement();
					stmt2 = conn.createStatement();
					stmt3 = conn.createStatement();
					conn.setAutoCommit(false);
					String option = request.getParameter("option") == null ? "" : request.getParameter("option");
					response.setCharacterEncoding("UTF-8");

					if (option.equalsIgnoreCase("checkLogin")) {
						checkLoginWeb(request, response);
					} else if (option.equalsIgnoreCase("getMenu")) {
						getMenu(request, response);
					} else if(option.equalsIgnoreCase("logOut")){
						logOut(request,response);
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
			//obj.clear();
			if (rst != null) {
				try {
					rst.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				rst = null;
			}
			if (rst1 != null) {
				try {
					rst1.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				rst1 = null;
			}
			if (rst2 != null) {
				try {
					rst2.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				rst2 = null;
			}
			if (rst3 != null) {
				try {
					rst3.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				rst3 = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				stmt = null;
			}
			if (stmt1 != null) {
				try {
					stmt1.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				stmt1 = null;
			}
			if (stmt2 != null) {
				try {
					stmt2.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				stmt2 = null;
			}
			if (stmt3 != null) {
				try {
					stmt3.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				stmt3 = null;
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
	
	private void logOut(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		System.out.println("Logout Process");
		obj.put("Data", "Y");
	}

	@SuppressWarnings("unchecked")
	private void checkLoginWeb(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", password = "", query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println(param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId")==null?"":Json.get("loginId").toString();
				password = Json.get("password")==null?"":Json.get("password").toString();

				String login_succ = "N", dateTime = "";
				String empNm = "", usercd = "";
				if (!loginId.equals("") && !password.equals("")) {
					StringBuffer sb = EncryptTest.encrypt(password);
					System.out.println(password+" password "+sb.toString()+" Dec ");
					query = "select count(*),date_format(sysdate(),'%d/%m/%Y %H:%i') from Login_mst " +
							"where emp_cd='"+loginId+"' and passwd = '"+sb.toString()+"'";
					System.out.println(query);
					rst = stmt.executeQuery(query);
					if (rst.next()) {
						dateTime = rst.getString(2);
						if (rst.getInt(1) == 0) {
							login_succ = "N";
						} else {
							login_succ = "Y";
							query = "select EMP_NM,EMP_CD from HR_EMP_MST where EMP_CD='"
									+ loginId + "'";
							rst1 = stmt1.executeQuery(query);
							if (rst1.next()) {
								empNm = rst1.getString(1) == null ? "" : rst1.getString(1);
								usercd = rst1.getString(2) == null ? "" : rst1.getString(2);
								HttpSession session = request.getSession();
								session.setAttribute("LoginId", rst1.getString(2));
								session.setMaxInactiveInterval(20 * 60);
							}
							obj.put("usercd", usercd);
							obj.put("empName", empNm);
							obj.put("dateTime", dateTime);
						}
					}
				}
				obj.put("loginSuccess", login_succ);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	@SuppressWarnings("unchecked")
	private void getMenu(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println(param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId")==null?"":Json.get("loginId").toString();
				ArrayList<JSONObject> module = new ArrayList<JSONObject>();
				ArrayList<JSONObject> app;
				JSONObject innerObj1;
				if (!loginId.equals("")) {
					query="SELECT distinct cast(a.module_id as decimal),c.MODULE_NM FROM EMPL_PERM a,APPL_MST b,module_mst c " +
							"WHERE a.module_id=c.module_id and a.emp_code='"+loginId+"'  AND a.view_perm='Y' " +
							"AND a.module_id=b.module_id AND a.appl_id=b.appl_id ORDER BY 1";
					rst=stmt.executeQuery(query);
					while(rst.next()) {
						innerObj1 = new JSONObject();
						app = new ArrayList<JSONObject>();
						query=" SELECT a.appl_id,b.appl_nm,b.classpath FROM EMPL_PERM a,APPL_MST b "   +
								" WHERE a.emp_code='"+loginId+"' AND a.module_id='"+rst.getString(1)+"' AND a.view_perm='Y'"   +
								" AND a.module_id=b.module_id AND a.appl_id=b.appl_id ORDER BY b.appl_id";
						rst1=stmt1.executeQuery(query);
						while(rst1.next()) {
							JSONObject innerObj = new JSONObject();
							innerObj.put("AppID",rst1.getString(1));
							innerObj.put("AppNm",rst1.getString(2));
							innerObj.put("AppState",rst1.getString(3));
							app.add(innerObj);
						}
						innerObj1.put("ModuleID",rst.getString(1));
						innerObj1.put("ModuleNm",rst.getString(2));
						innerObj1.put("App",app);
						module.add(innerObj1);
					}
				}
				obj.put("Data", module);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
}
