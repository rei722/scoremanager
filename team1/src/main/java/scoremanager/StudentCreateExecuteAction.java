package scoremanager;
 
import java.util.List;

// Servlet関連
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Beanクラス
import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
// DAOクラス
import dao.StudentDao;
// Action継承
import tool.Action;
 
/**
* 学生登録実行処理
* student_create.jspから送信されたデータを受け取り、
* Studentテーブルへ登録する
*/
public class StudentCreateExecuteAction extends Action {
 
	@Override
	public void execute(
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		 
		// ログイン中の教師情報取得
		Teacher teacher =
				(Teacher) request.getSession()
				.getAttribute("user");
 
		// 教師が所属する学校取得
		School school = teacher.getSchool();
		
		// フォームから入力値を取得
		String entYearStr = request.getParameter("ent_year");
		String no = request.getParameter("number");
		String name = request.getParameter("name");
		String classNum = request.getParameter("class_num");
 
		// StudentDao生成
		StudentDao dao = new StudentDao();
		
		ClassNumDao cNumDao = new ClassNumDao();

		List<String> list = cNumDao.filter(teacher.getSchool());
 
		// 学生番号重複チェック
		if (dao.get(no) != null) {
 
			// エラーメッセージ設定
			request.setAttribute(
					"duplicateError",
					"学生番号が重複しています");
 
			// 入力内容保持
			request.setAttribute("ent_year", entYearStr);
			request.setAttribute("number", no);
			request.setAttribute("name", name);
			request.setAttribute("class_num_set", list);
 
			// 登録画面へ戻る
			request.getRequestDispatcher(
					"/scoremanager/student_create.jsp")
					.forward(request, response);
 
			return;
		}
		// Studentオブジェクト作成
		Student student = new Student();
 
		student.setNo(no);                  // 学生番号
		student.setName(name);              // 氏名
		student.setEntYear(
				Integer.parseInt(entYearStr)); // 入学年度
 
		student.setClassNum(classNum);      // クラス番号
 
		// 新規登録時は在学中
		student.setAttend(true);
 
		// 学校情報セット
		student.setSchool(school);
 
		// DBへ登録
		dao.save(student);
 
		// 完了画面へ遷移
		request.getRequestDispatcher(
				"/scoremanager/student_create_done.jsp")
				.forward(request, response);
	}
}
//StudentCreateExcuteAction.java