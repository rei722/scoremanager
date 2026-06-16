package scoremanager;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();

        Teacher teacher =
                (Teacher) session.getAttribute("user");

        // 入力値取得
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String classNum = req.getParameter("classNum");
        String entYearStr = req.getParameter("entYear");

        boolean isAttend =
                req.getParameter("is_attend") != null;

        Map<String, String> errors =
                new HashMap<>();

        // 氏名チェック
        if (name == null || name.trim().isEmpty()) {

            errors.put("name",
                    "氏名を入力してください");

            req.setAttribute("errors", errors);

            req.getRequestDispatcher(
                    "/scoremanager/student_update.jsp")
                    .forward(req, res);

            return;
        }

        int entYear =
                Integer.parseInt(entYearStr);

        Student student =
                new Student();

        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(
                teacher.getSchool());

        StudentDao studentDao =
                new StudentDao();

        studentDao.save(student);

        req.getRequestDispatcher(
                "/scoremanager/student_update_done.jsp")
                .forward(req, res);
    }
}