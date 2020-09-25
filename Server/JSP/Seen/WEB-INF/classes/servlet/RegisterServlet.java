package servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;


import util.getToken;
import net.sf.json.JSONObject;
import dao.LoginDAO;
import entity.LoginBean;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private LoginDAO idao = new LoginDAO();
	//��¼����
	private LoginBean loginbean = new LoginBean();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		Connection conn = null;
		PreparedStatement stmt = null;		 
		ResultSet rs = null;
		 
		String acceptjson = "";
		
		try {
			 PrintWriter pw = response.getWriter();
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while((temp = br.readLine()) != null){
                sb.append(temp);
            }
            br.close();           
            acceptjson = sb.toString();
            if(acceptjson != ""){
                JSONObject jo = JSONObject.fromObject(acceptjson);	                	               
                System.out.println(jo.get("ע���˺�ΪuserID"));
                System.out.println(jo.get("����Ϊpassword"));
               String userID =jo.getString("userID");
               String password =jo.getString("password");
           
           
		
		
		
		
			loginbean = idao.getLoginBeanById(userID.toString().trim());
			
			if(loginbean == null){	
			    getToken gt = new   getToken(userID,userID);
				idao.addUser(userID, password,gt.token);///�������id �˶�����
				JSONObject json = new JSONObject(); 
				
				System.out.println("ע��ɹ��ˣ���������������������������");
			
				boolean code = true;
				
				json.put("code", code);
				pw.write(json.toString());   
				pw.flush();  
				pw.close(); 
			
//				request.getSession().setAttribute("userID",userID);
				
				
			
			
				
				
				
			}
			else
			{  
				JSONObject json = new JSONObject(); 
				boolean code = false;
				json.put("code", code);
				pw.write(json.toString());  
				System.out.println("faied");
				pw.flush();  
				pw.close();
			}
            }
            }
            catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
