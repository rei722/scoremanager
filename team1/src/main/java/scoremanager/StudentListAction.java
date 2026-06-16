package scoremanager;
 
// Listを使うために必要
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

// Servlet関連
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
// DAO
import dao.StudentDao;
// 親クラス
import tool.Action;
 
 
// 学生一覧表示処理
public class StudentListAction extends Action {
 
	/*
	 * executeメソッド
	 * FrontControllerから呼ばれる
	 */
	@Override
	public void execute(
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
 
		/*
		 * ログイン情報取得
		 *
		 * Sessionに保存されている
		 * Teacherを取得する
		 */
		Teacher teacher =
				(Teacher)request.getSession()
				.getAttribute("user");
 
 
		/*
		 * 学校情報取得
		 *
		 * DAO検索時に必要
		 */
		ClassNumDao dao = new ClassNumDao();
		StudentDao sdao = new StudentDao();
 
		//クラス・入学年度
		List<String> classList = dao.filter(teacher.getSchool());
		List<Student> studentList = sdao.filter(teacher.getSchool(),false);

		 //入学年度一覧作成(在校生のみ)
		Set<Integer> entYearSet = new TreeSet<>();
		for (Student student : studentList) {
			entYearSet.add(student.getEntYear());
		}

		// 入学年度
				String entYearStr =
						request.getParameter("f1");
 
				// クラス番号
				String classNum =
						request.getParameter("f2");
 
				// 在学中チェックボックス
				String active =
						request.getParameter("isAttend");
 
 
				/*
				 * チェックボックス判定
				 *
				 * チェックあり → true
				 * チェックなし → false
				 */
				boolean isAttend = active != null;
 
		List<Student> students;
 
 
		
		 //検索条件分岐

 
		// 入学年度未選択
		if (entYearStr == null
				|| entYearStr.isEmpty()) {
 
			
			 //学校のみ検索
			students = sdao.filter(
					teacher.getSchool(),
					isAttend
			);
 
		} else {
 
			
			 //String → int変換
			int entYear =
					Integer.parseInt(entYearStr);
 
 
			// クラス未選択
			if (classNum == null
					|| classNum.isEmpty()) {
 
				 //年度検索
				students = sdao.filter(
						teacher.getSchool(),
						entYear,
						isAttend
				);
 
			} else {
 
				 // 年度 + クラス検索
				students = sdao.filter(
						teacher.getSchool(),
						entYear,
						classNum,
						isAttend
				);
			}
		}
 
		//検索条件を渡す
		request.setAttribute("class_num_set", classList);
		request.setAttribute("ent_year_set", entYearSet);
 
		// 学生一覧も渡す
		request.setAttribute("students", students);
 
		request.setAttribute("f1", entYearStr);
		request.setAttribute("f2", classNum);
		request.setAttribute("isAttend", isAttend);
 
		 //JSPへ画面遷移
		request.getRequestDispatcher(
				"/scoremanager/student_list.jsp"
		).forward(request, response);
	}
}