//科目更新
package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest request,HttpServletResponse response
			)throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		School school = teacher.getSchool();
		
		//cd受け取り
		String cd = request.getParameter("cd");
		
		SubjectDao dao = new SubjectDao();
		
		//既存データ取得
		Subject subject = dao.get(cd, school);
		
		System.out.println("cd = " + cd);
		System.out.println("school = " + school.getCd());
		System.out.println("subject = " + subject);
		
		//jspへ
		request.setAttribute("subject",subject);
		
		request.getRequestDispatcher("/scoremanager/subject_update.jsp")
		.forward(request, response);
	}

}
