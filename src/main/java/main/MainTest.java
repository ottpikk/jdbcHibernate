package main;

import model.Employee;
import model.EmployeeDepartment;
import persistence.RepositoryEmployee;

import java.util.List;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {

        Employee employee = new Employee();
        employee.setFirstName("Johnny");
        employee.setLastName("Pineapple");
        employee.setDateOfBirth(25,11,1955);
        employee.setPhoneNumber("0-800-888-123");
        employee.setSalary(1234.56);
        employee.setEmail("somenthing@somenthing.com");
        employee.setEmployeeId(17);

        RepositoryEmployee repositoryEmployee = new RepositoryEmployee();

        /*
         repositoryEmployee.saveEmployee(employee);
         repositoryEmployee.updateEmployeeSalary(10,4567.89);
         repositoryEmployee.deleteEmployee(12);
         repositoryEmployee.updateEmployee(employee);


        List<Employee> listAllEmp = repositoryEmployee.listAllEmployees();
        for (Employee emp : listAllEmp){
            System.out.println(emp.toString());
        }


        int id = 25;
        Employee empResult = repositoryEmployee.findEmployeeById(id);
        if (empResult != null){
            System.out.println(empResult.toString());
        } else {
            System.out.println("Employee with ID "+id+ " not registered!");
        }
        */


        List<EmployeeDepartment> listAllEmp = repositoryEmployee.listEmployeeWithDepartment();
        for (EmployeeDepartment empDept : listAllEmp){
            System.out.println(empDept.toString());
        }


    }
}
