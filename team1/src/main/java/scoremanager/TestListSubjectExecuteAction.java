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
 
public class TestListSubjectExecuteAction extends Action {
 
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
 
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();
 
        String f1 = request.getParameter("f1");
        String f2 = request.getParameter("f2");
        String f3 = request.getParameter("f3");
 
        Map<String, String> errors = new HashMap<>();
 
        SubjectDao subjectDao = new SubjectDao();
        ClassNumDao cNumDao = new ClassNumDao();
        StudentDao studentDao = new StudentDao();
 
        if (f1 == null || f1.isEmpty()
                || f2 == null || f2.isEmpty()
                || f3 == null || f3.isEmpty()) {
 
            errors.put("f1", "入学年度とクラスと科目を選択してください");
 
            List<String> classList = cNumDao.filter(school);
            List<Subject> subjectList = subjectDao.filter(school);
            List<Student> studentList = studentDao.filter(school, false);
 
            Set<Integer> entYearSet = new TreeSet<>();
            for (Student s : studentList) {
                entYearSet.add(s.getEntYear());
            }
 
            request.setAttribute("class_num_set", classList);
            request.setAttribute("subject_set", subjectList);
            request.setAttribute("ent_year_set", entYearSet);
            request.setAttribute("errors", errors);
 
            request.getRequestDispatcher("/scoremanager/test_list.jsp")
                    .forward(request, response);
            return;
        }
 
        int entYear = Integer.parseInt(f1);
        String classNum = f2;
 
        Subject subject = subjectDao.get(f3, school);
 
        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> list = dao.filter(entYear, classNum, subject, school);
 
        if (list == null) {
            list = new ArrayList<>();
        }
 
        int maxNo = 2;
        if (list != null) {
            for (TestListSubject row : list) {
                for (int no : row.getPoints().keySet()) {
                    if (no > maxNo) maxNo = no;
                }
            }
        }
 
        List<String> classList = cNumDao.filter(school);
        List<Subject> subjectList = subjectDao.filter(school);
        List<Student> studentList = studentDao.filter(school, false);
 
        Set<Integer> entYearSet = new TreeSet<>();
        for (Student s : studentList) {
            entYearSet.add(s.getEntYear());
        }
 
        request.setAttribute("f", "sj");
        request.setAttribute("testList", list);
        request.setAttribute("subject", subject);
        request.setAttribute("f1", f1);
        request.setAttribute("f2", f2);
        request.setAttribute("f3", f3);
        request.setAttribute("maxNo", maxNo);
        request.setAttribute("class_num_set", classList);
        request.setAttribute("subject_set", subjectList);
        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("errors", errors);
 
        request.getRequestDispatcher("/scoremanager/test_list.jsp")
                .forward(request, response);
    }
}