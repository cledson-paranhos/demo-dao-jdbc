package model.dao;

import db.DB;
import model.dao.Impl.DepartmentDaoJDBC;

public class DepartmentFactory {
    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
