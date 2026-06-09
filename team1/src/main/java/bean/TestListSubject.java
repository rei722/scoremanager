//クラス別成績一覧
package bean;

import java.util.Map;

public class TestListSubject {

	private int entYear ;//入学年度
	private String studentNo;//学生番号
	private String studentName;//科目
	private String classNum;//クラス
	private Map<Integer,Integer> points;//回数
	
	public int getEntYear() {
		return entYear;
	}
	
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	
	public String getStudentNo() {
		return studentNo;
	}
	
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public String getClassNum() {
		return classNum;
	}
	
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	
	public Map<Integer,Integer> getPoints(){
		return points;
	}
	
	public void setPoints(Map<Integer,Integer> points) {
		this.points = points;
	}
	
	public String getPoint(int key) {
		 //Mapからkeyに対応する点数を取得
		Integer point = points.get(key);
		//点数が存在しない・存在する→文字列
		if (point ==null ) {
			return "-";
		}
		//文字列に切り替え
		return String .valueOf(point);
		
	}
	
	public void putPoint(int key,int value) {
		this.points.put(key, value);
	}
}
