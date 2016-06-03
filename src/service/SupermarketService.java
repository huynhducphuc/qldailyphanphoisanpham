package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Supermarket;

public class SupermarketService {

    private Connection con = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public ArrayList<Supermarket> getSupermarkets() {
        ArrayList<Supermarket> results = new ArrayList<Supermarket>();
        Supermarket supermarket = null;

        try {
            con = SQLConnection.getConnection();
            pstmt = con
                    .prepareStatement("select supmId, districtId, supmName, supmAddress, supmPhone, supmEmail, supmWebsite, longitude, latitude from supermarket;");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                supermarket = new Supermarket(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9));
                results.add(supermarket);
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

    public ArrayList<Supermarket> getSupermarkets(int districtId) {
        ArrayList<Supermarket> results = new ArrayList<Supermarket>();
        Supermarket supermarket = null;

        try {
            con = SQLConnection.getConnection();
            pstmt = con
                    .prepareStatement("select supmId, districtId, supmName, supmAddress, supmPhone, supmEmail, supmWebsite, longitude, latitude from supermarket where districtId = ?;");
            pstmt.setInt(1, districtId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                supermarket = new Supermarket(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9));
                results.add(supermarket);
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

    public ArrayList<Supermarket> getSupermarkets(String keyword, int cityId,
            int districtId) {
        ArrayList<Supermarket> results = new ArrayList<Supermarket>();
        Supermarket supermarket = null;

        String sqlQuery = "select supmId, districtId, supmName, supmAddress, supmPhone, supmEmail, supmWebsite, longitude, latitude from supermarket;";

        if (cityId == 0) {
            if (keyword == null || !(keyword.length() > 0)) {
                sqlQuery = "select supmId, districtId, supmName, supmAddress, supmPhone, supmEmail, supmWebsite, longitude, latitude from supermarket;";
            } else {
                sqlQuery = "select supmId, districtId, supmName, supmAddress, supmPhone, supmEmail, supmWebsite, longitude, latitude from supermarket where supmName like '%"
                        + keyword + "%' ;";
            }
        } else {
            if (districtId == 0) {
                if (keyword == null || !(keyword.length() > 0)) {
                    sqlQuery = "select s.supmId, s.districtId, s.supmName, s.supmAddress, s.supmPhone, s.supmEmail, s.supmWebsite, s.longitude, s.latitude from supermarket s join district d on d.districtId = s.districtId join city c on c.cityId = d.cityId where c.cityId = "
                            + cityId + ";";
                } else {
                    sqlQuery = "select s.supmId, s.districtId, s.supmName, s.supmAddress, s.supmPhone, s.supmEmail, s.supmWebsite, s.longitude, s.latitude from supermarket s join district d on d.districtId = s.districtId join city c on c.cityId = d.cityId where c.cityId = "
                            + cityId
                            + " and s.supmName like '%"
                            + keyword
                            + "%' ;";
                }
            } else {
                if (keyword == null || !(keyword.length() > 0)) {
                    sqlQuery = "select s.supmId, s.districtId, s.supmName, s.supmAddress, s.supmPhone, s.supmEmail, s.supmWebsite, s.longitude, s.latitude from supermarket s join district d on d.districtId = s.districtId join city c on c.cityId = d.cityId where c.cityId = "
                            + cityId
                            + " and d.districtId = "
                            + districtId
                            + " ;";
                } else {
                    sqlQuery = "select s.supmId, s.districtId, s.supmName, s.supmAddress, s.supmPhone, s.supmEmail, s.supmWebsite, s.longitude, s.latitude from supermarket s join district d on d.districtId = s.districtId join city c on c.cityId = d.cityId where c.cityId = "
                            + cityId
                            + " and d.districtId = "
                            + districtId
                            + " and s.supmName like '%" + keyword + "%' ;";
                }
            }
        }

        try {
            con = SQLConnection.getConnection();
            pstmt = con.prepareStatement(sqlQuery);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                supermarket = new Supermarket(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9));
                results.add(supermarket);
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

    public Supermarket getSupermarket(int supermarketId) {
        Supermarket supermarket = null;

        try {
            con = SQLConnection.getConnection();
            pstmt = con
                    .prepareStatement("select supmId, districtId, supmName, supmAddress, supmPhone, supmEmail, supmWebsite, longitude, latitude from supermarket where supmId = ?;");
            pstmt.setInt(1, supermarketId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                supermarket = new Supermarket(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLConnection.closeResultSet(rs);
            SQLConnection.closePrepareStatement(pstmt);
            SQLConnection.closeConnection(con);
        }

        return supermarket;
    }

    public int addSupermarket(Supermarket supermarket) {
        int result = 0;

        try {
            con = SQLConnection.getConnection();
            pstmt = con
                    .prepareStatement("insert into supermarket(districtId, supmName, supmAddress, supmPhone, supmEmail, supmWebsite, longitude, latitude) value (?, ?, ?, ?, ?, ?, ?, ?);");
            pstmt.setInt(1, supermarket.getDistrictId());
            pstmt.setString(2, supermarket.getName());
            pstmt.setString(3, supermarket.getAddress());
            pstmt.setString(4, supermarket.getPhone());
            pstmt.setString(5, supermarket.getEmail());
            pstmt.setString(6, supermarket.getWebsite());
            pstmt.setString(7, supermarket.getLongitude());
            pstmt.setString(8, supermarket.getLagtitude());
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLConnection.closePrepareStatement(pstmt);
            SQLConnection.closeConnection(con);
        }

        return result;
    }

    public int updateSupermarket(Supermarket supermarket) {
        int result = 0;

        try {
            con = SQLConnection.getConnection();
            pstmt = con
                    .prepareStatement("update supermarket set districtId = ?, supmName = ?, supmAddress = ?, supmPhone = ?, supmEmail = ?, supmWebsite = ?, longitude = ?, latitude = ? where supmId = ?;");
            pstmt.setInt(1, supermarket.getDistrictId());
            pstmt.setString(2, supermarket.getName());
            pstmt.setString(3, supermarket.getAddress());
            pstmt.setString(4, supermarket.getPhone());
            pstmt.setString(5, supermarket.getEmail());
            pstmt.setString(6, supermarket.getWebsite());
            pstmt.setString(7, supermarket.getLongitude());
            pstmt.setString(8, supermarket.getLagtitude());
            pstmt.setInt(9, supermarket.getId());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLConnection.closePrepareStatement(pstmt);
            SQLConnection.closeConnection(con);
        }

        return result;
    }

    public int deleteSupermarket(int supermarketId) {
        int result = 0;

        try {
            con = SQLConnection.getConnection();
            pstmt = con
                    .prepareStatement("delete from supermarket where supmId = ?;");
            pstmt.setInt(1, supermarketId);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLConnection.closePrepareStatement(pstmt);
            SQLConnection.closeConnection(con);
        }

        return result;
    }

    public ArrayList<Supermarket> getSupermarketByName(String name) {
        ArrayList<Supermarket> results = new ArrayList<Supermarket>();
        Supermarket supermarket = null;
        try {
            con = SQLConnection.getConnection();
            pstmt = con.prepareStatement("select supmId, districtId, supmName, supmAddress, supmPhone, supmEmail, supmWebsite, longitude, latitude from supermarket where supmName like ?");
            pstmt.setString(1, "%" + name + "%");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                supermarket = new Supermarket(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9));
                results.add(supermarket);
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
