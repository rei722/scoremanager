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
 
public class TestListStudentExecuteAction extends Action {
 
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
 
        HttpSession session = request.getSession();
 
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();
 
        String studentNo = request.getParameter("studentNo");
        SubjectDao subjectDao = new SubjectDao();
        ClassNumDao cNumDao = new ClassNumDao();
        StudentDao studentDao = new StudentDao();
        List<String> classList = cNumDao.filter(school);
        List<Subject> subjectList = subjectDao.filter(school);
        List<Student> allStudentList = studentDao.filter(school, false);
 
        Set<Integer> entYearSet = new TreeSet<>();
        for (Student s : allStudentList) {
            entYearSet.add(s.getEntYear());
        }
 
        Map<String, String> errors = new HashMap<>();
 
        if (studentNo == null || studentNo.isEmpty()) {
 
            errors.put("studentNo", "学生番号を入力してください");
 
            request.setAttribute("errors", errors);
            request.setAttribute("f", "st");
 
            request.getRequestDispatcher("/scoremanager/test_list.jsp")
                   .forward(request, response);
            return;
        }
 
        List<Student> studentList = studentDao.filter(school, false);
        Student student = null;
        for (Student s : studentList) {
        	if (s.getNo().trim().equals(studentNo.trim())) {
                student = s;
                break;
            }
        }
        if (student == null) {
            errors.put("studentNo", "該当する学生が存在しません");
            request.setAttribute("errors", errors);
 
            request.getRequestDispatcher("/scoremanager/test_list.jsp")
                   .forward(request, response);
            return;
        }
 
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> list = dao.filter(student);
 
        if (list == null || list.isEmpty()) {
            errors.put("studentNo", "成績データが存在しません");
        }
 
        request.setAttribute("f", "st");
 
        request.setAttribute("testList", list);
        request.setAttribute("student", student);
 
        request.setAttribute("studentNo", studentNo);
 
        request.setAttribute("class_num_set", classList);
        request.setAttribute("subject_set", subjectList);
        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("errors", errors);
 
        request.getRequestDispatcher("/scoremanager/test_list.jsp")
               .forward(request, response);
    }
}
//TestListStudentExrcuteAction.jsva