package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;




import entity.LoginBean;
import util.DBHelper;


public class LoginDAO {
	
		//��ȡ���е�¼��Ϣ
		public ArrayList<LoginBean> getAllItems(){
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			ArrayList<LoginBean> list = new ArrayList<LoginBean>();//��¼��Ϣ����
			try {
				conn = DBHelper.getConnection();
				String sql = "select * from Login;";	//SQL���
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				while(rs.next()){
					LoginBean login = new LoginBean();
					login.setUserID(rs.getString("userID"));
					login.setPassword(rs.getString("password"));
					
					list.add(login);	//��һ���˵���Ϣ���뼯��
				}
				return list;//���ؼ���
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null; 
			} finally{
				//�ͷ����ݼ�����
				if(rs != null){
					try {
						rs.close();
						rs = null;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//�ͷ�������
				if(stmt != null){
					try {
						stmt.close();
						stmt = null;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
		//����ID������Ϣ
		public LoginBean getLoginBeanById(String id) throws SQLException{
			Connection conn = null;
			PreparedStatement stmt = null;
			//stmt.setString(1,id);
			ResultSet rs = null;
			try {
				conn = DBHelper.getConnection();
				String sql = "select * from Login where userID=";	//SQL���
				stmt = conn.prepareStatement(sql+"'"+id+"'");
				System.out.println(sql+"'"+id+"'");
				rs = stmt.executeQuery();
				if(rs.next()){
					LoginBean login = new LoginBean();
					login.setUserID(rs.getString("userID"));
					login.setPassword(rs.getString("password"));
					login.setNickname(rs.getString("nickname"));
					login.setSex(rs.getBoolean("sex"));
					login.setToken(rs.getString("token"));
					login.setCicleimage(rs.getString("headImage"));
					login.setSignature(rs.getString("signature"));
				
					return login;
				}else{
					return null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null; 
			} finally{
				//�ͷ����ݼ�����
				if(rs != null){
					try {
						rs.close();
						rs = null;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//�ͷ�������
				if(stmt != null){
					try {
						stmt.close();
						stmt = null;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		public boolean addUser(String userID,String password,String token) throws SQLException{
			Connection conn = null;
			Statement stmt = null;

			try {
				conn = DBHelper.getConnection();
				stmt = conn.createStatement();
				String sql1 = "insert into Login(userID,password,token,nickname) values('"+userID+"','"+password+"','"+token+"','"+userID+"')";	//SQL���
				System.out.println(sql1);
				stmt.executeUpdate(sql1);
			

				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} finally{
				
				//�ͷ�������
				if(stmt != null){
					try {
						stmt.close();
						stmt = null;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
}
