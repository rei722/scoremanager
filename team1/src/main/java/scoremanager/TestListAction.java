package scoremanager;
 
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import tool.Action;
 
public class TestListAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
 
        ClassNumDao cNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        StudentDao studentDao = new StudentDao();
 
        // クラス一覧
        List<String> classList = cNumDao.filter(teacher.getSchool());
        // 科目一覧
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
        // 入学年度一覧
        List<Student> studentList = studentDao.filter(teacher.getSchool(), false);
        Set<Integer> entYearSet = new TreeSet<>();
        for (Student student : studentList) {
            entYearSet.add(student.getEntYear());
        }
 
        request.setAttribute("class_num_set", classList);
        request.setAttribute("subject_set", subjectList);
        request.setAttribute("ent_year_set", entYearSet);
 
        request.getRequestDispatcher("/scoremanager/test_list.jsp")
                .forward(request, response);
    }
}
//TestListAction.jsva