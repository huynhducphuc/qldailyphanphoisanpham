package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.City;

public class CityService {

    private Connection con = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public ArrayList<City> getCities() {
        ArrayList<City> results = new ArrayList<City>();
        City city = null;

        try {
            con = SQLConnection.getConnection();
            pstmt = con.prepareStatement("select cityId, cityName from city order by cityName asc");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                city = new City(rs.getInt(1), rs.getString(2));
                results.add(city);
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

    public City getCity(int cityId) {
        City city = null;

        try {
            con = SQLConnection.getConnection();
            pstmt = con.prepareStatement("select cityId, cityName from city where cityId = ?;");
            pstmt.setInt(1, cityId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                city = new City(rs.getInt(1), rs.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLConnection.closeResultSet(rs);
            SQLConnection.closePrepareStatement(pstmt);
            SQLConnection.closeConnection(con);
        }

        return city;
    }

    public int addCity(City ct) {
        int result = 0;
        try {
            con = SQLConnection.getConnection();
            pstmt = con.prepareStatement("insert into city(cityName) values(?);");
            pstmt.setString(1, ct.getCityName());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLConnection.closePrepareStatement(pstmt);
            SQLConnection.closeConnection(con);
        }
        return result;
    }

    public int updateCity(City ct) {
        int result = 0;

        try {
            con = SQLConnection.getConnection();
            pstmt = con.prepareStatement("update city set cityName = ? where cityId = ?;");
            pstmt.setString(1, ct.getCityName());
            pstmt.setInt(2, ct.getCityId());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLConnection.closePrepareStatement(pstmt);
            SQLConnection.closeConnection(con);
        }
        return result;
    }

    public int deleteCity(int cityId) {
        int result = 0;
        try {
            con = SQLConnection.getConnection();
            pstmt = con.prepareStatement("delete from city where cityId = ?");
            pstmt.setInt(1, cityId);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLConnection.closePrepareStatement(pstmt);
            SQLConnection.closeConnection(con);
        }
        return result;
    }

    public ArrayList<City> getCityByName(String name) {
        ArrayList<City> results = new ArrayList<City>();
        City city = null;
        try {
            con = SQLConnection.getConnection();
            pstmt = con.prepareStatement("select cityId, cityName from city where cityName like ? order by cityName asc");
            pstmt.setString(1, "%" + name + "%");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                city = new City(rs.getInt(1), rs.getString(2));
                results.add(city);
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
