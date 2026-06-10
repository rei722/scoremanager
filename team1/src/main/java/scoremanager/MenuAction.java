package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import tool.Action;

//メニュー画面を表示する
public class MenuAction extends Action{
	
	@Override
	public void execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
	
		// 既存のセッションを取得
		HttpSession session = request.getSession(false);
		
		// 未ログインの場合
        if (session == null || session.getAttribute("user") == null) {
        	// ログイン画面へリダイレクト
            response.sendRedirect(request.getContextPath() + "/scoremanager/Login.action");
            return;
        }

     // メニュー画面へフォワード
        request.getRequestDispatcher("/scoremanager/menu.jsp")
            .forward(request, response);
    }
}
//MenuAction.java
