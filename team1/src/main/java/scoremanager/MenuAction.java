package scoremanager;
 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import tool.Action;
 
public class MenuAction extends Action{
	@Override
	public void execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/scoremanager/Login.action");
            return;
        }
 
        request.getRequestDispatcher("/scoremanager/menu.jsp")
            .forward(request, response);
    }
}