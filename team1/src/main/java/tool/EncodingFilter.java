//リクエストがサーブレットに行く前に処理を挟むため
package tool;
import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns= "*.action")
public class EncodingFilter implements Filter{
		
		//フィルターの初期化
		@Override
		public void init(FilterConfig filterConfig)throws ServletException{
			System.out.println("初期化されました");
		}
		
		@Override
		public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain
				)throws ServletException,IOException{
			//文字コード設定
			request.setCharacterEncoding("UTF-8");
			//次の処理へ渡す。
			chain.doFilter(request, response);
		}
		
		//フィルターの処理
		@Override
		public void destroy() {
			System.out.println("破棄されました");
		}
}
