package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLConnection {
	private static Connection con = null;

	/**
	 * <h1>getConnection</h1>Mo ket noi den co so du lieu.
	 * @return con Connection
	 * @throws ClassNotFoundException Khong tim duoc driver
	 * @throws SQLException Khong the mo duoc ket noi.
	 */
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager
					.getConnection("jdbc:mysql://localhost:3307/qldailyppsanphamdb?useUnicode=true&characterEncoding=UTF-8&"
              + "user=root");

		} catch (ClassNotFoundException e) {
			System.err.println("Can not get SQLServerDriver class !");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Can not open connection !");
			e.printStackTrace();
		}

		return con;
	}

	/**
	 * <h1>closeConnection</h1>Dong ket noi den co so du lieu.
	 * @throws SQLException
	 */
	public static void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.err.println("Can not close Connection !");
			e.printStackTrace();
		}
	}

	/**
	 * <h1>closeResultSet</h1>Dong ResultSet.
	 * @throws SQLException
	 */
	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			System.err.println("Can not close ResultSet !");
			e.printStackTrace();
		}
	}

	/**
	 * <h1>closePrepareStatement</h1>Dong PrepareStatement.
	 * @throws SQLException
	 */
	public static void closePrepareStatement(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			System.err.println("Can not close PreparedStatement  !");
			e.printStackTrace();
		}
	}
}
