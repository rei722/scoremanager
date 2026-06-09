//学生情報を扱うBeanクラス、シリアライズを可能にするためにSerializableを実装
package bean;

public class User {
	
	//認証済みフラグ:boolean / 認証済み:true
	private boolean isAuthenticated;
	
	//ゲッター・セッター
	public boolean isAuthenticated() {
		return isAuthenticated;
	}
	
	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
}
