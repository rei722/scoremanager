//削除チェック
package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response
			)throws Exception{
		
		HttpSession session = request.getSession();
		//ログイン中の情報取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		School school = teacher.getSchool();
		
		//入力値取得
		String cd = request.getParameter("cd");
		
		//削除処理
		SubjectDao dao = new SubjectDao();
		Subject subject = dao.get(cd,school);
		
		dao.delete(subject);
		
		request.getRequestDispatcher("/scoremanager/subject_delete_done.jsp")
		.forward(request,response);
		
	}
}
