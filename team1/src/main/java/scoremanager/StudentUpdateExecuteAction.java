package scoremanager;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
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

        // 氏名チェック
        if (name == null || name.trim().isEmpty()) {

            req.setAttribute("nameError","氏名を入力してください");
            
            Student errorStudent = new Student();
            errorStudent.setNo(no);
            errorStudent.setName(name);
            errorStudent.setClassNum(classNum);
            
            if (entYearStr != null && !entYearStr.isEmpty()) {
                try {
                    errorStudent.setEntYear(Integer.parseInt(entYearStr));
                } catch (NumberFormatException e) {
                    //失敗時は初期値のまま
                }
            }
            
            req.setAttribute("student", errorStudent);
            
            //クラス一覧再取得
            ClassNumDao cdao = new ClassNumDao();
            List<String> classList = cdao.filter(teacher.getSchool());
            
            req.setAttribute("class_num_set", classList);
            
            //チェックボックス
            req.setAttribute("is_attend", isAttend);
            
            
            req.getRequestDispatcher(
                    "/scoremanager/student_update.jsp")
                    .forward(req, res);

            return;
        }

        StudentDao sDao = new StudentDao();
        Student student = sDao.get(no);
        
        student.setName(name);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(teacher.getSchool());


        sDao.save(student);

        req.getRequestDispatcher(
                "/scoremanager/student_update_done.jsp")
                .forward(req, res);
    }
}