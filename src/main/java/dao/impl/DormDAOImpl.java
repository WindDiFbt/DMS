package dao.impl;

import connection.MySQLConnection;
import dao.DormDAO;
import entity.Dorm;

import java.sql.*;
import java.util.*;

import mapper.RowMapper;
import mapper.RowMapperImpl;

import static dao.statement.DormSQLStatements.*;

public class DormDAOImpl implements DormDAO {
    MySQLConnection mySQLConnection = new MySQLConnection();
    RowMapper rowMapper = new RowMapperImpl();

    @Override
    public List<Dorm> getAll() {
        List<Dorm> dorms = new ArrayList<>();
        try (
                Connection connection = mySQLConnection.getConnection();
                PreparedStatement st = connection.prepareStatement(getAllDorms);
                ResultSet rs = st.executeQuery()
        ) {
            while (rs.next()) {
                dorms.add(rowMapper.dormMapper(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dorms;
    }

    @Override
    public Dorm getByDormName(String dormName) {
        Dorm dorm = null;
        try (
                Connection connection = mySQLConnection.getConnection();
                PreparedStatement st = connection.prepareStatement(getDormByDormName)
        ) {
            st.setString(1, dormName);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) dorm = rowMapper.dormMapper(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dorm;
    }

    @Override
    public Dorm getByDormID(int dormID) {
        Dorm dorm = null;
        try (
                Connection connection = mySQLConnection.getConnection();
                PreparedStatement st = connection.prepareStatement(getDormByDormID)
        ) {
            st.setInt(1, dormID);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) dorm = rowMapper.dormMapper(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dorm;
    }

    @Override
    public int getNumberOfFloorsByDormName(String dormName) {
        int numberOfFloors = 0;
        try (
                Connection connection = mySQLConnection.getConnection();
                PreparedStatement st = connection.prepareStatement(getNumberOfFloorsByDormName)
        ) {
            st.setString(1, dormName);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) numberOfFloors = rs.getInt("number_of_floor");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfFloors;
    }

    @Override
    public int update(Dorm dorm) {
        int result = 0;
        try (
                Connection connection = mySQLConnection.getConnection();
                PreparedStatement st = connection.prepareStatement(updateDorm)
        ) {
            st.setString(1, dorm.getDormName());
            st.setInt(2, dorm.getNumberOfFloor());
            st.setInt(3, dorm.getStatus().getStatusId());
            st.setInt(4, dorm.getDormId());
            result = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int add(Dorm dorm) {
        int result = 0;
        try (
                Connection connection = mySQLConnection.getConnection();
                PreparedStatement st = connection.prepareStatement(addDorm)
        ) {
            st.setString(1, dorm.getDormName());
            st.setInt(2, dorm.getNumberOfFloor());
            st.setInt(3, dorm.getStatus().getStatusId());
            result = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int numberOfDorm() {
        int total = 0;
        try (
                Connection connection = mySQLConnection.getConnection();
                PreparedStatement st = connection.prepareStatement(getNumberOfDorm);
                ResultSet rs = st.executeQuery()
        ) {
            if (rs.next()) total = rs.getInt("total_dorm");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public static void main(String[] args) {
        DormDAOImpl dormDAO = new DormDAOImpl();
        System.out.println(dormDAO.getAll());
        System.out.println(dormDAO.getByDormName("A"));
        System.out.println(dormDAO.getByDormID(2));
    }
}
