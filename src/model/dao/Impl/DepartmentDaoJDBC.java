package model.dao.Impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection connection = null;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement("INSERT INTO department (Id, Name) VALUES (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, department.getId());
            preparedStatement.setString(2, department.getName());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    department.setId(resultSet.getInt(1));
                }
            } else {
                throw new DbException("Unexpeted error! No rows affected");
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closePreparedStatment(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE DEPARTMENT SET Name=? WHERE Id=?");

            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("No department found with Id: " + department.getId());
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closePreparedStatment(preparedStatement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM DEPARTMENT WHERE Id=?");

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("No department found deleted Id: " + id);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closePreparedStatment(preparedStatement);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM DEPARTMENT WHERE Id = ?");

            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return instantiateDepartment(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closePreparedStatment(preparedStatement);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM DEPARTMENT");

            resultSet = preparedStatement.executeQuery();

            List<Department> listDepartment = new ArrayList<>();

            while (resultSet.next()) {
                Department department = instantiateDepartment(resultSet);
                listDepartment.add(department);
            }
            return listDepartment;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closePreparedStatment(preparedStatement);
        }
    }

    private Department instantiateDepartment(ResultSet resultSet) {
        try {
            Department department = new Department();
            department.setId(resultSet.getInt(1));
            department.setName(resultSet.getString(2));
            return department;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
