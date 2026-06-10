package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

//ログイン認証を行う
public class LoginExecuteAction extends Action{

	@Override
	public void execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {
		
		// セッション取得
		HttpSession session=request.getSession();
		
		// ログイン画面から入力されたIDとパスワードを取得
		String id=request.getParameter("id");
		String password=request.getParameter("password");
		
		// 教員情報を検索
		TeacherDao dao=new TeacherDao();
		Teacher teacher=dao.login(id, password);
		
		// 認証成功時
		if (teacher!=null) {
			// ログイン情報をセッションに保存
			session.setAttribute("user", teacher);
			// メニュー画面へリダイレクト
			response.sendRedirect(request.getContextPath() + "/scoremanager/Menu.action");
			return;
		}
		// 認証失敗時のメッセージを設定
		request.setAttribute("error", "IDまたはパスワードが確認できませんでした");	
		
		// ログイン画面へ戻る
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
}
//LoginExecuteAction.java