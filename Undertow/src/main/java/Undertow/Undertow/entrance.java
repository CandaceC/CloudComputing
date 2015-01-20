package Undertow.Undertow;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class entrance extends HttpServlet {


	public void init() throws ServletException {

	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userid=request.getParameter("userid");
		String date="";
		String location="";
		String m="";
		String n="";
		
		
		String requesturl = request.getRequestURI();
		String query = requesturl.substring(requesturl.lastIndexOf("/") + 1,
				requesturl.lastIndexOf("/") + 3);
		switch (query) {
		case "q1":
			response.sendRedirect("pathToResource");
			break;
		case "q2":
			response.sendRedirect("http://LiveQ2-100637681.us-east-1.elb.amazonaws.com/"+requesturl.substring(requesturl.lastIndexOf("/") + 1,requesturl.length()-1));
			System.out.println(response.toString());
			break;
		case "q3":
			System.out.println("================="+userid+"=================");
			response.sendRedirect("http://ec2-54-173-237-155.compute-1.amazonaws.com/q3?userid="+userid);
			break;
		case "q4":
			date=request.getParameter("date");
			location=request.getParameter("location");
			m=request.getParameter("m");
			n=request.getParameter("n");
			System.out.println("================="+location+"=================");
			response.sendRedirect("http://ec2-54-172-171-234.compute-1.amazonaws.com/q4?date="+date+"&location="+location+"&m="+m+"&n="+n);
			break;
		case "q5":
			m=request.getParameter("m");
			n=request.getParameter("n");
			response.sendRedirect("http://ec2-54-172-107-246.compute-1.amazonaws.com/q5?m=" + m + "&n=" + n);
			break;
		case "q6":
			m=request.getParameter("m");
			n=request.getParameter("n");
			response.sendRedirect("http://ec2-54-172-107-246.compute-1.amazonaws.com/q6?m=" + m + "&n=" + n);
			break;
		}
	}

}
