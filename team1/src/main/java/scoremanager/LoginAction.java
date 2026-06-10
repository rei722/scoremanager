package scoremanager;
 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tool.Action;
 
//ログイン画面を表示する
public class LoginAction extends Action {
	@Override
	public void execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		
		// ログイン画面へフォワード
		request.getRequestDispatcher("/scoremanager/login.jsp")
		.forward(request, response);
	}
}