package scoremanager;
 
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;
 
public class StudentCreateAction extends Action {
 
	@Override
	public void execute(
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
        Teacher teacher =
                (Teacher) request.getSession()
                .getAttribute("user");

        ClassNumDao cNumDao = new ClassNumDao();

        List<String> list =
                cNumDao.filter(teacher.getSchool());

        request.setAttribute("class_num_set", list);
        
		request.getRequestDispatcher(
				"/scoremanager/student_create.jsp")
				.forward(request, response);
	}
}