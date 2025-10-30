package application;

import model.dao.SellerDao;
import model.dao.SellerFactory;
import model.entities.Seller;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = SellerFactory.createSellerDao();

        System.out.println("Seller FindById:");

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);
    }
}
