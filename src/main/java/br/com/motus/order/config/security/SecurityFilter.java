package br.com.motus.order.config.security;

import br.com.motus.order.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserRepository userRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    var tokenJWT = recuperarToken(request);

    if(tokenJWT != null) {
      var subject = tokenService.getSubject(tokenJWT);
      var user = userRepository.findByLogin(subject);

      var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private String recuperarToken(HttpServletRequest request) {
    var authorizationHeader = request.getHeader("Authorization");
    if(authorizationHeader != null){
      return authorizationHeader.replace("Bearer ", "");
    }
    return null;
  }
}
