package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response
			)throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher =(Teacher)  session.getAttribute("user");
		School school = teacher.getSchool();
		
		//cd受け取り
		String cd = request.getParameter("cd");
		
		SubjectDao dao = new SubjectDao();
		
		//削除対象
		Subject subject = dao.get(cd,school);
		
		//jspへ
		request.setAttribute("subject",subject);
		
		request.getRequestDispatcher("/scoremanager/subject_delete.jsp")
		.forward(request, response);
	}
}
