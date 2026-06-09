//成績参照の科目情報
package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;
public class TestListSubjectDao extends Dao{
	private List<TestListSubject>postFilter(ResultSet rSet)throws Exception{
		//リスト初期化
		List<TestListSubject> list = new ArrayList<>();
		Map<String,TestListSubject>studentMap = new HashMap<>();
		try {
			//リザルトセット全件操作
			while(rSet.next()) {
				String studentNo = rSet.getString("student_no");
				//学生のインスタンスがあるか確認
				TestListSubject testListSubject = studentMap.get(studentNo);
				//リストになければ、新しくインスタンスを設定
				if(testListSubject == null) {
					testListSubject = new TestListSubject();
					testListSubject.setEntYear(rSet.getInt("ent_year"));
					testListSubject.setClassNum(rSet.getString("class_num"));
					testListSubject.setStudentNo(studentNo);
					testListSubject.setStudentName(rSet.getString("student_name"));
					//点数を初期化
					Map<Integer,Integer> points = new HashMap<>();
					testListSubject.setPoints(points);
					//リストに追加
					studentMap.put(studentNo,testListSubject);
					list.add(testListSubject);
				}
				//DBのnoとpointを取得
				int no = rSet.getInt("no");
				int point = rSet.getInt("point");
				//書かれてるor　nullでなければMapに返却
				testListSubject.getPoints().put(no,point);
			}
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
			return list;
		}
		//条件指定し、一覧を取得
		public List<TestListSubject> filter(int entYear,String classNum,Subject subject,School school)throws Exception{
			List<TestListSubject> list = new ArrayList<>();
			Connection connection = getConnection();
			PreparedStatement statement= null;
			ResultSet rSet = null; 
			//s.学生テーブルから
			//t.テストテーブルから
			String sql = "SELECT s.ent_year, s.class_num, s.no AS student_no, s.name AS student_name, t.no, t.point "
			           + "FROM student s "
			           + "LEFT JOIN test t ON t.student_no = s.no "
			           + "AND t.subject_cd = ? "
			           + "WHERE s.ent_year = ? AND s.class_num = ? AND s.school_cd = ? "
			           + "AND s.is_attend = true "
			           + "ORDER BY s.no ASC, t.no ASC";
			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, subject.getCd()); // 科目コード
				statement.setInt(2, entYear);
				statement.setString(3, classNum);
				statement.setString(4, school.getCd());
				rSet = statement.executeQuery();
				list = postFilter(rSet);
			}catch(SQLException e){
				e.printStackTrace();
				throw e;
			}finally {
				if(rSet !=null)rSet.close();
				if(statement !=null)statement.close();
				if(connection !=null)connection.close();
			}
			return list;
		}
}
//TestListSubjectDao.java