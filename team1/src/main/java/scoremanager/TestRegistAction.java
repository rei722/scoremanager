//一覧
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
 
public class TestRegistAction extends Action {
 
	@Override
	public void execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
 
		HttpSession session = request.getSession();
 
		Teacher teacher = (Teacher) session.getAttribute("user");
 
		String entYearStr = "";
		String classNum = "";
		String subjectCd = "";
		String no = "";
 
		int entYear = 0;
		int testNo = 0;
 
		List<Test> testList = new ArrayList<>();
 
		SubjectDao subjectDao = new SubjectDao();
 
		ClassNumDao cNumDao = new ClassNumDao();
 
		TestDao testDao = new TestDao();
 
		StudentDao studentDao = new StudentDao();
 
		Map<String, String> errors = new HashMap<>();
 
		// パラメータ取得
		entYearStr = request.getParameter("f1");
		classNum = request.getParameter("f2");
		subjectCd = request.getParameter("f3");
		no = request.getParameter("f4");
 
		// 入学年度変換
		try {
			if (entYearStr != null && !entYearStr.isEmpty()) {
				entYear = Integer.parseInt(entYearStr);
			}
 
		} catch (NumberFormatException e) {
			entYear = 0;
		}
 
		// 回数変換
		try {
			if (no != null && !no.isEmpty()) {
				testNo = Integer.parseInt(no);
			}
		} catch (NumberFormatException e) {
			testNo = 0;
		}
 
		// クラス一覧
		List<String> list = cNumDao.filter(teacher.getSchool());
 
		// 科目一覧
		List<Subject> subjectList =subjectDao.filter(teacher.getSchool());
 
		// 入学年度一覧（存在する年度のみ）
		List<Student> studentList =studentDao.filter(teacher.getSchool(),true);
 
		Set<Integer> entYearSet = new TreeSet<>();
 
		for (Student student : studentList) {
			entYearSet.add(student.getEntYear());
		}
 
		// 回数一覧
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
 
		// 検索
		if (isValid) {
 
			Subject subject =subjectDao.get(subjectCd,teacher.getSchool());
 
			testList = testDao.filter(entYear, classNum, subject, testNo, teacher. getSchool());
			request.setAttribute("subject", subject);
 
		} else if (
				entYearStr != null
				|| classNum != null
				|| subjectCd != null
				|| no != null) {
 
			errors.put(
					"f1","入学年度とクラスと科目と回数を選択してください");
		}
 
		// JSPへ渡す
		request.setAttribute("f1", entYearStr);
		request.setAttribute("f2", classNum);
		request.setAttribute("f3", subjectCd);
		request.setAttribute("f4", no);
		request.setAttribute("test_list", testList);
		request.setAttribute("class_num_set", list);
		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("no_set", noSet);
		request.setAttribute("subject_set", subjectList);
		request.setAttribute("errors", errors);
 
		request.getRequestDispatcher("/scoremanager/test_regist.jsp")
				.forward(request, response);
	}
}