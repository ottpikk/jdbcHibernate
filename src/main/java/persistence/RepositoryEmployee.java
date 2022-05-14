package persistence;

import model.Employee;
import model.EmployeeDepartment;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RepositoryEmployee {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public RepositoryEmployee(){
        connection = DBUtil.getDBConnection();
    }

    //DML - insert, update, delete (data manipulation)
    //DDL - create, drop (data definition)
    public void saveEmployee(Employee employee){
        //statement
        //prepareStatement
        String sql = "INSERT INTO employees (firstName, lastName, dateOfBirth, phoneNumber, salary, email)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getFirstName()); //parameterIndex is number of order in parameter and column
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, employee.getDateOfBirth());
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setDouble(5, employee.getSalary());
            preparedStatement.setString(6, employee.getEmail());
            int result = preparedStatement.executeUpdate();  // returns 0 or 1
            if(result > 0){
                System.out.println("Employee saved successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee){
        String sql = "UPDATE employees SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, salary = ?, dateOfBirth = ? " +
                " WHERE employeeId = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setDouble(5, employee.getSalary());
            preparedStatement.setDate(6, employee.getDateOfBirth());
            preparedStatement.setInt(7, employee.getEmployeeId());
            if (preparedStatement.executeUpdate() > 0){
                System.out.println("Employee updated successfully!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateEmployeeSalary(int employeeId, double newSalary){
        String sql = "UPDATE employees SET salary = ? WHERE employeeId = ? ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, newSalary);
            preparedStatement.setInt(2,employeeId);
            if (preparedStatement.executeUpdate() > 0){
                System.out.println("Employee salary updated with success!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteEmployee(int employeeId){
        String sql = "DELETE FROM employees WHERE employeeId = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            if (preparedStatement.executeUpdate() > 0){
                System.out.println("Employee with ID " + employeeId + " deleted successfully !!!");
            } else {
                System.out.println("There is no employee with ID " + employeeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //DQL - select (data query)
    public Employee findEmployeeById(int employeeId){
        String sql = "SELECT * FROM employees WHERE employeeId = ?";
        Employee employee = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,employeeId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                employee = new Employee();
                employee.setEmployeeId(resultSet.getInt("employeeId"));
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("LastName"));
                employee.setEmail(resultSet.getString("email"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setPhoneNumber(resultSet.getString("phoneNumber"));
                employee.setDateOfBirth(resultSet.getDate("dateOfBirth"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public List<Employee> listAllEmployees(){
        String sql = "SELECT * from employees";
        List<Employee> employeeList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("LastName"));
                employee.setEmail(resultSet.getString("email"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setPhoneNumber(resultSet.getString("phoneNumber"));
                employee.setDateOfBirth(resultSet.getDate("dateOfBirth"));
                employee.setEmployeeId(resultSet.getInt("employeeId"));
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public List<EmployeeDepartment> listEmployeeWithDepartment(){
        List<EmployeeDepartment> list = new LinkedList<>();
        String sql =    "SELECT e.employeeId, e.firstName, e.lastName, d.name "+
                        "FROM employees e "+
                        "RIGHT JOIN departments d ON e.departmentId = d.departmentId";

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                EmployeeDepartment employeeDept = new EmployeeDepartment();
                employeeDept.setEmployeeId(resultSet.getInt("e.employeeId"));
                employeeDept.setFirstName(resultSet.getString("e.firstName"));
                employeeDept.setLastName(resultSet.getString("e.lastName"));
                employeeDept.setDepartmentName(resultSet.getString("d.name"));
                list.add(employeeDept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<EmployeeDepartment> searchEmployeeByDepartmentName(String departmentName){

        return null;
    }

    public List<EmployeeDepartment> searchEmployeeByDepartmentId(int departmentId){

        return null;
    }
}
