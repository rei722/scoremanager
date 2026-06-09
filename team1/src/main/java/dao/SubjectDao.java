//科目テーブルに対して操作を行うDAOクラス
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    /**
     * 指定したコードと学校に紐づく科目を1件取得する
     */
    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
        	//科目コードと学校コード条件検索
            statement = connection.prepareStatement("select * from subject where cd=? and school_cd=?");
            statement.setString(1, cd);
            statement.setString(2, school.getCd());
            ResultSet rSet = statement.executeQuery();
            
            //結果が存在するマッピング
            if (rSet.next()) {
                subject = new Subject();
                subject.setCd(rSet.getString("cd"));
                subject.setName(rSet.getString("name"));
                subject.setSchool(school);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subject;
    }

    /**
     * 学校に所属する全科目をリストで取得する
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
        	//学校コードで絞り込み科目コード順並び替え
            statement = connection.prepareStatement("select * from subject where school_cd=? order by cd asc");
            statement.setString(1, school.getCd());
            rSet = statement.executeQuery();
            //リストに追加
            while (rSet.next()) {
                Subject subject = new Subject();
                subject.setCd(rSet.getString("cd"));
                subject.setName(rSet.getString("name"));
                subject.setSchool(school);
                list.add(subject);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }

    /**
     * 科目を保存（存在すれば更新、なければ新規）
     */
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            // すでに存在するか確認
            Subject old = get(subject.getCd(), subject.getSchool());

            if (old == null) {
                // 新規作成 (INSERT)
                statement = connection.prepareStatement(
                    "insert into subject(cd, name, school_cd) values(?, ?, ?)");
                statement.setString(1, subject.getCd());
                statement.setString(2, subject.getName());
                statement.setString(3, subject.getSchool().getCd());
            } else {
                // 更新 (UPDATE)
                statement = connection.prepareStatement(
                    "update subject set name=? where cd=? and school_cd=?");
                statement.setString(1, subject.getName());
                statement.setString(2, subject.getCd());
                statement.setString(3, subject.getSchool().getCd());
            }
            count = statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return count > 0;
    }

    /**
     * 科目を削除
     */
    public boolean delete(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
        	//一致するレコードを削除
            statement = connection.prepareStatement("delete from subject where cd=? and school_cd=?");
            statement.setString(1, subject.getCd());
            statement.setString(2, subject.getSchool().getCd());
            count = statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return count > 0;
    }
}