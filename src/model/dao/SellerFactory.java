package model.dao;

import db.DB;
import model.dao.Impl.SellerDaoJDBC;

public class SellerFactory {
    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }
}
