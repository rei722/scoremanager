package scoremanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

// 学生番号による成績参照を行う
public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	// セッションからログイン中の教員情報を取得
        HttpSession session = request.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 学生番号を取得
        String studentNo = request.getParameter("studentNo");

        // DAO生成
        SubjectDao subjectDao = new SubjectDao();
        ClassNumDao cNumDao = new ClassNumDao();
        StudentDao studentDao = new StudentDao();
        
     // 画面表示用データ取得
        List<String> classList = cNumDao.filter(school);
        List<Subject> subjectList = subjectDao.filter(school);
        List<Student> allStudentList = studentDao.filter(school, false);

        // 入学年度一覧作成
        Set<Integer> entYearSet = new TreeSet<>();
        for (Student s : allStudentList) {
            entYearSet.add(s.getEntYear());
        }

        // エラーメッセージ格納用
        Map<String, String> errors = new HashMap<>();

        // 学生番号未入力チェック
        if (studentNo == null || studentNo.isEmpty()) {

            errors.put("studentNo", "学生番号を入力してください");

            request.setAttribute("errors", errors);
            request.setAttribute("f", "st");

         // 入力画面へ戻る
            request.getRequestDispatcher("/scoremanager/test_list.jsp")
                   .forward(request, response);
            return;
        }

     // 学生情報を検索
        List<Student> studentList = studentDao.filter(school, false);
        Student student = null;
        for (Student s : studentList) {
        	// 学生番号が一致する学生を検索
        	if (s.getNo().trim().equals(studentNo.trim())) {
                student = s;
                break;
            }
        }
        
        // 該当学生が存在しない場合
        if (student == null) {
            errors.put("studentNo", "該当する学生が存在しません");
            
            request.setAttribute("errors", errors);
            
         // 入力画面へ戻る
            request.getRequestDispatcher("/scoremanager/test_list.jsp")
                   .forward(request, response);
            return;
        }

     // 学生の成績情報を取得
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> list = dao.filter(student);

        // 成績データが存在しない場合
        if (list == null || list.isEmpty()) {
            errors.put("studentNo", "成績データが存在しません");
        }

     // 学生番号検索モードを設定
        request.setAttribute("f", "st");

     // 成績情報を設定
        request.setAttribute("testList", list);
        request.setAttribute("student", student);

     // 入力された学生番号を設定
        request.setAttribute("studentNo", studentNo);

     // 検索条件用データを設定
        request.setAttribute("class_num_set", classList);
        request.setAttribute("subject_set", subjectList);
        request.setAttribute("ent_year_set", entYearSet);
        
     // エラー情報を設定
        request.setAttribute("errors", errors);

     // 成績参照画面へフォワード
        request.getRequestDispatcher("/scoremanager/test_list.jsp")
               .forward(request, response);
    }
}
//TestListStudentExrcuteAction.jsva