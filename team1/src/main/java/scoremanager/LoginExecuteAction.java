package scoremanager;
 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;
 
public class LoginExecuteAction extends Action{
 
	@Override
	public void execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		HttpSession session=request.getSession();
		String id=request.getParameter("id");
		String password=request.getParameter("password");
		TeacherDao dao=new TeacherDao();
		Teacher teacher=dao.login(id, password);
		if (teacher!=null) {
			session.setAttribute("user", teacher);
			response.sendRedirect(request.getContextPath() + "/scoremanager/Menu.action");
			return;
		}
		request.setAttribute("message", "IDまたはパスワードが確認できませんでした");	
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
}