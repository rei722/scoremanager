//登録
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
 
public class TestRegistExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
 
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
 
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao();
 
        // パラメータ取得
        String entYearStr = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subjectCd = request.getParameter("f3");
        String no = request.getParameter("f4");
 
        int testNo = Integer.parseInt(no);
 
        Subject subject = subjectDao.get(subjectCd, teacher.getSchool());
 
        List<Test> testList = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();
 
        // インデックスでループしてパラメータ取得
        int i = 0;
        while (request.getParameter("student_no_" + i) != null) {
 
            String studentNo = request.getParameter("student_no_" + i);
            String pointStr = request.getParameter("point_" + i);
 
            int point = 0;
            try {
                point = Integer.parseInt(pointStr);
                if (point < 0 || point > 100) {
                    errors.put("point_" + i, "0〜100の数値を入力してください");
                }
            } catch (NumberFormatException e) {
                errors.put("point_" + i, "数値を入力してください");
            }
 
            Student student = new Student();
            student.setNo(studentNo);
            student.setClassNum(classNum);
 
            Test test = new Test();
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(teacher.getSchool());
            test.setNo(testNo);
            test.setPoint(point);
 
            testList.add(test);
            i++;
        }
 
        // エラーがなければ保存
        if (errors.isEmpty()) {
            testDao.save(testList);
            request.getRequestDispatcher("/scoremanager/test_regist_done.jsp")
                    .forward(request, response);  // ← 完了画面へ
        } else {
            // エラーがあればJSPに戻す
            request.setAttribute("f1", entYearStr);
            request.setAttribute("f2", classNum);
            request.setAttribute("f3", subjectCd);
            request.setAttribute("f4", no);
            request.setAttribute("test_list", testList);
            request.setAttribute("subject", subject);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/scoremanager/test_regist.jsp")
                    .forward(request, response);  // ← 入力画面に戻す
        }
    }
}