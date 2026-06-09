package scoremanager;
 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tool.Action;
 
public class LoginAction extends Action {
	@Override
	public void execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		request.getRequestDispatcher("/scoremanager/login.jsp")
		.forward(request, response);
	}
}