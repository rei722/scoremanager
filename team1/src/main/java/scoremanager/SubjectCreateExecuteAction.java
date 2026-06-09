//登録画面で受け取った値をチェック
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

public class SubjectCreateExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    HttpSession session = request.getSession();
	    //ログイン中の情報取得
	    Teacher teacher = (Teacher) session.getAttribute("user");
	    
	    SubjectDao subjectDao = new SubjectDao();

	    // 入力値取得
	    String cd = request.getParameter("cd");
	    String name = request.getParameter("name");
	    //	前後空白削除（	trim=空白によるチェック抜け防止）
	    if(cd !=null) {
	    	cd = cd.trim();
	    }
	    
	    if(name != null) {
	    	name = name.trim();
	    }
	    
	    Map<String, String> errors = new HashMap<>();

	    // 2. バリデーション（入力チェック）
	    // 科目コードのチェック
	    if (cd == null || cd.isEmpty()) {
	        errors.put("cd", "科目コードを入力してください");
	    } else if (cd.length() != 3) { 
	        // 必要に応じて桁数チェック（例として3桁。設定に合わせて変更・削除してください）
	        errors.put("cd", "科目コードは3文字で入力してください");
	        
	    }

	    // 科目名のチェック
	    if (name == null || name.isEmpty()) {
	        errors.put("name", "科目名を入力してください");
	        
	    }

	    // 重複チェック（既に同じコードの科目が登録されていないか確認）
	    if (!errors.containsKey("cd")) {
	        Subject existing = subjectDao.get(cd, teacher.getSchool());
	        if (existing != null) {
	            errors.put("cd", "この科目コードは既に登録されています");
	   
	        }
//	        //DBチェック
//	        Subject subject = subjectDao.get(cd, teacher.getSchool());
//	        if (subject == null) {
//	            errors.put("cd", "科目が存在していません");
//	        }
	    }
	        
	        if (!errors.isEmpty()) {
	        	
	        	request.setAttribute("errors", errors);
	        	request.setAttribute("cd", cd);
	        	request.setAttribute("name", name);
	        	
	        	// 入力画面に戻す
	        	request.getRequestDispatcher("/scoremanager/subject_create.jsp")
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
	        
        	request.getRequestDispatcher("/scoremanager/subject_create_done.jsp")
        	.forward(request, response);
	    
	}

}
