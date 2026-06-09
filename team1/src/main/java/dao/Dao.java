package dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Dao {
    // 🔹 データソース（DataSource）を管理する static 変数
    // → 一度取得したデータソースを再利用することで、効率的に接続を管理
    static DataSource ds;

    /**
     * 🔗 データベース接続を取得するメソッド
     *
     * 初回の呼び出し時に `DataSource` を取得し、それを使ってデータベース接続を返します。
     * 2回目以降は、すでに取得済みの `DataSource` を利用して接続を確立します。
     *
     * @return データベースの `Connection` オブジェクト
     * @throws Exception データソースの取得や接続確立時にエラーが発生した場合
     */
    public Connection getConnection() throws Exception {
        // ① 初回の呼び出し時（`ds` が `null` の場合）のみデータソースを取得
        if (ds == null) {
            // ② JNDI (Java Naming and Directory Interface) を利用して `InitialContext` を作成
            InitialContext ic = new InitialContext();

            // ③ JNDI からデータソース (`jdbc/book`) を取得し、`ds` に保存
            ds = (DataSource) ic.lookup("java:comp/env/jdbc/score");
        }

        // ④ `DataSource` を利用して `Connection` を取得し、呼び出し元に返す
        return ds.getConnection();
    }
}
