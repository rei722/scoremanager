package scoremanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

//成績登録処理を行う
public class TestRegistExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	// セッションからログイン中の教員情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

     // DAO生成
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao();

     // 検索条件を取得
        String entYearStr = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");
        String no = request.getParameter("f4");

     // 回数を数値へ変換
        int testNo = Integer.parseInt(no);
     // 科目情報を取得
        Subject subject = subjectDao.get(subjectCd, teacher.getSchool());

     // テスト一覧とエラーメッセージ格納用
        List<Test> testList = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();

     // 送信された学生情報を順番に取得
        int i = 0;
        while (request.getParameter("student_no_" + i) != null) {
        	// 学籍番号と点数を取得
            String studentNo = request.getParameter("student_no_" + i);
            String pointStr = request.getParameter("point_" + i);

            int point = 0;
         // 点数の入力チェック
            try {
                point = Integer.parseInt(pointStr);
             // 点数範囲チェック
                if (point < 0 || point > 100) {
                    errors.put("point_" + i, "0〜100の数値を入力してください");
                }
            } catch (NumberFormatException e) {
            	// 数値以外が入力された場合
                errors.put("point_" + i, "数値を入力してください");
            }

            // 学生情報を生成
            Student student = new Student();
            student.setNo(studentNo);
            student.setClassNum(classNum);

         // テスト情報を生成
            Test test = new Test();
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(teacher.getSchool());
            test.setNo(testNo);
            test.setPoint(point);

         // テスト一覧へ追加
            testList.add(test);
            i++;
        }

     // 入力エラーがない場合
        if (errors.isEmpty()) {
        	// テスト情報を保存
            testDao.save(testList);
         // 完了画面へ遷移
            request.getRequestDispatcher("/scoremanager/test_regist_done.jsp")
                    .forward(request, response);  // ← 完了画面へ
        } else {
        	// エラー時は入力値を再表示するため設定
            request.setAttribute("f1", entYearStr);
            request.setAttribute("f2", classNum);
            request.setAttribute("f3", subjectCd);
            request.setAttribute("f4", no);
            
         // テスト情報・エラー情報を設定
            request.setAttribute("test_list", testList);
            request.setAttribute("subject", subject);
            request.setAttribute("errors", errors);
            
            // 入力画面へ戻る
            request.getRequestDispatcher("/scoremanager/test_regist.jsp")
                    .forward(request, response);
        }
    }
}
//TestRegistExecuteAction.java