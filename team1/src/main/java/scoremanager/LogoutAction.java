package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import tool.Action;

//ログアウト処理を行う
public class LogoutAction extends Action{
	
	@Override
	public void execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		
		// 既存のセッションを取得
		HttpSession session = request.getSession(false);
		
		// セッションが存在する場合
		if (session != null) {
			// セッションを無効化してログアウト
			session.invalidate();
		}
		
		// ログアウト完了画面へフォワード
		request.getRequestDispatcher("/scoremanager/logout.jsp")
			.forward(request, response);
	}
}
//LogoutAction.java
