package dev.jhon0206.springjwt.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.jhon0206.springjwt.security.config.JwtProperties;
import dev.jhon0206.springjwt.security.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final JwtProperties jwtProperties;

  public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService, JwtProperties jwtProperties) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
    this.jwtProperties = jwtProperties;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      final String authHeader = request.getHeader("Authorization");
      final String prefix = jwtProperties.tokenPrefix();
      if (authHeader == null || !authHeader.startsWith(prefix)) {
        filterChain.doFilter(request, response);
        return;
      }

      final String jwt = authHeader.substring(prefix.length()).trim();
      final String userEmail = jwtService.extractUsername(jwt);

      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        if (jwtService.isTokenValid(jwt, userDetails)) {
          if (!jwtService.isAccessToken(jwt)) {
            throw new JwtException("Refresh tokens cannot be used to access API resources");
          }
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write(
          """
              {
                "error": "Unauthorized",
                "message": "%s"
              }
              """
              .formatted(e.getMessage()));
      response.getWriter().flush();
    }
  }

}
