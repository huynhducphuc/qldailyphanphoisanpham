package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.District;

public class DistrictService {
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public ArrayList<District> getDistricts() {
		ArrayList<District> results = new ArrayList<District>();
		District district = null;

		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("select districtId, districtName, cityId from district order by districtName asc;");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				district = new District(rs.getInt(1), rs.getString(2),
						rs.getInt(3));
				results.add(district);
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
	
	public ArrayList<District> getDistricts(int cityId) {
		ArrayList<District> results = new ArrayList<District>();
		District district = null;

		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("select districtId, districtName, cityId from district where cityId = ? order by districtName asc;");
			pstmt.setInt(1, cityId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				district = new District(rs.getInt(1), rs.getString(2),
						rs.getInt(3));
				results.add(district);
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
	
	public District getDistrict(int districtId) {
		District district = null;
		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("select districtId, districtName, cityId from district where districtId = ?;");
			pstmt.setInt(1, districtId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				district = new District(rs.getInt(1), rs.getString(2),
						rs.getInt(3));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLConnection.closeResultSet(rs);
			SQLConnection.closePrepareStatement(pstmt);
			SQLConnection.closeConnection(con);
		}
		return district;
	}
	
	public int addDistrict(District district) {
		int result = 0;
		
		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("insert into district(districtName, cityId) values(?, ?);");
			pstmt.setString(1, district.getDistrictName());
			pstmt.setInt(2, district.getCityId());
		
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLConnection.closePrepareStatement(pstmt);
			SQLConnection.closeConnection(con);
		}
		
		return result;
	}
	
	public int updateDistrict(District district) {
		int result = 0;
		
		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("update district set districtName = ?, cityId = ? where districtId = ?;");
			pstmt.setString(1, district.getDistrictName());
			pstmt.setInt(2, district.getCityId());
			pstmt.setInt(3, district.getDistrictId());
		
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLConnection.closePrepareStatement(pstmt);
			SQLConnection.closeConnection(con);
		}
		
		return result;
	}
	
	public int deleteDistrict(int districtId) {
		int result = 0;
		
		try {
			con = SQLConnection.getConnection();
			pstmt = con.prepareStatement("delete from district where districtId = ?");
			pstmt.setInt(1, districtId);
		
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLConnection.closePrepareStatement(pstmt);
			SQLConnection.closeConnection(con);
		}
		
		return result;
	}
    public ArrayList<District> getDistrictByName(String name) {
        ArrayList<District> results = new ArrayList<District>();
        District district = null;
        try {
            con = SQLConnection.getConnection();
            pstmt = con.prepareStatement("select districtId, districtName, cityId from district where districtName like ? order by districtName asc");
            pstmt.setString(1, "%" + name + "%");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                district = new District(rs.getInt(1), rs.getString(2), rs.getInt(3));
                results.add(district);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLConnection.closeConnection(con);
            SQLConnection.closePrepareStatement(pstmt);
            SQLConnection.closeResultSet(rs);
        }

        return results;
    }
    
}
