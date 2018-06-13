package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Statement;
import net.sf.json.JSONObject;
import random.Serialnumber;
import util.DBHelper;

/**
 * Servlet implementation class TieServlet
 */
@WebServlet("/TieServlet")
public class TieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TieServlet() {
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
		 
		 String acceptjson = "";
		 String tieID;
		 String t_userID;
		 String Image1;
		 String Image2;
		 String Image3;
		 String time;
		 String title;
		
		 
		 String content;
		 
		 try {
			 	//���ݿ�����
				conn = DBHelper.getConnection();				 
				String sql = "insert into Tie(tieID,t_userID,Image1,Image2,Image3,time,title,content) values(?,?,?,?,?,?,?,?)";
				stmt = conn.prepareStatement(sql);
				Statement stmt2 = conn.createStatement();
				
				//��ȡjson����
	            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(), "utf-8"));
	            StringBuffer sb = new StringBuffer("");
	            String temp;
	            while((temp = br.readLine()) != null){
	                sb.append(temp);
	            }
	            br.close();               
	            acceptjson = sb.toString();
	            System.out.println("=======json is========��������!!!!!!!!!!!"+acceptjson);
	            if(acceptjson != ""){
	                JSONObject jo = JSONObject.fromObject(acceptjson);	                	               
	                System.out.println(jo.get("t_userID"));
	
	
	                System.out.println(jo.get("time"));
	                System.out.println(jo.get("title"));
	       
	                System.out.println(jo.get("content"));
	                Image1 = jo.getString("Image1");
	                Image2 = jo.getString("Image2");
	                Image3 = jo.getString("Image3");
	                title = jo.getString("title");
	                t_userID = jo.getString("t_userID");
	        
	          
	                time = jo.getString("time");
	                content = jo.getString("content");
	  
	                
	                
	                //��������id
	                Serialnumber sn = new Serialnumber();
	                tieID = "t"+sn.getNum();
	                System.out.println(tieID);
	                	                
	                
	                
	                //�����ݿ��������
	                stmt.setString(1, tieID);
	                stmt.setString(2, t_userID);
	                stmt.setString(3, Image1);
	                stmt.setString(4, Image2);
	                stmt.setString(5, Image3);
	                stmt.setString(6, time);
	                stmt.setString(7, title);
	                stmt.setString(8, content);
	                
	                System.out.println("ͼƬ��"+Image1);
	                String sql2 =
	           	         
	           	         "UPDATE  Tie SET Image1 = '"+Image1+"' WHERE tieID = '"
	           	          +tieID+"'";
	                stmt2.executeUpdate(sql2);
	                 sql2 =
		           	         
		           	         "UPDATE  Tie SET Image2 = '"+Image2+"' WHERE tieID = '"
		           	          +tieID+"'";
		                stmt2.executeUpdate(sql2);
		                 sql2 =
			           	         
			           	         "UPDATE  Tie SET Image3 = '"+Image3+"' WHERE tieID = '"
			           	          +tieID+"'";
			                stmt2.executeUpdate(sql2);
	                stmt2.executeUpdate(sql2);
	                stmt.executeUpdate();
	                
	              
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
