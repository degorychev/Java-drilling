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
import javax.servlet.RequestDispatcher;

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
			response.setContentType("text/html;charset=UTF-8"); // ��������� �� http-������� �������� ��������� lasname
			String lastname = request.getParameter("lastname"); // ��������� ��� �������� ��������� �����������
			ArrayList<Employee> employees = new ArrayList<Employee>(); // �������� �������� �� PostgreSQL
			Class.forName("org.postgresql.Driver").newInstance(); // ��������� ���������� � ��
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/EmployeeDB", "postgres", "1"); // ���������� SQL-�������
			ResultSet rs = con.createStatement().executeQuery("Select id, first_name, last_name, designation, phone " + "From employee " + "Where last_name like '" + lastname + "'"); // ������������ ����������� �������
			while (rs.next()) {
				// �� ������ ������ ������� �����������
				// ������ ������ Employee.
				// �������� ������� ���������� �� ����� ������
				Employee emp = new Employee(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				
				// ���������� ���������� ������� � ���������
				employees.add(emp);
			}
			
			// ��������� ������� � ���������� � ��
			rs.close();
			con.close();
			/*
			// ������� ���������� � ��������� �����������
			PrintWriter out = response.getWriter();
			out.println("��������� ����������<br>");
			for (Employee emp : employees) {
				out.print(emp.getFirstName() + " " + emp.getLastName() + " " + emp.getDesignation() + " " + emp.getPhone() + "<br>");
			}
			*/
			
			// ��������� ����������� � �������� ������� employeesFound
			request.setAttribute("employeesFound", employees);
			// ��������������� http-������� �� �������� index.jsp
			RequestDispatcher dispatcher = getServletContext()
			.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
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