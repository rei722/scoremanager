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
 
public class TestDeleteExecuteAction extends Action {
 
    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response)
                        throws Exception {
    	//セッション
        HttpSession session = request.getSession();
 
        Teacher teacher = (Teacher) session.getAttribute("user");
        
        //所属している学校を取得
        School school = teacher.getSchool();
 
        // パラメータ取得
        String studentNo = request.getParameter("student_no");
        String subjectCd = request.getParameter("f3");
        int no = Integer.parseInt(request.getParameter("f4"));
 
        // 学生
        Student student = new Student();
        student.setNo(studentNo);
 
        // 科目
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);
 
        // 削除対象作成
        Test test = new Test();
        test.setStudent(student);
        test.setSubject(subject);
        test.setSchool(school);
        test.setNo(no);
 
        // 削除実行
        TestDao testDao = new TestDao();
        testDao.delete(test);
 
        request.getRequestDispatcher("TestRegist.action").forward(request, response);
    }
}
//TestDeleteExecuteAction.java