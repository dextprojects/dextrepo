package com.exim;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;

import org.json.simple.JSONObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.parser.JSONParser;

/**
 * Servlet implementation class CheckIn
 */
@WebServlet("/CheckIn")
public class CheckIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn = null;
	Statement stmtnotif = null;
	Statement stmt = null;
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	ResultSet rstnotif = null;
	ResultSet rst = null;
	ResultSet rst1 = null;
	ResultSet rst2 = null;
	ResultSet rst3 = null;
	JSONObject obj = new JSONObject();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckIn() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
					stmt = conn.createStatement();
					stmt1 = conn.createStatement();
					stmt2 = conn.createStatement();
					stmt3 = conn.createStatement();
					conn.setAutoCommit(false);
					String option = request.getParameter("option") == null ? "" : request.getParameter("option");
					response.setCharacterEncoding("UTF-8");

					if (option.equalsIgnoreCase("CheckLogin")) {
						checkLogin(request, response); 
					} else if (option.equalsIgnoreCase("CheckLoginWeb")) {
						checkLoginWeb(request, response);
					} else if (option.equalsIgnoreCase("CheckLoginCell")) {
						checkLoginCell(request, response); 
					} else if (option.equalsIgnoreCase("EnterCompl")) { 
						entercompl(request, response);
					} else if (option.equalsIgnoreCase("EnterNotice")) { 
						EnterNotice(request, response);
					} else if (option.equalsIgnoreCase("ViewSingleEmplCompl")) { 
						if(checkCurrentSession(request, response))
							ViewSingleEmplCompl(request, response);
					} else if (option.equalsIgnoreCase("ViewSingleEmplAllCompl")) { 
						if(checkCurrentSession(request, response))
							ViewSingleEmplAllCompl(request, response);
					} else if (option.equalsIgnoreCase("UserRegistration")) {
						UserRegistration(request, response); 
					} else if (option.equalsIgnoreCase("CheckChangedCompl")) {
						CheckChangedCompl(request, response); 
					} else if (option.equalsIgnoreCase("SubmitChangeEntry")) {
						if(checkCurrentSession(request, response))
							SubmitChangeEntry(request, response); 
					} else if (option.equalsIgnoreCase("getCiRequests")) {
						if(checkCurrentSession(request, response))
							getCiRequests(request, response);
					} else if (option.equalsIgnoreCase("checkSession")) {
						checkSession(request, response);
					} else if (option.equalsIgnoreCase("getAllLiveReq")) {
						getAllLiveReq(request, response);
					} else if (option.equalsIgnoreCase("getAllClosedReq")) {
						getAllClosedReq(request, response);
					} else if (option.equalsIgnoreCase("getSingleReq")) {
						getSingleReq(request, response);
					} else if(option.equalsIgnoreCase("getNotif")) {
						getNotif(request, response);
					} else if (option.equalsIgnoreCase("getAllUsers")) {
						if(checkCurrentSession(request, response))
							getAllUsers(request, response);
					} else if (option.equalsIgnoreCase("getNotices")) {
						if(checkCurrentSession(request, response))
							getNotices(request, response);
					} else if (option.equalsIgnoreCase("getMonReqGraph")) {
						if(checkCurrentSession(request, response))
							getMonReqGraph(request, response);
					} else if (option.equalsIgnoreCase("getUserProfile")) {
						getUserProfile(request, response);
					} else if (option.equalsIgnoreCase("insertScanBarcode")) {
						insertScanBarcode(request, response);
					} else if (option.equalsIgnoreCase("getScannedBarcode")) {
						getScannedBarcode(request, response);
					} else if (option.equalsIgnoreCase("getDirectHouse")) {
						getDirectHouse(request, response);
					} else if (option.equalsIgnoreCase("getUserNoticeCell")) {
						getUserNoticeCell(request, response);
					} else if (option.equalsIgnoreCase("getNoticesCell")) {
						getNoticesCell(request, response);
					} else if (option.equalsIgnoreCase("getAllHouses")) {
						if(checkCurrentSession(request, response))
							getAllHouses(request, response);
					} else if (option.equalsIgnoreCase("resetHouse")) {
						if(checkCurrentSession(request, response))
							resetHouse(request, response);
					} else if(option.equalsIgnoreCase("LinkHouse")) {
						linkHouse(request, response);
					} else if(option.equalsIgnoreCase("getAllFlags")) {
						obj.put("Login","CheckLogin || Param = loginId, password");
						obj.put("Complaint Entry","EnterCompl || Param = loginId, remarks, complSubject, complDetail");
						obj.put("Registration","UserRegistration || Param = userFirstName, userLastName, userMobile, userPassword");
						obj.put("Notification","CheckChangedCompl || Param = loginId");
						obj.put("Modify Req","SubmitChangeEntry || compl_id, ind, status, user_cd, subject, remarks");
						obj.put("All Requests","getCiRequests");
						obj.put("Session check","checkSession || Param = loginId");
						obj.put("All Live Req","getAllLiveReq || Param = loginId");
						obj.put("All Closed Req","getAllClosedReq || Param = loginId");
						obj.put("Single Req","getSingleReq");
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
			if (stmtnotif != null) {
				try {
					stmtnotif.close();
				} catch (SQLException e) {
					System.out.println("Exception in Login " + e);
				}
				stmtnotif = null;
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
	
	@SuppressWarnings({"unchecked"})
	public void checkSession(HttpServletRequest request,HttpServletResponse response) {
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		try {
			if(request.getSession(false)!=null) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				String loginId = Json.get("loginId").toString();
				
				if(request.getSession(false).getAttribute("usercd").equals(loginId)) {
					System.out.println("Session True");
					obj.put("session",true);
				}
			} else {
				obj.put("session",false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("session",false);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getDirectHouse(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		String term = request.getParameter("term[term]") == null ? "" : request.getParameter("term[term]");
		try {
			ArrayList<JSONObject> houses=new ArrayList<JSONObject>();
			System.out.println("term: "+term);
			query="select a.hrs_cd,a.hrs_nm,b.emp_nm from ris_horse_mst a, hr_emp_mst b, ris_trn_horse_dtl c where a.hrs_nm like '"+term.toUpperCase()+"%' and a.hrs_cd=c.hrs_cd and c.flag='Y' and b.emp_cd=c.trn_cd order by hrs_alias_cd";
			System.out.println(query);
			rst=stmt.executeQuery(query);
			while(rst.next()) {
				JSONObject jsn=new JSONObject();
				jsn.put("id",rst.getString(1));
				jsn.put("text",(rst.getString(2)==null?"":rst.getString(2).trim())+" : "+(rst.getString(3)==null?"":rst.getString(3).trim())+"");
				houses.add(jsn);
			}
			obj.put("results", houses);
			System.out.println(obj);
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings({"unchecked"})
	public boolean checkCurrentSession(HttpServletRequest request,HttpServletResponse response) {
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println("Session Param: "+param);
		try {
			if(request.getSession(false)!=null) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				String loginId = Json.get("loginId").toString();
				
				if(request.getSession(false).getAttribute("usercd").equals(loginId)) {
					System.out.println("Session: True");
					return true;
				} else {
					System.out.println("Session: False");
					obj.put("Session",false);
					return false;
				}
			} else {
				obj.put("Session",false);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Session",false);
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	private void resetHouse(HttpServletRequest request, HttpServletResponse response) {
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		String query="";
		try {
			JSONObject Json = (JSONObject) new JSONParser().parse(param);
			
			System.out.println("::resetHouse:: "+Json.get("houseCd"));
			query="update ris_trn_horse_dtl set Flag='N' where hrs_cd='"+Json.get("houseCd")+"'";
			stmt.executeUpdate(query);
			conn.commit();
			//obj.put("Data","Y");
			getAllHouses(request,response);
		} catch(Exception e) {
			obj.put("Error", "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("unchecked")
	private void SubmitChangeEntry(HttpServletRequest request, HttpServletResponse response) {
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		try {
			JSONObject Json = (JSONObject) new JSONParser().parse(param);
			String user_cd = Json.get("loginId").toString();
			String compl_id = Json.get("compl_id").toString();
			String status = Json.get("status").toString();
			String subject = Json.get("subject").toString();
			String remarks = Json.get("remarks").toString();
				
			String query = "",user_cd_mst="";
			if (!status.equals("DONE")) {
				query = "update bi_complain_mst set status='" + status
						+ "' where compl_id ='" + compl_id + "'";
			} else {
				query = "update bi_complain_mst set status='" + status
						+ "', FLAG='X' where compl_id ='" + compl_id + "'";
			}
			System.out.println("Update: " + query);
			stmt.executeUpdate(query);
			
			int SeqNo = 0;
			query = "select count(COMPL_ID) from BI_COMPLAIN_DTL where compl_id ='" + compl_id + "' ";
			System.out.println("select: " + query);
			rst = stmt1.executeQuery(query);
			if (rst.next()) {
				SeqNo = rst.getInt(1) + 1;
			}
			
			
			query = "select USER_CD from BI_COMPLAIN_MST where compl_id ='" + compl_id + "' ";
			System.out.println("select: " + query);
			rst = stmt1.executeQuery(query);
			if (rst.next()) {
				user_cd_mst = rst.getString(1)==null?"":rst.getString(1);
			}
			
			query = "insert into BI_COMPLAIN_DTL (COMPL_ID, SEQ_NO, UPDATE_BY, REMARKS, STATUS, UPDATE_DT, FLAG) "
				+ "values('"+ compl_id+ "','"+ SeqNo+ "','"+ user_cd+ "','"+ remarks+ "','"+ status+ "', sysdate, 'Y')";
			System.out.println("Insert-TRANS: " + query);
			stmt2.executeUpdate(query);
			// //////////////////////
			query = "select count(COMPL_ID) from BI_COMPL_CHANGE_DTL where compl_id ='"
				+ compl_id + "' and del_flag='N'";
			System.out.println("select: " + query);
			rst = stmt1.executeQuery(query);
			if (rst.next()) {
				if (rst.getInt(1) == 0) {
					query = "insert into BI_COMPL_CHANGE_DTL (COMPL_ID, USER_CD, SUBJECT, STATUS, CHANGE_DT, DEL_FLAG) " +
					"values('"+ compl_id+ "','"+ user_cd_mst+ "','"+ subject.replace("'", "''")+ "','"
					+ status + "',sysdate,'N')";
					System.out.println("Insert: " + query);
					stmt2.executeUpdate(query);
				} else {
					SeqNo = rst.getInt(1);
					query = "update BI_COMPL_CHANGE_DTL set DEL_FLAG='N',status='"
						+ status + "' where compl_id ='" + compl_id + "'";
					System.out.println("Update:BI_COMPL_CHANGE_DTL " + query);
					stmt2.executeUpdate(query);
				}
			}
			conn.commit();
			obj.put("compl_id", compl_id);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			obj.put("Error", "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void getCiRequests(HttpServletRequest request, HttpServletResponse response) {
			try {
				JSONObject innerObj = new JSONObject();
				JSONObject innerinnerObj = new JSONObject();
				ArrayList arr = new ArrayList();
				ArrayList dataArr = new ArrayList();
				int i=0;
				String query = "select a.compl_id, to_char(a.CREATED_DATE,'dd/mm/yyyy hh24:mi'), a.remarks, a.status, a.compl_subject, a.compl_detail," +
							" b.hrs_nm,a.flag from BI_COMPLAIN_MST a, ris_horse_mst b where a.user_cd= b.hrs_cd order by" +
							" a.compl_id Desc";
				rst = stmt.executeQuery(query);
				while (rst.next()) {
					dataArr = new ArrayList();
					i=0;
					innerObj = new JSONObject();
					innerObj.put("Vcompl_id", rst.getString(1));
					innerObj.put("Vcreated_d", rst.getString(2));
					innerObj.put("Vremarks", rst.getString(3));
					innerObj.put("Vstatus", rst.getString(4));
					innerObj.put("Vcompl_subject", rst.getString(5));
					innerObj.put("Vcompl_detail", rst.getString(6));
					innerObj.put("Vemp_nm", rst.getString(7));
					innerObj.put("Vflag", rst.getString(8));
					String qry1="select status,b.emp_nm, a.remarks, to_char(a.update_dt,'dd/mm/yyyy hh24:mi') from BI_COMPLAIN_DTL a, HR_EMP_MST b where a.update_BY= b.EMP_CD and compl_id='"+rst.getString(1)+"' ORDER by update_dt desc";
					rst2 = stmt1.executeQuery(qry1);
					while (rst2.next()) {
						i++;
						innerinnerObj = new JSONObject(); 
						innerinnerObj.put("id",i);
						innerinnerObj.put("status",rst2.getString(1));
						innerinnerObj.put("updatedBy",rst2.getString(2));
						innerinnerObj.put("remarks",rst2.getString(3)==null?"No Remarks":rst2.getString(3));
						innerinnerObj.put("update_dt",rst2.getString(4));
						dataArr.add(innerinnerObj);
					}
					innerObj.put("Data",dataArr);
					innerObj.put("DataLen", dataArr.size());
					arr.add(innerObj);
				}
				obj.put("Requests", arr);
				System.out.println(arr);
			} catch (Exception e) {
				obj.put("Error", "Error: " + e.getLocalizedMessage());
				e.printStackTrace();
			}
	}

	@SuppressWarnings("unchecked")
	private void CheckChangedCompl(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		try {
			JSONObject Json = (JSONObject) new JSONParser().parse(param);
			String userId = Json.get("loginId").toString();
			if (!userId.equals("")) {
				query = "select count(*) from BI_COMPL_CHANGE_DTL where user_cd='" + userId + "' and del_flag='N'";
				rst = stmt.executeQuery(query);
				if (rst.next()) {
					if (rst.getInt(1) != 0) {
						ArrayList complid = new ArrayList();
						ArrayList subject = new ArrayList();
						ArrayList Status = new ArrayList();
						query = "select compl_id,subject,Status from BI_COMPL_CHANGE_DTL where user_cd='" + userId + "' and del_flag='N'";
						rst1 = stmt1.executeQuery(query);
						while (rst1.next()) {
							complid.add(rst1.getString(1) == null ? "" : rst1.getString(1));
							subject.add(rst1.getString(2) == null ? "" : rst1.getString(2));
							Status.add(rst1.getString(3) == null ? "" : rst1.getString(3));
						}
						query = "update BI_COMPL_CHANGE_DTL set del_flag='Y' where user_cd='" + userId + "' and del_flag='N'";
						stmt2.executeUpdate(query);
						
						JSONObject jobj = new JSONObject();
						jobj.put("complId", complid);
						jobj.put("subject", subject);
						jobj.put("status", Status);
						obj.put("Data", jobj);
					} else {
						obj.put("Data", "NO-DATA");
					}
				} else {
					obj.put("Data", "NO-DATA");
				}
				ArrayList noticeSubject = new ArrayList();
				ArrayList noticeDt = new ArrayList();
				query = "select subject, created_dt from bi_msg_notification where user_cd='" + userId + "' and del_flag='N'";
				rst = stmt.executeQuery(query);
				while(rst.next()) {
					noticeSubject.add(rst.getString(1) == null ? "" : rst.getString(1));
					noticeDt.add(rst.getString(2) == null ? "" : rst.getString(2));
				}
				query = "update bi_msg_notification set del_flag='Y' where user_cd='" + userId + "' and del_flag='N'";
				stmt2.executeUpdate(query);
				
				if(noticeSubject.size()>0) {
					JSONObject jobj = new JSONObject();
					jobj.put("noticeSubject", noticeSubject);
					jobj.put("noticeDt", noticeDt);
					obj.put("MSG", jobj);
				} else {
					obj.put("MSG", "NO-DATA");
				}
				
				conn.commit();
			}
		} catch (Exception e) {
			obj.put("Error", "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void UserRegistration(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				String userHouseAlias = Json.get("userHouseAlias").toString().trim() == null ? "" : Json.get("userHouseAlias").toString().trim();
				String userFirstName = Json.get("userFirstName").toString().trim() == null ? "" : Json.get("userFirstName").toString().trim();
				String userLastName = Json.get("userLastName").toString().trim() == null ? "" : Json.get("userLastName").toString().trim();
				String userMobile = Json.get("userMobile").toString().trim() == null ? "" : Json.get("userMobile").toString().trim();
				String userPassword = Json.get("userPassword").toString().trim() == null ? "" : Json.get("userPassword").toString().trim();
				String FullName = "",houseCD="" ;
				if (!userHouseAlias.equals("")) {
					boolean houseFlag=false,mobileFlag=false;
					
					query = "select count(*) from HR_EMP_MST where MOB_NO='" + userMobile + "'";
					rst = stmt.executeQuery(query);
					if (rst.next()) {
						if (rst.getInt(1) == 0) {
							mobileFlag=true;
						}
					}
					if(mobileFlag) {
						query="select count(*),hrs_cd from ris_horse_mst where hrs_alias_cd='"+userHouseAlias+"' group by hrs_cd";
						rst = stmt.executeQuery(query);
						if(rst.next()) {
							if(rst.getInt(1)!=0){
								houseCD=rst.getString(2)==null?"":rst.getString(2);
								query="select count(*),trn_cd from ris_trn_horse_dtl where flag='Y' and hrs_cd='"+houseCD+"' group by trn_cd";
								rst1=stmt1.executeQuery(query);
								if(rst1.next()){
									obj.put("Data", "Owner Already Available For "+userHouseAlias+".");
								} else {
									houseFlag=true;
								}
							} else {
								obj.put("Data", userHouseAlias+" House Number Not Found.");
							}
						} else {
							obj.put("Data", userHouseAlias+" House Number Not Found.");
						}
					} else {
						obj.put("Data","Mobile Number Already Registered. Please Enter New Mobile Number.");
					}
					
					if(mobileFlag && houseFlag) {
						int user_cd = 0;
						query = "select IFNULL(max(EMP_CD),'390000') from HR_EMP_MST";
						rst1 = stmt1.executeQuery(query);
						if (rst1.next()) {
							user_cd = rst1.getInt(1) + 1;
						}
						StringBuffer sb = EncryptTest.encrypt(userPassword);
						FullName = userFirstName + " " + userLastName;
						
						query="insert into ris_trn_horse_dtl (comp_cd,trn_cd,hrs_cd,eff_dt,flag) " +
								"values('101','"+user_cd+"','"+houseCD+"',sysdate(),'Y')";
						stmt1.executeUpdate(query);
						
						query = "insert into HR_EMP_MST (COMP_CD, EMP_CD, EMP_NM, EMP_ABR, MOB_NO, FLAG,JOIN_DT) "
								+ "values('101', '"
								+ user_cd
								+ "','"
								+ FullName
								+ "','-','"
								+ userMobile
								+ "','Y',sysdate())";
						int x = stmt1.executeUpdate(query);
						if (x == 1) {
							query = "insert into SEC_EMP_PASSWORDS (EMP_CD, PASSWORD, LOGIN_STATUS,LAST_CHANGE) "
									+ "values('"
									+ user_cd
									+ "','"
									+ sb.toString() + "','Y',sysdate())";
							stmt1.executeUpdate(query);
							obj.put("Data", "Registered Successfully..");
						} else {
							obj.put("Data", "Not Saved");
						}
						conn.commit();
					}
					
				} else {
					obj.put("Data", "Server Not Getting Required Values..");
				}
			}
		} catch (Exception e) {
			obj.put("Error", "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void linkHouse(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		try {
			if (!param.equals("")) {
				boolean houseFlag=false,mobileFlag=false;
				
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				String userHouseAlias = Json.get("userHouseAlias").toString().trim() == null ? "" : Json.get("userHouseAlias").toString().trim();
				String userMobile = Json.get("userMobile").toString().trim() == null ? "" : Json.get("userMobile").toString().trim();
				String houseCD="" ;
				if (!userHouseAlias.equals("")) {
					query = "select count(*) from HR_EMP_MST where MOB_NO='" + userMobile + "'";
					rst = stmt.executeQuery(query);
					if (rst.next()) {
						if (rst.getInt(1) == 1) {
							mobileFlag=true;
						}
					}
					if(mobileFlag) {
						query="select count(*),hrs_cd from ris_horse_mst where hrs_alias_cd='"+userHouseAlias+"' group by hrs_cd";
						rst = stmt.executeQuery(query);
						if(rst.next()) {
							if(rst.getInt(1)!=0){
								houseCD=rst.getString(2)==null?"":rst.getString(2);
								query="select count(*),trn_cd from ris_trn_horse_dtl where flag='Y' and hrs_cd='"+houseCD+"' group by trn_cd";
								rst1=stmt1.executeQuery(query);
								if(rst1.next()){
									obj.put("Data", "Owner Already Available For "+userHouseAlias+".");
								} else {
									houseFlag=true;
								}
							} else {
								obj.put("Data", userHouseAlias+" House Number Not Found.");
							}
						} else {
							obj.put("Data", userHouseAlias+" House Number Not Found.");
						}
					} else {
						obj.put("Data","Mobile Number Not Registered. Please Enter Registered Mobile Number.");
					}
					
					if(mobileFlag && houseFlag) {
						String user_cd="";
						query = "select emp_cd from HR_EMP_MST where MOB_NO='" + userMobile + "'";
						rst1 = stmt1.executeQuery(query);
						if (rst1.next()) {
							user_cd = rst1.getString(1);
						}
						query="insert into ris_trn_horse_dtl (comp_cd,trn_cd,hrs_cd,eff_dt,flag) " +
							"values('101','"+user_cd+"','"+houseCD+"',sysdate(),'Y')";
						stmt1.executeUpdate(query);
						conn.commit();
						obj.put("Data", "Linked Successfully..");
					}
				}
				
			}
		} catch (Exception e) {
			obj.put("Error", "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void ViewSingleEmplAllCompl(HttpServletRequest request,	HttpServletResponse response) {
		String loginId = "", query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println("ViewSingleEmplAllCompl: " + param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId").toString();
				ArrayList<String> arraySubject = new ArrayList<String>();
				ArrayList<String> arrayDate = new ArrayList<String>();
				ArrayList<String> arrayRequest = new ArrayList<String>();
				ArrayList<String> arrayStatus = new ArrayList<String>();

				JSONObject innerObj = new JSONObject();
				if (!loginId.equals("")) {
					query = "Select Compl_id,case when to_date(sysdate,'dd/mm/yyyy')=to_date(created_date,'dd/mm/yyyy') then to_char(created_date,'HH:MI AM') else to_char(created_date,'DD Mon') end as CDate,compl_subject,status "
							+ "from bi_complain_mst where user_cd ='"
							+ loginId
							+ "' order by Compl_id desc";
					// System.out.println(query);
					rst = stmt.executeQuery(query);
					while (rst.next()) {
						arraySubject.add(rst.getString(3) == null ? "-" : rst.getString(3));
						arrayRequest.add(rst.getString(1) == null ? "-" : rst.getString(1));
						arrayDate.add(rst.getString(2) == null ? "-" : rst.getString(2));
						arrayStatus.add(rst.getString(4) == null ? "-" : rst.getString(4).charAt(0)+ "");
					}
					innerObj.put("Subject", arraySubject);
					innerObj.put("Request", arrayRequest);
					innerObj.put("Date", arrayDate);
					innerObj.put("Status", arrayStatus);
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
	
	@SuppressWarnings("unchecked")
	private void getAllLiveReq(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println("getAllLiveReq: " + param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId").toString();
				ArrayList<String> arraySubject = new ArrayList<String>();
				ArrayList<String> arrayDate = new ArrayList<String>();
				ArrayList<String> arrayRequest = new ArrayList<String>();
				ArrayList<String> arrayStatus = new ArrayList<String>();

				JSONObject innerObj = new JSONObject();
				if (!loginId.equals("")) {
					query = "Select Compl_id,case when date_format(sysdate(),'%d/%m/%Y')=date_format(created_date,'%d/%m/%Y') then date_format(created_date,'%h:%i %p') else date_format(created_date,'%d %b') end as CDate,compl_subject,status "
							+ "from bi_complain_mst where user_cd ='"
							+ loginId
							+ "' and flag is null order by Compl_id desc";
					 System.out.println(query);
					rst = stmt.executeQuery(query);
					while (rst.next()) {
						arraySubject.add(rst.getString(3) == null ? "-" : rst.getString(3));
						arrayRequest.add(rst.getString(1) == null ? "-" : rst.getString(1));
						arrayDate.add(rst.getString(2) == null ? "-" : rst.getString(2));
						arrayStatus.add(rst.getString(4) == null ? "-" : fontCamelCase(rst.getString(4)));
					}
					innerObj.put("Subject", arraySubject);
					innerObj.put("Request", arrayRequest);
					innerObj.put("Date", arrayDate);
					innerObj.put("Status", arrayStatus);
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
	
	@SuppressWarnings("unchecked")
	private void getMonReqGraph(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		try {
			JSONObject innerObj = new JSONObject();
			String count="",month="";
			query = "select count(*),to_char(created_date,'Month') from bi_complain_mst group by to_char(created_date,'Month')";
			System.out.println(query);
			rst = stmt.executeQuery(query);
			while (rst.next()) {
				count+=rst.getInt(1)+"=";
				month+=rst.getString(2) == null ? "-" : rst.getString(2)+"=";
			}
			innerObj.put("count",count.subSequence(0,count.length()-1));
			innerObj.put("month",month.subSequence(0,month.length()-1));
			
			obj.put("Data", innerObj);
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getAllUsers(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		System.out.println("getAllUsers: ");
		try {
			ArrayList<JSONObject> arrayAll_Users = new ArrayList<JSONObject>();
			int i=1;
			JSONObject innerObj = new JSONObject();
			query = "select emp_cd, to_char(join_dt,'dd-Mon-yyyy'), emp_nm, mob_no, user_flag from hr_emp_mst order by emp_cd desc";
			System.out.println(query);
			rst = stmt.executeQuery(query);
			while (rst.next()) {
				innerObj = new JSONObject();
				innerObj.put("No",i+"");
				innerObj.put("Emp_cd",rst.getString(1) == null ? "-" : rst.getString(1));
				innerObj.put("Join_dt",rst.getString(2) == null ? "-" : rst.getString(2));
				innerObj.put("Emp_nm",rst.getString(3) == null ? "-" : rst.getString(3));
				innerObj.put("Mob_no",rst.getString(4) == null ? "-" : rst.getString(4));
				innerObj.put("User_flag",rst.getString(5) == null ? "-" : rst.getString(5));
				arrayAll_Users.add(innerObj);
				i++;
			}
			
			obj.put("Data", arrayAll_Users);
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getAllHouses(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		System.out.println("getAllHouses: ");
		try {
			ArrayList<JSONObject> arrayAll_Users = new ArrayList<JSONObject>();
			int i=1;
			JSONObject innerObj = new JSONObject();
			query = "select hrs_cd,hrs_nm,hrs_alias_cd from ris_horse_mst order by hrs_alias_cd asc";
			System.out.println(query);
			rst = stmt.executeQuery(query);
			while (rst.next()) {
				innerObj = new JSONObject();
				innerObj.put("No",i+"");
				innerObj.put("hrs_cd",rst.getString(1) == null ? "-" : rst.getString(1));
				innerObj.put("hrs_nm",rst.getString(2) == null ? "-" : rst.getString(2));
				innerObj.put("hrs_alias_cd",rst.getString(3) == null ? "-" : rst.getString(3));
				query="select trn_cd from ris_trn_horse_dtl where flag='Y' and hrs_cd='"+rst.getString(1)+"'";
				rst1=stmt1.executeQuery(query);
				if(rst1.next()){
					innerObj.put("usr_flag","Y");
					query="select emp_nm, mob_no from hr_emp_mst where emp_cd='"+rst1.getString(1)+"'";
					rst2=stmt2.executeQuery(query);
					if(rst2.next()) {
						innerObj.put("emp_nm",rst2.getString(1)==null?"-":rst2.getString(1));
						innerObj.put("mob_no",rst2.getString(2)==null?"-":rst2.getString(2));
					} else {
						innerObj.put("emp_nm","-");
						innerObj.put("mob_no","-");
					}
				} else {
					innerObj.put("emp_nm","-");
					innerObj.put("mob_no","-");
					innerObj.put("usr_flag","N");
				}
				arrayAll_Users.add(innerObj);
				i++;
			}
			
			obj.put("Data", arrayAll_Users);
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getNotices(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		System.out.println("getNotices");
		try {
			ArrayList<JSONObject> arrayAll_Notices = new ArrayList<JSONObject>();
			int i=1;
			JSONObject innerObj = new JSONObject();
			query = "select notice_id, not_subject, not_detail, to_char(created_dt,'dd/mm/yy hh24:mi') , remarks, to_id, created_id from bi_bulletin_mst order by notice_id desc";
			System.out.println(query);
			rst = stmt.executeQuery(query);
			while (rst.next()) {
				innerObj = new JSONObject();
				innerObj.put("No",i+"");
				innerObj.put("notice_id",rst.getString(1) == null ? "-" : rst.getString(1));
				innerObj.put("not_subject",rst.getString(2) == null ? "-" : rst.getString(2));
				innerObj.put("not_detail",rst.getString(3) == null ? "-" : rst.getString(3));
				innerObj.put("created_dt",rst.getString(4) == null ? "-" : rst.getString(4));
				innerObj.put("remarks",rst.getString(5) == null ? "-" : rst.getString(5));
				if(rst.getString(6)!=null) {
					query="select hrs_nm from ris_horse_mst where hrs_cd='"+rst.getString(6)+"'";
					rst1=stmt1.executeQuery(query);
					if(rst1.next()) {
						innerObj.put("to_id",rst1.getString(1) == null ? "-" : rst1.getString(1));
					}
				} else {
					innerObj.put("to_id","All");
				}
				if(!rst.getString(7).equalsIgnoreCase("")) {
					query="select emp_nm from hr_emp_mst where emp_cd='"+rst.getString(7)+"'";
					rst1=stmt1.executeQuery(query);
					if(rst1.next()) {
						innerObj.put("created_id",rst1.getString(1) == null ? "-" : rst1.getString(1));
					}
				} else {
					innerObj.put("created_id","-");
				}
				arrayAll_Notices.add(innerObj);
				i++;
			}
			
			obj.put("Data", arrayAll_Notices);
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getNoticesCell(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		try {
			JSONObject innerObj = new JSONObject();
			ArrayList<String> notifId = new ArrayList<String>();
			ArrayList<String> notifSubject = new ArrayList<String>();
			ArrayList<String> notifDate = new ArrayList<String>();
			ArrayList<String> notifDesc = new ArrayList<String>();
			query = "select notice_id, not_subject, not_detail,  case when date_format(sysdate(),'%d/%m/%Y')=date_format(created_dt,'%d/%m/%Y') then date_format(created_dt,'%h:%i %p') else date_format(created_dt,'%d %b') end, remarks, to_id, created_id,date_format(eff_dt,'%d/%m/%Y') from bi_bulletin_mst where to_id is null order by notice_id desc";
			System.out.println(query);
			rst = stmt.executeQuery(query);
			while (rst.next()) {
				notifId.add(rst.getString(1) == null ? "-" : rst.getString(1));
				notifSubject.add(rst.getString(2) == null ? "-" : rst.getString(2));
				notifDate.add(rst.getString(4) == null ? "-" : rst.getString(4));
				notifDesc.add(rst.getString(3) == null ? "-" : rst.getString(3));
				/*innerObj.put("notice_id",rst.getString(1) == null ? "-" : rst.getString(1));
				innerObj.put("not_subject",rst.getString(2) == null ? "-" : rst.getString(2));
				innerObj.put("not_detail",rst.getString(3) == null ? "-" : rst.getString(3));
				innerObj.put("created_dt",rst.getString(4) == null ? "-" : rst.getString(4));
				innerObj.put("remarks",rst.getString(5) == null ? "-" : rst.getString(5));
				innerObj.put("eff_dt",rst.getString(8) == null ? "-" : rst.getString(8));
				if(rst.getString(6)!=null) {
					query="select hrs_nm from ris_horse_mst where hrs_cd='"+rst.getString(6)+"'";
					rst1=stmt1.executeQuery(query);
					if(rst1.next()) {
						innerObj.put("to_id",rst1.getString(1) == null ? "-" : rst1.getString(1));
					}
				} else {
					innerObj.put("to_id","All");
				}
				if(!rst.getString(7).equalsIgnoreCase("")) {
					query="select emp_nm from hr_emp_mst where emp_cd='"+rst.getString(7)+"'";
					rst1=stmt1.executeQuery(query);
					if(rst1.next()) {
						innerObj.put("created_id",rst1.getString(1) == null ? "-" : rst1.getString(1));
					}
				} else {
					innerObj.put("created_id","-");
				}
				arrayAll_Notices.add(innerObj);
				i++;*/
			}
			innerObj.put("not_subject", notifSubject);
			innerObj.put("notice_id", notifId);
			innerObj.put("not_detail", notifDesc);
			innerObj.put("created_dt", notifDate);
			
			obj.put("Data", innerObj);
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getUserNoticeCell(HttpServletRequest request, HttpServletResponse response) {
		String query = "",loginId="";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println("getUserProfile: " + param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId").toString();
				ArrayList<String> notifId = new ArrayList<String>();
				ArrayList<String> notifSubject = new ArrayList<String>();
				ArrayList<String> notifDate = new ArrayList<String>();
				ArrayList<String> notifDesc = new ArrayList<String>();
				JSONObject innerObj = new JSONObject();
				query = "select notice_id, not_subject, not_detail, case when date_format(sysdate(),'%d/%m/%Y')=date_format(created_dt,'%d/%m/%Y') then date_format(created_dt,'%h:%i %p') else date_format(created_dt,'%d %b') end , remarks, to_id, created_id,date_format(eff_dt,''%d/%m/%Y'') from bi_bulletin_mst where to_id='"+loginId+"' order by notice_id desc";
				System.out.println(query);
				rst = stmt.executeQuery(query);
				while (rst.next()) {
					notifId.add(rst.getString(1) == null ? "-" : rst.getString(1));
					notifSubject.add(rst.getString(2) == null ? "-" : rst.getString(2));
					notifDate.add(rst.getString(4) == null ? "-" : rst.getString(4));
					notifDesc.add(rst.getString(3) == null ? "-" : rst.getString(3));
					/*innerObj = new JSONObject();
					innerObj.put("notice_id",rst.getString(1) == null ? "-" : rst.getString(1));
					innerObj.put("not_subject",rst.getString(2) == null ? "-" : rst.getString(2));
					innerObj.put("not_detail",rst.getString(3) == null ? "-" : rst.getString(3));
					innerObj.put("created_dt",rst.getString(4) == null ? "-" : rst.getString(4));
					innerObj.put("remarks",rst.getString(5) == null ? "-" : rst.getString(5));
					innerObj.put("eff_dt",rst.getString(8) == null ? "-" : rst.getString(8));
					if(rst.getString(6)!=null) {
						query="select hrs_nm from ris_horse_mst where hrs_cd='"+rst.getString(6)+"'";
						rst1=stmt1.executeQuery(query);
						if(rst1.next()) {
							innerObj.put("to_id",rst1.getString(1) == null ? "-" : rst1.getString(1));
						}
					} else {
						innerObj.put("to_id","All");
					}
					if(!rst.getString(7).equalsIgnoreCase("")) {
						query="select emp_nm from hr_emp_mst where emp_cd='"+rst.getString(7)+"'";
						rst1=stmt1.executeQuery(query);
						if(rst1.next()) {
							innerObj.put("created_id",rst1.getString(1) == null ? "-" : rst1.getString(1));
						}
					} else {
						innerObj.put("created_id","-");
					}
					arrayAll_Notices.add(innerObj);
					i++;*/
				}
				innerObj.put("not_subject", notifSubject);
				innerObj.put("notice_id", notifId);
				innerObj.put("not_detail", notifDesc);
				innerObj.put("created_dt", notifDate);
				
				obj.put("Data", innerObj);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void getUserProfile(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println("getUserProfile: " + param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId").toString();

				JSONObject innerObj = new JSONObject();
				if (!loginId.equals("")) {
					query = "select date_format(join_dt,'%d-%b-%Y %H:%i'), emp_nm, mob_no, user_flag from hr_emp_mst where emp_cd='"+loginId+"'";
					System.out.println(query);
					rst = stmt.executeQuery(query);
					while (rst.next()) {
						innerObj.put("Join_dt",rst.getString(1)==null?"":rst.getString(1));
						innerObj.put("Emp_nm",rst.getString(2)==null?"":rst.getString(2));
						innerObj.put("Mob_no",rst.getString(3)==null?"":rst.getString(3));
						innerObj.put("User_flag",rst.getString(4)==null?"":rst.getString(4));
					}
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
	
	@SuppressWarnings("unchecked")
	private void insertScanBarcode(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println("getUserProfile: " + param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				String val1=Json.get("val1").toString();
				String val2=Json.get("val2").toString();
				boolean flag=false;
				query="select count(*) from BI_SCAN_BARCODE where VALUE_1='"+val1+"' and VALUE_2='"+val2+"'";
				rst = stmt.executeQuery(query);
				if(rst.next()) {
					if(rst.getInt(1)==0) {
						query = "insert into BI_SCAN_BARCODE (SCAN_DT,VALUE_1,VALUE_2) values(sysdate(),'"+val1+"','"+val2+"')";
						System.out.println(query);
						stmt.executeUpdate(query);
						conn.commit();
						flag=true;
					} else {
						flag=false;
					}
				}
				obj.put("Data", flag);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getScannedBarcode(HttpServletRequest request, HttpServletResponse response) {
		String query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println("getUserProfile: " + param);
		try {
			ArrayList<JSONObject> arrayAll_barcodes = new ArrayList<JSONObject>();
			int i=1;
			JSONObject innerObj = new JSONObject();
			query = "select to_char(scan_dt,'dd/mm/yyyy hh24:mi:ss'), value_1, value_2 from BI_SCAN_BARCODE order by scan_dt desc";
			System.out.println(query);
			rst=stmt.executeQuery(query);
			while (rst.next()) {
				innerObj = new JSONObject();
				innerObj.put("No",i+"");
				innerObj.put("scan_dt",rst.getString(1)==null?"":rst.getString(1));
				innerObj.put("value_1",rst.getString(2)==null?"":rst.getString(2));
				innerObj.put("value_2",rst.getString(3)==null?"":rst.getString(3));
				arrayAll_barcodes.add(innerObj);
				i++;
			}
			obj.put("Data", arrayAll_barcodes);
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	public String fontCamelCase(String str){
		str = (str.charAt(0)+"").toUpperCase()+(str.substring(1)).toLowerCase();
		return str;
	}
	
	@SuppressWarnings("unchecked")
	private void getAllClosedReq(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println("ViewSingleEmplAllCompl: " + param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId").toString();
				ArrayList<String> arraySubject = new ArrayList<String>();
				ArrayList<String> arrayDate = new ArrayList<String>();
				ArrayList<String> arrayRequest = new ArrayList<String>();
				ArrayList<String> arrayStatus = new ArrayList<String>();

				JSONObject innerObj = new JSONObject();
				if (!loginId.equals("")) {
					query = "Select Compl_id,case when date_format(sysdate(),'%d/%m/%Y')=date_format(created_date,'%d/%m/%Y') then date_format(created_date,'%h:%h %p') else date_format(created_date,'%d %b') end as CDate,compl_subject,status "
							+ "from bi_complain_mst where user_cd ='"
							+ loginId
							+ "' and flag='X' order by Compl_id desc";
					// System.out.println(query);
					rst = stmt.executeQuery(query);
					while (rst.next()) {
						arraySubject.add(rst.getString(3) == null ? "-" : rst.getString(3));
						arrayRequest.add(rst.getString(1) == null ? "-" : rst.getString(1));
						arrayDate.add(rst.getString(2) == null ? "-" : rst.getString(2));
						arrayStatus.add(rst.getString(4) == null ? "-" : rst.getString(4).charAt(0)+ "");
					}
					innerObj.put("Subject", arraySubject);
					innerObj.put("Request", arrayRequest);
					innerObj.put("Date", arrayDate);
					innerObj.put("Status", arrayStatus);
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

	@SuppressWarnings("unchecked")
	private void ViewSingleEmplCompl(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", query = "", complId = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId").toString();
				complId = Json.get("complId").toString();

				JSONObject innerObj = new JSONObject();
				if (!loginId.equals("")) {
					query = "Select to_char(created_date,'dd-MON-yyyy hh24:mi'),compl_subject,status,remarks,COMPL_DETAIL "
							+ "from bi_complain_mst where user_cd ='"
							+ loginId
							+ "' and Compl_id='" + complId + "'";
					// System.out.println(query);
					rst = stmt.executeQuery(query);
					while (rst.next()) {
						innerObj.put("created_date", rst.getString(1) == null ? "" : rst.getString(1));
						innerObj.put("compl_subject", rst.getString(2) == null ? "" : rst.getString(2));
						innerObj.put("status", rst.getString(3) == null ? "" : rst.getString(3));
						innerObj.put("remark", rst.getString(4) == null ? "" : rst.getString(4));
						innerObj.put("compl_desc", rst.getString(5) == null ? "" : rst.getString(5));
					}
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
	
	@SuppressWarnings("unchecked")
	private void getSingleReq(HttpServletRequest request, HttpServletResponse response) {
		String query = "", complId = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				complId = Json.get("complId").toString();

				JSONObject innerObj = new JSONObject();
				JSONObject titleObj = new JSONObject();
				JSONObject detailObj = new JSONObject();
				ArrayList<String> statusArr=new ArrayList();
				ArrayList<String> remarkArr=new ArrayList();
				ArrayList<String> updateDtArr=new ArrayList();
				if (!complId.equals("")) {
					query = "select compl_subject, compl_detail, date_format(created_date, '%d/%m/%Y %h:%i %p'), " +
							"remarks, status from bi_complain_mst where compl_id = '" + complId + "'";
					 //System.out.println(query);
					rst = stmt.executeQuery(query);
					if (rst.next()) {
						titleObj.put("compl_subject", rst.getString(1) == null ? "" : rst.getString(1));
						titleObj.put("compl_detail", rst.getString(2) == null ? "" : rst.getString(2));
						titleObj.put("created_date", rst.getString(3) == null ? "" : rst.getString(3));
						titleObj.put("compl_remarks", rst.getString(4) == null ? "" : rst.getString(4));
						titleObj.put("compl_status", rst.getString(5) == null ? "" : rst.getString(5));
					}
					query = "select status, remarks, date_format(update_dt, '%d/%m %h:%i %p') from bi_complain_dtl " +
							"where compl_id = '" + complId + "' order by seq_no desc";
					 //System.out.println(query);
					rst = stmt.executeQuery(query);
					while (rst.next()) {
						statusArr.add(rst.getString(1) == null ? "" : fontCamelCase(rst.getString(1)));
						remarkArr.add(rst.getString(2) == null ? "No Remarks" : rst.getString(2));
						updateDtArr.add(rst.getString(3) == null ? "" : rst.getString(3));
					}
					detailObj.put("statusArr",statusArr);
					detailObj.put("remarkArr",remarkArr);
					detailObj.put("updateDtArr",updateDtArr);
					innerObj.put("Title",titleObj);
					innerObj.put("Detail",detailObj);
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

	@SuppressWarnings("unchecked")
	private void EnterNotice(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", query = "", remarks = "", notSubject = "", notDetail = "",effDt="",toId="";
		long noticeId = 10000;
		int count = 0;
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		JSONObject innerObj = new JSONObject();
		try {
			JSONObject Json = (JSONObject) new JSONParser().parse(param);
			loginId = Json.get("loginId").toString().trim();
			toId = Json.get("toId").toString().trim();
			remarks = Json.get("remarks") == null ? "" : Json.get("remarks").toString().trim().replace("'", "''");
			notSubject = Json.get("notSubject").toString().trim().replace("'", "''");
			notDetail = Json.get("notDetail").toString().trim().replace("'", "''");
			effDt = Json.get("effDt").toString().trim().replace("'", "''");
			
			query = "select nvl(max(notice_id),10000),to_char(to_date(sysdate,'dd/mm/yyyy hh24:mi'),'dd/mm/yyyy hh24:mi') from BI_BULLETIN_MST";
			rst = stmt.executeQuery(query);
			if (rst.next()) {
				noticeId = rst.getLong(1) + 1;
			}
			if (toId.equals("")) {
				query = "insert into BI_BULLETIN_MST (NOTICE_ID, NOT_SUBJECT, NOT_DETAIL, CREATED_DT, EFF_DT, REMARKS, CREATED_ID, DEL_FLAG) " +
						"values('"+noticeId+"','"+notSubject+"','"+notDetail+"',sysdate,to_date('"+effDt+"','dd/mm/yyyy'),'"+remarks+"','"+loginId+"','N')";
				count = stmt2.executeUpdate(query);
			} else {
				query = "insert into BI_BULLETIN_MST (NOTICE_ID, NOT_SUBJECT, NOT_DETAIL, CREATED_DT, EFF_DT, REMARKS, CREATED_ID,TO_ID, DEL_FLAG) " +
						"values('"+noticeId+"','"+notSubject+"','"+notDetail+"',sysdate,to_date('"+effDt+"','dd/mm/yyyy'),'"+remarks+"','"+loginId+"','"+toId+"','N')";	
				System.out.println(query);
				count = stmt2.executeUpdate(query);
				
				query="insert into bi_msg_notification(NOTICE_ID, USER_CD, SUBJECT, CREATED_DT, DEL_FLAG) " +
						"values('"+noticeId+"','"+toId+"','"+notSubject+"',sysdate,'N')";
				System.out.println(query);
				stmt2.executeUpdate(query);
			}
			
			innerObj.put("noticeId", noticeId);
			innerObj.put("status", count);
			obj.put("Data", innerObj);
			if(count!=0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void entercompl(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", query = "", remarks = "", subject = "", detail = "";
		long complId = 10000;
		int count = 0;
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		JSONObject innerObj = new JSONObject();
		try {
			JSONObject Json = (JSONObject) new JSONParser().parse(param);
			loginId = Json.get("loginId").toString().trim();
			remarks = Json.get("remarks").toString().trim() == null ? "" : Json.get("remarks").toString().trim().replace("'", "''");
			subject = Json.get("complSubject").toString().trim().replace("'", "''");
			detail = Json.get("complDetail").toString().trim().replace("'", "''");

			if (!loginId.equals("")) {
				query = "select IFNULL(max(compl_id),10000) from BI_COMPLAIN_MST";
				// System.out.println(query);
				rst = stmt.executeQuery(query);
				if (rst.next()) {
					complId = rst.getLong(1) + 1;
				}

				query = "insert into BI_COMPLAIN_MST (COMPL_ID,CREATED_DATE,user_cd,COMPL_SUBJECT,COMPL_DETAIL,REMARKS,STATUS) "
						+ "values('"
						+ complId
						+ "',sysdate(),'"
						+ loginId
						+ "','"
						+ subject
						+ "','"
						+ detail
						+ "','"
						+ remarks
						+ "','NEW')";
				count = stmt.executeUpdate(query);
				int SeqNo = 0;
				query = "select count(COMPL_ID) from BI_COMPLAIN_DTL where compl_id ='" + complId + "' ";
				System.out.println("select: " + query);
				rst = stmt1.executeQuery(query);
				if (rst.next()) {
					SeqNo = rst.getInt(1) + 1;
				}
				
				query = "insert into BI_COMPLAIN_DTL (COMPL_ID, SEQ_NO, UPDATE_BY, REMARKS, STATUS, UPDATE_DT, FLAG) "
					+ "values('"
					+ complId
					+ "','"
					+ SeqNo
					+ "','"
					+ loginId
					+ "','','NEW',sysdate(),'Y')";
				System.out.println("Insert-TRANS: " + query);
				count = count * stmt2.executeUpdate(query);
				innerObj.put("complId", complId);
			}
			innerObj.put("status", count);
			obj.put("Data", innerObj);
			if(count!=0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
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

					query = "select count(*),to_char(sysdate,'dd/mm/yyyy hh24:mi') from HR_EMP_MST a, SEC_EMP_PASSWORDS b "
							+ " where a.MOB_NO='" + loginId + "' and a.FLAG='Y' and User_flag='Y' and a.emp_cd = b.emp_cd and b.password = '"+sb.toString()+"'";
					System.out.println(query);
					rst = stmt.executeQuery(query);
					if (rst.next()) {
						dateTime = rst.getString(2);
						if (rst.getInt(1) == 0) {
							login_succ = "N";
						} else {
							login_succ = "Y";
							query = "select EMP_NM,EMP_CD from HR_EMP_MST where MOB_NO='"
									+ loginId + "'";
							rst1 = stmt1.executeQuery(query);
							if (rst1.next()) {
								empNm = rst1.getString(1) == null ? "" : rst1.getString(1);
								usercd = rst1.getString(2) == null ? "" : rst1.getString(2);
								HttpSession session = request.getSession();
								session.setAttribute("usercd", rst1.getString(2));
								session.setMaxInactiveInterval(10 * 60);
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
	private void checkLogin(HttpServletRequest request, HttpServletResponse response) {
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

					query = "select count(*),to_char(sysdate,'dd/mm/yyyy hh24:mi') from HR_EMP_MST a, SEC_EMP_PASSWORDS b "
							+ " where a.MOB_NO='" + loginId + "' and a.FLAG='Y' and a.emp_cd = b.emp_cd and b.password = '"+sb.toString()+"'";
					System.out.println(query);
					rst = stmt.executeQuery(query);
					if (rst.next()) {
						dateTime = rst.getString(2);
						if (rst.getInt(1) == 0) {
							login_succ = "N";
						} else {
							login_succ = "Y";
							query = "select EMP_NM,EMP_CD from HR_EMP_MST where MOB_NO='"
									+ loginId + "'";
							rst1 = stmt1.executeQuery(query);
							if (rst1.next()) {
								empNm = rst1.getString(1) == null ? "" : rst1.getString(1);
								usercd = rst1.getString(2) == null ? "" : rst1.getString(2);
								HttpSession session = request.getSession();
								session.setAttribute("usercd", rst1.getString(2));
								session.setMaxInactiveInterval(10 * 60);
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
	private void checkLoginCell(HttpServletRequest request, HttpServletResponse response) {
		String loginId = "", password = "", query = "";
		String param = request.getParameter("Param") == null ? "" : request.getParameter("Param");
		System.out.println(param);
		try {
			if (!param.equals("")) {
				JSONObject Json = (JSONObject) new JSONParser().parse(param);
				loginId = Json.get("loginId")==null?"":Json.get("loginId").toString();
				password = Json.get("password")==null?"":Json.get("password").toString();
				boolean login_succ = false;
				String dateTime = "";
				String usercd = "",houseNm="";
				if (!loginId.equals("") && !password.equals("")) {
					StringBuffer sb = EncryptTest.encrypt(password);

					query="select count(*),date_format(sysdate(),'%d/%m/%Y %h:%i %p') from hr_emp_mst a, SEC_EMP_PASSWORDS b where a.emp_cd=(select trn_cd from ris_trn_horse_dtl where hrs_cd=(select hrs_cd from ris_horse_mst where hrs_alias_cd='"+loginId+"') and flag='Y') and a.FLAG='Y' and a.emp_cd = b.emp_cd and b.password = '"+sb.toString()+"'";
					System.out.println(query);
					rst = stmt.executeQuery(query);
					if (rst.next()) {
						dateTime = rst.getString(2);
						if (rst.getInt(1) == 0) {
							login_succ = false;
						} else {
							query = "select b.hrs_cd,b.hrs_nm from ris_trn_horse_dtl a, ris_horse_mst b where a.hrs_cd=b.hrs_cd and b.hrs_alias_cd='"+loginId+"' and a.flag='Y'";
							System.out.println(query);
							rst1 = stmt1.executeQuery(query);
							if (rst1.next()) {
								login_succ = true;
								usercd = rst1.getString(1) == null ? "" : rst1.getString(1);
								houseNm = rst1.getString(2) == null ? "" : rst1.getString(2);
								obj.put("houseNm", houseNm);
								obj.put("usercd", usercd);
								obj.put("dateTime", dateTime);
							}
							query="select emp_nm,mob_no from hr_emp_mst where emp_cd in (select trn_cd from ris_trn_horse_dtl where flag='Y' and hrs_cd='"+usercd+"' group by trn_cd)";
							System.out.println(query);
							rst1 = stmt1.executeQuery(query);
							if (rst1.next()) {
								obj.put("userName", rst1.getString(1));
								obj.put("userMobile", rst1.getString(2));
							}
						}
					}
				}
				obj.put("loginSuccess",login_succ);
				System.out.println(obj);
				//obj.put("Data", innerObj);
			} else {
				obj.put("Data", "Server Not Getting Required Values..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("Error", "Error: " + e.getLocalizedMessage());
		}
	}
}
