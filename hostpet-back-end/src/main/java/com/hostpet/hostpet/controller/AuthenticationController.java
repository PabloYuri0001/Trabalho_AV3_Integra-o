package com.hostpet.hostpet.controller;


import com.hostpet.hostpet.dtos.AuthenticationDTO;
import com.hostpet.hostpet.dtos.LoginResponseDTO;
import com.hostpet.hostpet.dtos.LoginResponseWithUserDTO;
import com.hostpet.hostpet.dtos.RegisterDTO;
import com.hostpet.hostpet.entity.User;
import com.hostpet.hostpet.exceptions.CustomException;
import com.hostpet.hostpet.repository.UserRepository;
import com.hostpet.hostpet.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;  // Gerencia a autenticação do usuário

    @Autowired
    private UserRepository userRepository; // Repositório para interagir com a tabela de usuários

    @Autowired
    private TokenService tokenService; // Serviço para gerar o token JWT


   // Endpoint para login, recebe credenciais, autentica e retorna um token JWT
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        Optional<UserDetails> optionalUser = Optional.ofNullable(userRepository.findByEmail(data.email()));
        if(optionalUser.isEmpty()){
            throw new CustomException("E-mail não registrado", HttpStatus.BAD_REQUEST);
        }

        // Cria um token de autenticação com e-mail e senha fornecidos
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);  // Autentica as credenciais

        // Gera um token JWT para o usuário autenticado
        var token = tokenService.genarateToken((User) auth.getPrincipal());

        // Obtém o usuário autenticado
        User authenticatedUser = (User) auth.getPrincipal();

        // Cria a resposta com o token, id e email
        LoginResponseWithUserDTO response = new LoginResponseWithUserDTO(token, authenticatedUser.getId(), authenticatedUser.getEmail());

        // Retorna a resposta com token, id e email
        return ResponseEntity.ok(response);
    }
    // Endpoint para registro de um novo usuário, recebe dados e salva no banco
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data)  {

        // Verifica se o e-mail já está registrado
        if (this.userRepository.findByEmail(data.email()) != null) {
            throw new CustomException("E-mail já registrado", HttpStatus.BAD_REQUEST);
        }

        // Criptografa a senha usando BCrypt
        String encryptadPassword = new BCryptPasswordEncoder().encode(data.password());

        // Cria um novo objeto de usuário com e-mail e senha criptografada
        User newUser = new User(data.email(),encryptadPassword);

        // Salva o novo usuário no banco de dados
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build(); // Retorna resposta de sucesso (200 OK)

    }

}
