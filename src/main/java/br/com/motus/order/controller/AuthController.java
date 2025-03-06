package br.com.motus.order.controller;

import br.com.motus.order.config.security.TokenService;
import br.com.motus.order.controller.dto.AuthDTO;
import br.com.motus.order.controller.dto.TokenJwtDTO;
import br.com.motus.order.model.user.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private AuthenticationManager manager;

  private TokenService tokenService;

  public AuthController(AuthenticationManager manager, TokenService tokenService){
    this.manager = manager;
    this.tokenService = tokenService;
  }

  @PostMapping
  public ResponseEntity auth(@RequestBody @Valid AuthDTO authDTO){
    var authenticationToken = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
    var authentication = manager.authenticate(authenticationToken);

    var token = tokenService.generateToken((User) authentication.getPrincipal());
    return ResponseEntity.ok(new TokenJwtDTO(token));
  }

}
