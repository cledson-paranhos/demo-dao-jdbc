package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;


public class ProgramDepartment {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("Department findById");
        Department department = departmentDao.findById(1);
        System.out.println(department);

        System.out.println();

        System.out.println("Department findAll");
        List<Department> listDepartment = departmentDao.findAll();
        listDepartment.forEach(System.out::println);

        System.out.println();

        System.out.println("Department insert");
        department = new Department(6, "Financeiro");
        departmentDao.insert(department);
        System.out.println("Department refresh " + department);

        System.out.println();

        System.out.println("Department update");
        department = departmentDao.findById(6);
        departmentDao.update(department);
        System.out.println("Department refresh " + department);

        System.out.println();

        System.out.println("Department delete");
        departmentDao.deleteById(6);
        System.out.println("Department deleted successful");
    }
}
