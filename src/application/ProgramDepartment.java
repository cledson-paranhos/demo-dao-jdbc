package application;

import model.dao.DepartmentDao;
import model.dao.DepartmentFactory;
import model.entities.Department;

import java.util.List;


public class ProgramDepartment {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DepartmentFactory.createDepartmentDao();

        System.out.println("Department findById" );
        Department department = departmentDao.findById(1);
        System.out.println(department);

        System.out.println();

        System.out.println("Department findAll" );
        List<Department> listDepartment = departmentDao.findAll();
        listDepartment.forEach(System.out::println);
    }
}
