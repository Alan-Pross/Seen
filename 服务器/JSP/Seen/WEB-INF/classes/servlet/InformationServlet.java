package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;


import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Statement;
import net.sf.json.JSONObject;
import util.DBHelper;


import entity.InformationBean;;
/**
 * Servlet implementation class InformationServlet
 */
@WebServlet("/InformationServlet")
public class InformationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      InformationBean info=new InformationBean();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InformationServlet() {
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
		 Statement stmt = null;	
		 
		 try {
			 	//���ݿ�����
				conn = DBHelper.getConnection();				 
				stmt = conn.createStatement();
			
				 String acceptjson = "";
				
				//��ȡjson����
	            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(), "utf-8"));
	            StringBuffer sb = new StringBuffer("");
	            String temp;
	            PrintWriter pw = response.getWriter();
	    		
	            while((temp = br.readLine()) != null){
	                sb.append(temp);
	            }
	            br.close();               
	            acceptjson = sb.toString();
	            System.out.println("=======json is========���¸�����Ϣ��������������"+acceptjson);
	            if(acceptjson != ""){
	                JSONObject jo = JSONObject.fromObject(acceptjson);	                	               
	                
	                
	               String userID = jo.getString("userID");
	               String  nickname = jo.getString("nickname");
	               String  signature = jo.getString("signature");
	               int   property1 = 0;
	               int   property2= 0;
	               boolean sex = true;
//	               String headImage = jo.getString("headImage");
	                
	               String sql =
	         
	         "UPDATE  Login SET nickname = '"+nickname+"' ,signature = '"+signature+"' , property1 = '"
	         		 +property1+"',property2='"+property2+"',sex='"+sex+"'WHERE userID = '"
	         
	          +userID+"'";
	               
	               
	               stmt.executeUpdate(sql);
	               System.out.println(sql);
	                //�����ݿ��������
//	           	JSONObject json = new JSONObject(); 
//				
//				System.out.println("sucess");
//	                
//	           	boolean code = true;
//				json.put("code", code);
//				pw.write(json.toString());   
//				pw.flush();  
//				pw.close(); 
	            }
	            else{
	                System.out.println("get the json failed");
	            }
	        } catch (Exception e) {
	            // TODO: handle exception
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
