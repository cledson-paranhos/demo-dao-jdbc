package model.dao.Impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?"
            );

            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Department department = instantiateDepartment(resultSet);

                return instantiateSeller(resultSet, department);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeConnection();
        }
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setDepartment(department);
        return seller;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "ORDER BY Name"
            );

            resultSet = preparedStatement.executeQuery();

            List<Seller> listSeller = new ArrayList<>();
            Map<Integer, Department> mapDepartment = new HashMap<>();

            while (resultSet.next()) {
                int departmentId = resultSet.getInt("DepartmentId");

                Department dep = mapDepartment.get(departmentId);

                if (dep == null) {
                    dep = instantiateDepartment(resultSet);
                    mapDepartment.put(departmentId, dep);
                }
                Seller seller = instantiateSeller(resultSet, dep);
                listSeller.add(seller);
            }
            return listSeller;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeConnection();
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE DepartmentId = ? " +
                            "ORDER BY Name"
            );

            preparedStatement.setInt(1, department.getId());

            resultSet = preparedStatement.executeQuery();

            List<Seller> listSeller = new ArrayList<>();
            Map<Integer, Department> mapDepartment = new HashMap<>();

            while (resultSet.next()) {
                int departmentId = resultSet.getInt("DepartmentId");

                Department dep = mapDepartment.get(departmentId);

                if (dep == null) {
                    dep = instantiateDepartment(resultSet);
                    mapDepartment.put(departmentId, dep);
                }
                Seller seller = instantiateSeller(resultSet, dep);
                listSeller.add(seller);
            }
            return listSeller;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeConnection();
        }
    }
}
