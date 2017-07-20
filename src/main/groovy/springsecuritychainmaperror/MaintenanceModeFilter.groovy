package springsecuritychainmaperror

import groovy.util.logging.Slf4j
import org.springframework.web.filter.GenericFilterBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by franciscojosegarciarico on 14/07/2017.
 */
@Slf4j
class MaintenanceModeFilter extends GenericFilterBean {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)  throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req
		HttpServletResponse response = (HttpServletResponse) res

		println("********************* start MaintenanceModeFilter ******************")
		println( "URI ${request.requestURI}")
		println( "********************* end MaintenanceModeFilter ******************")
		chain.doFilter(request, response)
	}
}
