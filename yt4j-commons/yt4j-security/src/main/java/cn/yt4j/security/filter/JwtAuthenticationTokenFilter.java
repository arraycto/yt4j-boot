package cn.yt4j.security.filter;

import cn.yt4j.security.property.JwtAuthFilterProperty;
import cn.yt4j.security.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shichenyang
 */
@AllArgsConstructor
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;

	private final JwtAuthFilterProperty jwtAuthFilterProperty;

	private final JwtUtil jwtUtil;

	/**
	 * 过滤器逻辑
	 * @param request .
	 * @param response .
	 * @param chain .
	 * @throws ServletException .
	 * @throws IOException ,
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(this.jwtAuthFilterProperty.getHeader());
		String tokenHead = this.jwtAuthFilterProperty.getTokenHead();
		if (authHeader != null && authHeader.startsWith(tokenHead)) {
			String authToken = authHeader.substring(tokenHead.length());
			if (jwtUtil.validateToken(authToken)) {
				String subject = jwtUtil.getSubjectFromToken(authToken);
				UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}

}
