//科目一覧
package scoremanager;

import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {
	@Override
	public void execute(HttpServletRequest request,HttpServletResponse response
			)throws Exception{
		//セッション取得
		HttpSession session = request.getSession();
		
		//ログイン中の情報取得
		Teacher teacher =(Teacher) session.getAttribute("user");
		
		//所属している学校取得
		School school = teacher.getSchool();
		
		SubjectDao dao = new SubjectDao();
		
		List<Subject> subjectList=dao.filter(school); 
		
		//jspへ引き渡し
		request.setAttribute("subjectList",subjectList);
		
		RequestDispatcher rd = request.getRequestDispatcher("/scoremanager/subject_list.jsp");
		
		rd.forward(request, response);
		
	}
}
