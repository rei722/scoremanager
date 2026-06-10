package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Teacher;

public class TeacherDao extends Dao {
	//ログイン情報をもとに教員情報を取得する
    public Teacher login(String id, String password)
        throws Exception {
    	 // 教員インスタンス初期化
        Teacher teacher = null;

     // DBへのコネクション確立
        Connection con = getConnection();

     // プリペアードステートメント
        PreparedStatement st;
        
     // ログイン認証用SQLをセット
        st = con.prepareStatement("select * from teacher where id=? and password=?");
        
     // ログインIDとパスワードをバインド
        st.setString(1, id);
        st.setString(2, password);
        
     // SQL実行
        ResultSet rs = st.executeQuery();

        // 検索結果が存在する場合
        while (rs.next()) {
         // 教員情報を生成
            teacher = new Teacher();
            
         // 教員情報を設定
            teacher.setId(rs.getString("id"));
            teacher.setPassword(rs.getString("password"));
            teacher.setName(rs.getString("name"));
            
         // 学校情報を生成
            School school = new School();
         // 学校コードを設定
            school.setCd(rs.getString("school_cd"));
         // 教員に学校情報を設定
            teacher.setSchool(school);
        }
     // プリペアードステートメントを閉じる
        st.close();
     // コネクションを閉じる
        con.close();
        
        return teacher;
    }
}