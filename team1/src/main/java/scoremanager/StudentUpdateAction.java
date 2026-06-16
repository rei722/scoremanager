package scoremanager;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action{
	
	@Override
	public void execute(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		String no = req.getParameter("no");
		
		StudentDao dao = new StudentDao();
		
		Student student = dao.get(no);
		
		// クラス一覧取得
	    ClassNumDao cdao = new ClassNumDao();
	    List<String> classList =
	            cdao.filter(student.getSchool());
		
		req.setAttribute("student", student);
		req.setAttribute("class_num_set", classList);
		
		req.getRequestDispatcher(
				"/scoremanager/student_update.jsp")
				.forward(req, res);
	}
}