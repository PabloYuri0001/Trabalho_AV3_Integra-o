package com.hostpet.hostpet.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.hostpet.hostpet.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.securit.token.secret}") // Injeta o segredo da chave JWT a partir do arquivo de configuração (application.properties ou application.yml)

    private  String secret;

    // Método para gerar um token JWT para um usuário
    public String genarateToken(User user){
        try {
            // Define o algoritmo de assinatura do token com a chave secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Cria o token JWT
            String token = JWT.create()
                    .withIssuer("auth-api") // Define o emissor do token
                    .withSubject(user.getUsername()) // Define o "subject" como o email(que é usado para validar o usurio) do usuário
                    .withExpiresAt(genarateExpirationDate())// Define a data de expiração do token
                    .sign(algorithm);// Assina o token com o algoritmo HMAC256 e a chave secreta
            return token; // Retorna o token gerado
        }catch (JWTCreationException expc){ // Lança uma exceção em caso de erro ao gerar o token
            throw new RuntimeException("Erro ao gerar o token",expc);
        }
    }

    // Método para validar um token JWT e retornar o "subject" (usuário)
    public String validateToken(String token){
        try {
            // Define o algoritmo de verificação do token com a chave secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Verifica o token e retorna o "subject" (nome de usuário) após validação
            return JWT.require(algorithm)
                    .withIssuer("auth-api") // Verifica se o emissor do token é o esperado
                    .build() // Cria a configuração de verificação
                    .verify(token) // Verifica o token
                    .getSubject(); // Retorna o "subject" (nome de usuário) associado ao token

        }catch (JWTCreationException expc){
            return ""; // Retorna uma string vazia caso ocorra algum erro na validação
        }
    }

    // Método para gerar a data de expiração do token (2 horas a partir do momento atual)
    private Instant genarateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
