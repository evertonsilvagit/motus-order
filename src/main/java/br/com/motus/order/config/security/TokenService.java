package br.com.motus.order.config.security;

import br.com.motus.order.model.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(User user){
    System.out.println(secret);
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer("Order API")
          .withSubject(user.getLogin())
          .withExpiresAt(dataExpiracao())
          .sign(algorithm);
    } catch (JWTCreationException exception){
      throw new RuntimeException("Error generating jwt token");
    }
  }

  public String getSubject(String tokenJWT){
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("Order API")
          .build()
          .verify(tokenJWT)
          .getSubject();

    } catch (JWTVerificationException exception){
      throw new RuntimeException("Token JWT inválido ou expirado!");
    }
  }

  private Instant dataExpiracao() {
    return LocalDateTime
        .now()
        .plusHours(2)
        .toInstant(ZoneOffset.of("-03:00"));
  }

}
