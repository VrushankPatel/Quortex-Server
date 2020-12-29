package com.quortexservice.quortex.requestfilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quortexservice.quortex.service.UserService;
import com.quortexservice.quortex.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userDetailsService;

	
	private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("Calling doFilterInternal for ["+request.getRequestURI()+"] endpoint");
		if ( !List.of(ConstantUtil.AUTH_IGNORE_ENDPOINT).contains(request.getRequestURI())) {
				//!request.getRequestURI().equalsIgnoreCase("/signup") && !request.getRequestURI().equalsIgnoreCase("/login")) {
			final String authorizationHeader = request.getHeader("Authorization");
			log.info("Authorization header[" + authorizationHeader + "]");
			String username = null;
			String token = null;
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				log.info("Authorization header not found in request");
				configResponse(response);
			}else {
			//if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
				token = authorizationHeader.substring(7);
				username = userDetailsService.extractUsername(token);
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
					if (userDetailsService.validateToken(token, userDetails)) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
						log.info("Authorization successful");
					}
				}
				}catch (Exception e) {
					log.info("Authorization falied ["+e.getMessage() +"]");
					configResponse(response);
				}
			}
			
		}
		filterChain.doFilter(request, response);

	}
	
	private void configResponse(HttpServletResponse response) throws IOException {
		String content = "{\"message\": \""+ConstantUtil.AUTH_FAILED_ERROR_MESSAGE+"\",\"code\": "+ConstantUtil.AUTH_FAILED_ERROR_CODE+"}";
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(content);
		out.flush();
	}

}
