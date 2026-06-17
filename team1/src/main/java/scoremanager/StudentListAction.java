package scoremanager;

import java.util.ArrayList;
import java.util.HashMap;
// Listを使うために必要
import java.util.List;
import java.util.Map;

// Servlet関連
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
// DAO
import dao.StudentDao;
// 親クラス
import tool.Action;

// 学生一覧表示処理
public class StudentListAction extends Action {
	@Override
	public void execute(
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		HttpSession session = request.getSession();//セッション
 
		//ログイン情報取得
		 
		Teacher teacher =(Teacher)session.getAttribute("user");
		
		String entYearStr = "";//入力された入学年度
		String classNum = "";//入力されたクラス番号
		String isAttendStr = "";//入力された在学フラグ
		int entYear = 0;//入学年度
		boolean isAttend = false;//在学フラグ
		
		List<Student> students = null;//学生リスト
		

 
		//学校情報取得
		ClassNumDao cNumDao = new ClassNumDao();
		StudentDao sDao = new StudentDao();
		
		Map<String,String> errors = new HashMap<>();//エラーメッセージ
		
		entYearStr = request.getParameter("f1");
		classNum = request.getParameter("f2");
		isAttendStr = request.getParameter("f3");
		 
		if (entYearStr != null && !entYearStr.equals("0") && !entYearStr.equals("")) {
			//数値変換
			entYear = Integer.parseInt(entYearStr);
		}
		
		
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = 2026; i <= 2036; i++) {
			entYearSet.add(i);
		}
		
		//在校中
		if (isAttendStr !=null) {
			isAttend = true;
		}
		
		//クラス番号一覧取得
		List<String> list = cNumDao.filter(teacher.getSchool());
		
		//クラス+在校中
		if (entYear ==0 && classNum != null && !classNum.equals("0") && isAttend) {
			
			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
			
			request.setAttribute("errors", errors);
			//一覧に戻す
			students = sDao.filter(teacher.getSchool(),false);
		}
			
		//クラスのみ
		 else if(entYear ==0 && classNum != null && !classNum.equals("0") ) {
			
			 errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
				
				request.setAttribute("errors", errors);
				//一覧に戻す
				students = sDao.filter(teacher.getSchool(),false);
		 }
		
		else {
			//入学年度+クラス+在学中
			if(entYear != 0 && !classNum.equals("0") && isAttend) {
			
				//入学年度+在校中
				students = sDao.filter(teacher.getSchool(),entYear,classNum,isAttend);
			}
			
			//入学年度+クラス
		else if(entYear != 0 && classNum !=null && !classNum.equals("0")) {
			
			//入学年度のみ
			students = sDao.filter(teacher.getSchool(), entYear,classNum,false);
		}
		//入学年度+在学中	
		 else if(entYear !=0 && isAttend){
				
			students = sDao.filter(teacher.getSchool(), entYear,isAttend);
		 }
			//入学年度のみ
		else if(entYear !=0){
			students = sDao.filter(teacher.getSchool(),entYear,false);
		}
			//在学中のみ
		else if(isAttend) {
			students = sDao.filter(teacher.getSchool(), isAttend);
		}
		
		//全件
		else {
			students = sDao.filter(teacher.getSchool(),false);
			}
		}
		
		request.setAttribute("f1",entYear);
		request.setAttribute("f2",classNum);
		request.setAttribute("f3",isAttendStr);
		
		request.setAttribute("students",students);
		request.setAttribute("class_num_set",list);
		request.setAttribute("ent_year_set",entYearSet);
		
		request.getRequestDispatcher("/scoremanager/student_list.jsp").forward(request,response);
	}
}