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

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

//科目別成績参照処理を行う
public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	// セッションからログイン中の教員情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 検索条件を取得
        String f1 = request.getParameter("f1");
        String f2 = request.getParameter("f2");
        String f3 = request.getParameter("f3");

     // エラーメッセージ格納用
        Map<String, String> errors = new HashMap<>();

     // DAO生成
        SubjectDao subjectDao = new SubjectDao();
        ClassNumDao cNumDao = new ClassNumDao();
        StudentDao studentDao = new StudentDao();

     // 検索条件の入力チェック
        if (f1 == null || f1.isEmpty()
                || f2 == null || f2.isEmpty()
                || f3 == null || f3.isEmpty()) {

            errors.put("f1", "入学年度とクラスと科目を選択してください");

         // 画面表示用データ取得
            List<String> classList = cNumDao.filter(school);
            List<Subject> subjectList = subjectDao.filter(school);
            List<Student> studentList = studentDao.filter(school, false);
         // 入学年度一覧作成
            Set<Integer> entYearSet = new TreeSet<>();
            
            for (Student s : studentList) {
                entYearSet.add(s.getEntYear());
            }
            
         // 検索条件用データを設定
            request.setAttribute("class_num_set", classList);
            request.setAttribute("subject_set", subjectList);
            request.setAttribute("ent_year_set", entYearSet);
            
         // エラー情報を設定
            request.setAttribute("errors", errors);

         // 成績参照画面へ戻る
            request.getRequestDispatcher("/scoremanager/test_list.jsp")
                    .forward(request, response);
            return;
        }

        // 検索条件を設定
        int entYear = Integer.parseInt(f1);
        String classNum = f2;

     // 科目情報を取得
        Subject subject = subjectDao.get(f3, school);

     // 科目別成績情報を取得
        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> list = dao.filter(entYear, classNum, subject, school);
        
     // データが存在しない場合は空リストを設定
        if (list == null) {
            list = new ArrayList<>();
        }
     // 表示する最大回数を取得
        int maxNo = 2;
        if (list != null) {
            for (TestListSubject row : list) {
                for (int no : row.getPoints().keySet()) {
                    if (no > maxNo) maxNo = no;
                }
            }
        }

     // 画面表示用データ取得
        List<String> classList = cNumDao.filter(school);
        List<Subject> subjectList = subjectDao.filter(school);
        List<Student> studentList = studentDao.filter(school, false);

        // 入学年度一覧作成
        Set<Integer> entYearSet = new TreeSet<>();
        for (Student s : studentList) {
            entYearSet.add(s.getEntYear());
        }

        // 科目検索モードを設定
        request.setAttribute("f", "sj");
        
     // 検索結果を設定
        request.setAttribute("testList", list);
        request.setAttribute("subject", subject);
     // 検索条件を設定
        request.setAttribute("f1", f1);
        request.setAttribute("f2", f2);
        request.setAttribute("f3", f3);
     // 表示用データを設定
        request.setAttribute("maxNo", maxNo);
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
//TestListSubjectExrcuteAction.jsva