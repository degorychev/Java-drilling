package ru.ulstu.vt.servletLab;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Servlet implementation class EmployeeServlet
*/
@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	* @see HttpServlet#HttpServlet()
	*/
	
	public EmployeeServlet() {
		super();	
	}
	
	/**
	* @see HttpServlet#doGet(HttpServletRequest request,
	HttpServletResponse
	* response)
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			response.setContentType("text/html;charset=UTF-8"); // Получение из http-запроса значения параметра lasname
			String lastname = request.getParameter("lastname"); // Коллекция для хранения найденных сотрудников
			ArrayList<Employee> employees = new ArrayList<Employee>(); // Загрузка драйвера БД PostgreSQL
			Class.forName("org.postgresql.Driver").newInstance(); // Получение соединения с БД
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/EmployeeDB", "postgres", "1"); // Выполнение SQL-запроса
			ResultSet rs = con.createStatement().executeQuery("Select id, first_name, last_name, designation, phone " + "From employee " + "Where last_name like '" + lastname + "'"); // Перечисление результатов запроса
			while (rs.next()) {
				// По каждой записи выборки формируется
				// объект класса Employee.
				// Значения свойств заполяются из полей записи
				Employee emp = new Employee(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				
				// Добавление созданного объекта в коллекцию
				employees.add(emp);
			}
			
			// Закрываем выборку и соединение с БД
			rs.close();
			con.close();
			// Выводим информацию о найденных сотрудниках
			PrintWriter out = response.getWriter();
			out.println("Найденные сотрудники<br>");
			for (Employee emp : employees) {
				out.print(emp.getFirstName() + " " + emp.getLastName() + " " + emp.getDesignation() + " " + emp.getPhone() + "<br>");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServletException(ex);
		}
	}
	
	/**
	* @see HttpServlet#doPost(HttpServletRequest request,
	HttpServletResponse
	* response)
	*/
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	// TODO Auto-generated method stub
		doGet(request, response);
	}
}