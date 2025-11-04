package application;

import model.dao.SellerDao;
import model.dao.SellerFactory;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class ProgramSeller {
    public static void main(String[] args) {
        SellerDao sellerDao = SellerFactory.createSellerDao();

        System.out.println("Seller FindById:" );
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println();

        System.out.println("Seller FindByDepartment:" );
        Department department = new Department(2, "" );
        List<Seller> listDepartment = sellerDao.findByDepartment(department);
        listDepartment.forEach(System.out::println);

        System.out.println();

        System.out.println("Seller FindAll:" );
        List<Seller> listAllSeller = sellerDao.findAll();
        listAllSeller.forEach(System.out::println);

        System.out.println("Seller Insert:" );
        seller = new Seller(1, "Greg", "Greg@gmail.com", new Date(), 3000.00, department);
        sellerDao.insert(seller);
        System.out.println("Seller Inserting " + seller);

        System.out.println("Seller Update:" );
        seller = sellerDao.findById(1);
        seller.setName("Marta Green");
        seller.setEmail("MartaGreen@Gmail.com");
        sellerDao.update(seller);
        System.out.println("Seller Updating " + seller);

        System.out.println("Seller Delete:" );
        sellerDao.deleteById(20);
        System.out.println("Seller deleting sucessful");
    }
}
