package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {
	// 在籍中の学生を取得するための基本SQL
    private String baseSql = "SELECT * FROM student WHERE school_cd = ? AND is_attend = true ";
    
    // 指定された学生・科目・回数のテスト情報を取得
    public Test get(Student student, Subject subject, School school, int no) {

    	//テストインスタンス初期化
        Test test = null;
        
        // テスト情報検索用SQL
        String sql = "SELECT * FROM test WHERE school_cd = ?"
                   + " AND student_no = ?"
                   + " AND subject_cd = ?"
                   + " AND no = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

        	// SQLパラメータ設定
            ps.setString(1, school.getCd());
            ps.setString(2, student.getNo());
            ps.setString(3, subject.getCd());
            ps.setInt(4, no);

         // SQL実行
            ResultSet rs = ps.executeQuery();

         // 検索結果が存在する場合
            if (rs.next()) {
            	// テスト情報を設定
                test = new Test();
                test.setNo(rs.getInt("no"));
                test.setPoint(rs.getInt("point"));
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }

 // 学生情報の検索結果をTestリストへ変換
    private List<Test> postFilter(ResultSet rs, School school) throws Exception {

    	 // テストリスト初期化
        List<Test> list = new ArrayList<>();

        while (rs.next()) {
        	// 学生情報を設定
            Student student = new Student();
            student.setNo(rs.getString("no"));
            student.setName(rs.getString("name"));
            student.setEntYear(rs.getInt("ent_year"));
            student.setClassNum(rs.getString("class_num"));
            student.setSchool(school);

            // テスト情報を生成
            Test test = new Test();
            
            // 学生情報と学校情報を設定
            test.setStudent(student);
            test.setSchool(school);

            // リストへ追加
            list.add(test);
        }

        return list;
    }
    
 // 条件に一致する学生とテスト情報を取得
    public List<Test> filter(int entYear, String classNum, Subject subject, int no, School school) {

    	// テストリスト初期化
        List<Test> list = new ArrayList<>();
        
     // 学年・クラスで学生を検索
        String sql = baseSql
                   + " AND ent_year = ?"
                   + " AND class_num = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

        	// SQLに検索条件をバインド
            ps.setString(1, school.getCd());
            ps.setInt(2, entYear);
            ps.setString(3, classNum);

         // SQL実行
            ResultSet rs = ps.executeQuery();
            list = postFilter(rs, school);
            
         // 各学生のテスト点数を設定
            for (Test test : list) {
            	// 科目と回数を設定
                test.setSubject(subject);
                test.setNo(no);

                // 登録済みのテスト情報を取得
                Test saved = get(test.getStudent(), subject, school, no);
                
                if (saved != null) {
                	// テスト情報が存在する場合は点数を設定
                    test.setPoint(saved.getPoint());
                } else {
                	// テスト情報が存在しない場合は未登録として設定
                    test.setPoint(-1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
 // テスト情報を一括登録・更新
    public boolean save(List<Test> list) {

        try (Connection con = getConnection()) {
        	// トランザクション開始
            con.setAutoCommit(false);

         // テスト情報を順番に登録・更新
            for (Test test : list) {
            	// 登録・更新失敗時はロールバック
                if (!save(test, con)) {
                    con.rollback();
                    return false;
                }
            }

         // 正常終了時はコミット
            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

 // テスト情報を登録または更新
    private boolean save(Test test, Connection con) {

    	// 既存データがあれば更新、なければ登録するSQL
        String sql =
        		"MERGE INTO test (student_no, subject_cd, no, point, school_cd, class_num) "
        			+ "KEY(student_no, subject_cd, no, school_cd) "
        			+ "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

        	 // SQLに登録情報をバインド
            ps.setString(1, test.getStudent().getNo());
            ps.setString(2, test.getSubject().getCd());
            ps.setInt(3, test.getNo());
            ps.setInt(4, test.getPoint());
            ps.setString(5, test.getSchool().getCd());
            ps.setString(6, test.getStudent().getClassNum());

         // SQL実行
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // テスト情報を削除
    public boolean delete(Test test) {

    	// テスト情報削除用SQL
        String sql =
            "DELETE FROM test WHERE school_cd=? "
          + "AND student_no=? "
          + "AND subject_cd=? "
          + "AND no=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

        	// SQLに削除対象情報をバインド
            ps.setString(1, test.getSchool().getCd());
            ps.setString(2, test.getStudent().getNo());
            ps.setString(3, test.getSubject().getCd());
            ps.setInt(4, test.getNo());

         // SQL実行
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
//TestDao.java
