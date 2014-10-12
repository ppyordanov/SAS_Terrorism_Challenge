package db;

import graph.Entity;
import graph.EntityPair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertEvent {
	static Connection con;
	static final String insertSQL = 
			"INSERT INTO WikiEvent (eventid, title, url, description, parents, children, third, eventdate, lat, lon)" +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	static PreparedStatement s;
	public InsertEvent() {
		con = DBConnections.getConnection();
		try {
			s = con.prepareStatement(insertSQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void Insert(Entity e) {
		String title = "";
		for (String word : e.getPage().split(" ")) {
			title += "_" + word;
		}
		String relevent = "";
		if (e.getSimilarPages() != null)
			for (EntityPair s : e.getSimilarPages()) {
				relevent += "," + s.entity.getId();
			}
		try {
			s.setString(1, e.getId());
			s.setString(2, e.getPage());
			s.setString(3, "http://en.wikipedia.org/wiki/" + title.substring(1));
			s.setString(4, "");
			s.setString(5,  relevent.substring(1));
			s.setString(6, "");
			s.setString(7, "");
			s.setString(8, "");
			s.setString(9, "");
			s.setString(10, "");
			s.execute();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
}
