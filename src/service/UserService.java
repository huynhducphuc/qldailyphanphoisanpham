package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.User;

public class UserService {
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public ArrayList<User> getUsers() {
		ArrayList<User> results = new ArrayList<User>();
		User user = null;

		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("select userId, username, password from user;");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
				results.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLConnection.closeResultSet(rs);
			SQLConnection.closePrepareStatement(pstmt);
			SQLConnection.closeConnection(con);
		}

		return results;
	}

	public User getUser(int userId) {
		User user = null;

		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("select userId, username, password from user where userId = ?;");
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLConnection.closeResultSet(rs);
			SQLConnection.closePrepareStatement(pstmt);
			SQLConnection.closeConnection(con);
		}

		return user;
	}
	
	public User getUser(String username, String password) {
		User user = null;

		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("select userId, username, password from user where username = ? and password = ?;");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLConnection.closeResultSet(rs);
			SQLConnection.closePrepareStatement(pstmt);
			SQLConnection.closeConnection(con);
		}

		return user;
	}
	
	public int addUser(User user) {
		int result = 0;
		
		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("insert into user(username, password) values(?, ?);");
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
		
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLConnection.closePrepareStatement(pstmt);
			SQLConnection.closeConnection(con);
		}
		
		return result;
	}
	
	public int updateUser(User user) {
		int result = 0;
		
		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("update user set username = ?, password = ? where userId = ?;");
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getUserId());
		
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLConnection.closePrepareStatement(pstmt);
			SQLConnection.closeConnection(con);
		}
		
		return result;
	}
	
	public int deleteUser(int userId) {
		int result = 0;
		
		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("delete from user where userId = ?;");
			pstmt.setInt(1, userId);
		
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLConnection.closePrepareStatement(pstmt);
			SQLConnection.closeConnection(con);
		}
		
		return result;
	}

}
