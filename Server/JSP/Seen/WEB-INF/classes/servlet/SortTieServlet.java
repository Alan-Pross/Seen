package servlet;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoginDAO;
import entity.LoginBean;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DBHelper;
import util.getToken;

/**
 * Servlet implementation class SortTieServlet
 */
@WebServlet("/SortTieServlet")
public class SortTieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	String bottleID;
	String abID;
	String title;
	String throwID;
	String content;
	String answerID;
	String time;
	 LoginDAO user = new LoginDAO();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SortTieServlet() {
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
		
		PrintWriter pw = response.getWriter();
		String sql;
		int Sort;
        String acceptjson = "";
		
		try {
			
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while((temp = br.readLine()) != null){
                sb.append(temp);
            }
            br.close();           
            acceptjson = sb.toString();
            System.out.println(sb);
           
            if(acceptjson != "")
            {
                JSONObject jo = JSONObject.fromObject(acceptjson);
                
             
                
                Sort = jo.getInt("Sort");   
                conn = DBHelper.getConnection();
                System.out.println(Sort);
              
		   if (Sort==1)
			 sql = "SELECT tieID FROM Tie ORDER BY Time  DESC";
		   else  sql = "SELECT tieID FROM Tie ORDER BY pageviews  DESC";
		   stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			JSONArray jsonReply = new JSONArray(); 
			while(rs.next()){
			
				JSONObject json = new JSONObject(); 
				String tieID = rs.getString("tieID");
				
				json.put("tieID", tieID);
				jsonReply.add(json);
			                    }
			
			
			 pw.write(jsonReply.toString());   
				pw.flush();  
				pw.close(); 
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
