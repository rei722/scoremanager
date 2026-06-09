//学生別成績一覧
//成績参照の学生科目情報を指している
//testとsubjectをjoin、あってるかわからん過ぎる。
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao{
	//結合
	private String baseSql = "select sub.name as subject_name, t.subject_cd, t.no, t.point from test t "
	+ "inner join subject sub on t.subject_cd = sub.cd "
	+ "and t.school_cd = sub.school_cd "
	+ "where t.student_no = ? ";
	
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		//リスト初期化
		List<TestListStudent> list = new ArrayList<>();
		try {
			//リザルトセットを全件操作
			while (rSet.next()) {
				//インスタンスを初期化
				TestListStudent testlistStudent = new TestListStudent();
 
				//インスタンスに検索結果をセット
				testlistStudent.setSubjectName(rSet.getString("subject_name"));//科目名
				testlistStudent.setSubjectCd(rSet.getString("subject_cd"));//科目コード
				testlistStudent.setNum(rSet.getInt("no"));
				testlistStudent.setPoint(rSet.getInt("point"));
 
				//リスト追加
				list.add(testlistStudent);
			}
 
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<TestListStudent> filter(Student student)throws Exception{
		 /**
	     * 学校に所属する全科目をリストで取得する
	     */
	    List<TestListStudent> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rSet = null;
	    
	    //並び替え条件
	    String order = "order by t.subject_cd asc, t.no asc";
	  
	    try {
	    	statement = connection.prepareStatement(baseSql+order);
		    statement.setString(1, student.getNo());
		    rSet = statement.executeQuery();
	    	
		    	//リストへの格納
		    	list = postFilter(rSet);
	    } catch (Exception e) {
	    	throw e;
	    } finally {
	    	if (statement !=null) statement.close();
	    	if(connection !=null) connection.close();
	    }
	    return list;
	    }
}
