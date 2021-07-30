package application;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DbConnect {
	public static Connection c;
	public static Statement st;
	static int id;
	static{
		try {
			// for mysql
			connect();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	static public void connect() throws Exception {
		if(c==null || c.isClosed()) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskdb?useSSL=false&allowPublicKeyRetrieval=true",
					"root", "89299");
			st=c.createStatement();
		}
	}
	static public void disconnect() throws Exception {
		c.close();
	}
	
	static public boolean insertWebcamShotStart(InputStream i) throws Exception {
		connect();
//		st.executeUpdate("insert into webcamshot (taskdate,startimage) values(CURRENT_DATE,'"+i+"')");
//		ResultSet r=st.executeQuery("select max(id) from webcamshot");
//		r.next();
//		id=r.getInt(1);
		
		PreparedStatement p=c.prepareStatement("insert into webcamshot (taskdate,startimage) values(CURRENT_DATE,?)");
		p.setBinaryStream(1, i);
		p.executeUpdate();
		PreparedStatement getMaxId=c.prepareStatement("select max(id) from webcamshot");
		ResultSet r=getMaxId.executeQuery();
		r.next();
		id=r.getInt(1);
		
		
		return true;
	}
	static public boolean insertWebcamShotStop(InputStream i) throws Exception {
		connect();
		//st.executeUpdate("update webcamshot set stopimage='"+i+"' where id="+id);
		
		PreparedStatement p=c.prepareStatement("update webcamshot set stopimage=? where id=?");
		p.setBinaryStream(1, i);
		p.setInt(2, id);
		p.executeUpdate();
		
		return true;
	}

	static public boolean insertScreenShot(InputStream i) throws Exception {
		connect();
		//st.executeUpdate("insert into screenshot (taskdate,image) values(CURRENT_DATE,'"+i+"')");
		
		PreparedStatement p=c.prepareStatement("insert into screenshot (taskdate,image) values(CURRENT_DATE,?)");
		p.setBinaryStream(1, i);
		p.executeUpdate();
		
		return true;
	}
	static public ArrayList<InputStream> getWebcamShot(LocalDate l) throws Exception {
		connect();
		java.sql.Date d=java.sql.Date.valueOf(l);
		ResultSet r=st.executeQuery("select * from webcamshot where taskdate='"+d+"'");
		ArrayList<InputStream> images = new ArrayList<>();
		while(r.next()) {
			images.add(r.getBinaryStream("startimage"));
			images.add(r.getBinaryStream("stopimage"));
		}
		return images;
	}
	static public ArrayList<InputStream> getScreenShot(LocalDate l) throws Exception {
		connect();
		java.sql.Date d=java.sql.Date.valueOf(l);
		ResultSet r=st.executeQuery("select * from screenshot where taskdate='"+d+"'");
		ArrayList<InputStream> images = new ArrayList<>();
		while(r.next()) {
			images.add(r.getBinaryStream("image"));
		}
		return images;
	}
}
