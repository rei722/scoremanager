package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

//テスト情報の削除処理を行う
public class TestDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response)
                        throws Exception {

    	// セッションからログイン中の教員情報を取得
        HttpSession session = request.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");
        
        // 学校情報を取得
        School school = teacher.getSchool();

     // 削除対象の情報を取得
        String studentNo = request.getParameter("student_no");
        String subjectCd = request.getParameter("f3");
        int no = Integer.parseInt(request.getParameter("f4"));

     // 学生情報を生成
        Student student = new Student();
        student.setNo(studentNo);

     // 科目情報を取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);

     // 削除対象のテスト情報を生成
        Test test = new Test();
        test.setStudent(student);
        test.setSubject(subject);
        test.setSchool(school);
        test.setNo(no);

     // テスト情報を削除
        TestDao testDao = new TestDao();
        testDao.delete(test);

        request.getRequestDispatcher("TestRegist.action").forward(request, response);
    }
}
//TestDeleteExecuteAction.java