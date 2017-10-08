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
@WebServlet("/BuyerSupplierMaster")
public class BuyerSupplierMaster extends HttpServlet {
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
	public BuyerSupplierMaster() {
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

					if (option.equalsIgnoreCase("getBuyers")) {
						getBuyers(request, response);
					} else if (option.equalsIgnoreCase("getTradersOfBuyers")) {
						getTradersOfBuyers(request, response);
					} else if (option.equalsIgnoreCase("getSuppliers")) {
						getSuppliers(request, response);
					} else if (option.equalsIgnoreCase("getTradersOfSuppliers")) {
						getTradersOfSuppliers(request, response);
					} else if(option.equalsIgnoreCase("getDirectSupplier")) {
						getDirectSupplier(request, response);
					} else if(option.equalsIgnoreCase("getDirectBuyer")) {
						getDirectBuyer(request, response);
					} else if(option.equalsIgnoreCase("getBuyerDetail")) {
						getBuyerDetail(request, response);
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
	
	@SuppressWarnings("unchecked")
	private void getDirectSupplier(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		String term = request.getParameter("term[term]") == null ? "" : request.getParameter("term[term]");
		System.out.println(param);
		try {
			if (!param.equals("")) {
				ArrayList<JSONObject> suppliers=new ArrayList<JSONObject>();
					System.out.println("term: "+term);
					query="select supl_cd,supl_nm,del_flag from ex_supl_mst where supl_nm like '"+term+"%' order by supl_nm";
					System.out.println(query);
					rst=stmt.executeQuery(query);
					while(rst.next()) {
						JSONObject jsn=new JSONObject();
						jsn.put("id",rst.getString(1));
						jsn.put("text",rst.getString(2)==null?"":rst.getString(2).trim());
//						jsn.put("supDel",rst.getString(3)==null?"N":rst.getString(3));
						suppliers.add(jsn);
					}
				obj.put("results", suppliers);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void getBuyerDetail(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println(param);
		try {
			if (!param.equals("")) {
				/*query="";
				rst=stmt.executeQuery(query);
				while(rst.next()) {
					
				}*/
				
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void getDirectBuyer(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		String term = request.getParameter("term[term]") == null ? "" : request.getParameter("term[term]");
		System.out.println(param);
		try {
			if (!param.equals("")) {
				ArrayList<JSONObject> suppliers=new ArrayList<JSONObject>();
					System.out.println("term: "+term);
					query="select buyer_cd,buyer_nm,del_flag from ex_buyer_mst where buyer_nm like '"+term+"%' order by buyer_nm";
					System.out.println(query);
					rst=stmt.executeQuery(query);
					while(rst.next()) {
						JSONObject jsn=new JSONObject();
						jsn.put("id",rst.getString(1));
						jsn.put("text",rst.getString(2)==null?"":rst.getString(2).trim());
//						jsn.put("supDel",rst.getString(3)==null?"N":rst.getString(3));
						suppliers.add(jsn);
					}
				obj.put("results", suppliers);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getSuppliers(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", query = "",delFlag="";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println(param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId")==null?"":Json.get("loginId").toString();
				delFlag= Json.get("delFlag")==null?"":Json.get("delFlag").toString();
				ArrayList<JSONObject> suppliers=new ArrayList<JSONObject>();
				if (!loginId.equals("")) {
					String str="";
					if(delFlag.equals("Y")) {
						str=" del_flag='Y' ";
					} else {
						str=" (del_flag='N' or del_flag is null) ";
					}
					
					query="select supl_cd,supl_nm,del_flag from ex_supl_mst where "+str+" order by supl_nm";
					System.out.println(query);
					rst=stmt.executeQuery(query);
					while(rst.next()) {
						JSONObject jsn=new JSONObject();
						jsn.put("supCd",rst.getString(1));
						jsn.put("supNm",rst.getString(2)==null?"":rst.getString(2).trim());
						jsn.put("supDel",rst.getString(3)==null?"N":rst.getString(3));
						suppliers.add(jsn);
					}
				}
				obj.put("Data", suppliers);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getBuyers(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", query = "",delFlag="";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println(param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				delFlag = Json.get("delFlag")==null?"":Json.get("delFlag").toString();
				loginId = Json.get("loginId")==null?"":Json.get("loginId").toString();
				ArrayList<JSONObject> buyers=new ArrayList<JSONObject>();
				if (!loginId.equals("")) {
					String str="";
					if(delFlag.equals("Y")) {
						str=" del_flag='Y' ";
					} else {
						str=" (del_flag='N' or del_flag is null) ";
					}
					
					query="select Buyer_cd,Buyer_nm,del_flag from ex_buyer_mst where "+str+" order by buyer_nm";
					System.out.println(query);
					rst=stmt.executeQuery(query);
					while(rst.next()) {
						JSONObject jsn=new JSONObject();
						jsn.put("buyCd",rst.getString(1));
						jsn.put("buyNm",rst.getString(2)==null?"":rst.getString(2).trim());
						jsn.put("buyDel",rst.getString(3)==null?"N":rst.getString(3));
						buyers.add(jsn);
					}
				}
				obj.put("Data", buyers);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getTradersOfSuppliers(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println(param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId")==null?"":Json.get("loginId").toString();
				ArrayList<JSONObject> traders=new ArrayList<JSONObject>();
				if (!loginId.equals("")) {
					query="select emp_cd,emp_nm from hr_emp_mst where supl_flag='Y' and (del_flag is null or del_flag='N') order by emp_nm";
					System.out.println(query);
					rst=stmt.executeQuery(query);
					while(rst.next()) {
						JSONObject jsn=new JSONObject();
						jsn.put("id",rst.getString(1));
						jsn.put("text",rst.getString(2)==null?"":rst.getString(2).trim());
						traders.add(jsn);
					}
				}
				obj.put("Data", traders);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getTradersOfBuyers(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println(param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId")==null?"":Json.get("loginId").toString();
				ArrayList<JSONObject> traders=new ArrayList<JSONObject>();
				if (!loginId.equals("")) {
					query="select emp_cd,emp_nm from hr_emp_mst where buy_flag='Y' and (del_flag is null or del_flag='N') order by emp_nm";
					System.out.println(query);
					rst=stmt.executeQuery(query);
					while(rst.next()) {
						JSONObject jsn=new JSONObject();
						jsn.put("id",rst.getString(1));
						jsn.put("text",rst.getString(2)==null?"":rst.getString(2).trim());
						traders.add(jsn);
					}
				}
				obj.put("Data", traders);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
}
