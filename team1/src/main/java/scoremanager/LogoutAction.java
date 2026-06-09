package scoremanager;
 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import tool.Action;
 
public class LogoutAction extends Action{
	@Override
	public void execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		request.getRequestDispatcher("/scoremanager/logout.jsp")
			.forward(request, response);
	}
}