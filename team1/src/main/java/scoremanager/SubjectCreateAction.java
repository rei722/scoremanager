//登録画面に進む
package scoremanager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tool.Action;

public class SubjectCreateAction extends Action {
	@Override
	public void execute(HttpServletRequest request,HttpServletResponse response
			)throws Exception{
		

		//登録画面のJSP
		request.getRequestDispatcher("subject_create.jsp").forward(request, response);
		
	}
}