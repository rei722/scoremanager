package scoremanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

//成績登録画面の初期表示および検索処理を行う
public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest request,HttpServletResponse response) throws Exception {

		// セッションからログイン中の教員情報を取得
		HttpSession session = request.getSession();

		Teacher teacher = (Teacher) session.getAttribute("user");

		// 検索条件初期化
		String entYearStr = "";
		String classNum = "";
		String subjectCd = "";
		String no = "";

		int entYear = 0;
		int testNo = 0;

		// テスト一覧
		List<Test> testList = new ArrayList<>();

		// DAO生成
		SubjectDao subjectDao = new SubjectDao();

		ClassNumDao cNumDao = new ClassNumDao();

		TestDao testDao = new TestDao();

		StudentDao studentDao = new StudentDao();

		// エラーメッセージ格納用
		Map<String, String> errors = new HashMap<>();

		// 検索条件を取得
		entYearStr = request.getParameter("f1");
		classNum = request.getParameter("f2");
		subjectCd = request.getParameter("f3");
		no = request.getParameter("f4");

		// 入学年度を数値へ変換
		if (entYearStr != null && !entYearStr.isEmpty()) {
		    entYear = Integer.parseInt(entYearStr);
		}
		
		// 回数を数値へ変換
		if (no != null && !no.isEmpty()) {
		    testNo = Integer.parseInt(no);
		}

		// クラス一覧
		List<String> list = cNumDao.filter(teacher.getSchool());

		// 科目一覧
		List<Subject> subjectList =subjectDao.filter(teacher.getSchool());

		// 学生一覧取得
		List<Student> studentList =studentDao.filter(teacher.getSchool(), true);

		// 入学年度一覧（存在する年度のみ）
		Set<Integer> entYearSet = new TreeSet<>();

		for (Student student : studentList) {
			entYearSet.add(student.getEntYear());
		}

		// 回数一覧作成
		List<Integer> noSet = new ArrayList<>();

		noSet.add(1);
		noSet.add(2);

		// 検索条件チェック
		boolean isValid =
				entYear != 0
				&& classNum != null
				&& !classNum.isEmpty()
				&& subjectCd != null
				&& !subjectCd.isEmpty()
				&& testNo != 0;

		// 検索条件が入力されているか確認
		if (isValid) {

			// 科目情報取得
			Subject subject =subjectDao.get(subjectCd,teacher.getSchool());

			// テスト情報検索
			testList = testDao.filter(entYear, classNum, subject, testNo, teacher. getSchool());
			
			// 科目情報をリクエストへ設定
			request.setAttribute("subject", subject);

		} else if (
				entYearStr != null
				|| classNum != null
				|| subjectCd != null
				|| no != null) {

			// 検索条件未入力時のエラーメッセージ
			errors.put(
					"f1","入学年度とクラスと科目と回数を選択してください");
		}

		// JSPの検索条件保持
		request.setAttribute("f1", entYearStr);
		request.setAttribute("f2", classNum);
		request.setAttribute("f3", subjectCd);
		request.setAttribute("f4", no);
		
		// JSPの検索結果
		request.setAttribute("test_list", testList);
		
		//JSPの検索一覧
		request.setAttribute("class_num_set", list);
		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("no_set", noSet);
		request.setAttribute("subject_set", subjectList);
		
		//エラー
		request.setAttribute("errors", errors);

		// 成績登録画面へフォワード
		request.getRequestDispatcher("/scoremanager/test_regist.jsp")
				.forward(request, response);
	}
}
//TestRegistAction.java