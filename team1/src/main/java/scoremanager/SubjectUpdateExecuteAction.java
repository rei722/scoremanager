//変更チェック→完了画面
package scoremanager;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

	@Override
		public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		    HttpSession session = request.getSession();
		    //ログイン中の情報取得
		    Teacher teacher = (Teacher) session.getAttribute("user");
		    
		    SubjectDao subjectDao = new SubjectDao();

		    // 入力値の取得
		    String cd = request.getParameter("cd");
		    String name = request.getParameter("name");
//			前後空白削除（	trim=空白によるチェック抜け防止）
		    if(cd !=null) {
		    	cd = cd.trim();
		    }
		    
		    if(name != null) {
		    	name = name.trim();
		    }
		    
		    Map<String, String> errors = new HashMap<>();

		    // 科目名のチェック
		    if (name == null || name.isEmpty()) {
		        errors.put("name", "科目名を入力してください");
		    }
		    
		    
		        if(!errors.isEmpty()) {
		        	Subject subject = new Subject();
		        	
		        	subject.setCd(cd);
		        	subject.setName(name);
		        	
		        	request.setAttribute("subject", subject);
		            request.setAttribute("errors", errors);
		        	
		        	//入力画面に戻す
		        	request.getRequestDispatcher("/scoremanager/subject_update.jsp")
		        		.forward(request, response);
		        	return;
		        }
		        	
		        //科目情報をセット
		        Subject subject = new Subject();
		        subject.setCd(cd);
		        subject.setName(name);
		        subject.setSchool(teacher.getSchool());
		        
		        //保存
		        subjectDao.save(subject);
		        
	        	request.getRequestDispatcher("/scoremanager/subject_update_done.jsp")
	        	.forward(request, response);
	}
}