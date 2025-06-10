package com.hostpet.hostpet.security;

import com.hostpet.hostpet.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Marca essa classe como um componente do Spring, permitindo que ela seja injetada em outros componentes
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService; // Serviço responsável por validar e recuperar informações do token JWT
    @Autowired
    UserRepository userRepository; // Repositório de usuários para buscar dados do usuário no banco de dados


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recupera o token JWT da requisição
        var token = this.recoverToken(request);

         // Se o token for encontrado, valida e recupera o usuário associado a ele
        if(token != null){
            var subject = tokenService.validateToken(token); // Valida o token e extrai o "subject" (usuário)
            UserDetails user = userRepository.findByEmail(subject); // Busca o usuário no banco de dados pelo email

             // Cria um objeto de autenticação com as informações do usuário
            var authentication = new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
            // Define o usuário autenticado no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

         // Continua o fluxo da requisição, passando o controle para o próximo filtro na cadeia
        filterChain.doFilter(request,response);
    }

    
    // Método para extrair o token JWT do cabeçalho Authorization da requisição
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization"); // Recupera o cabeçalho "Authorization"
        if(authHeader == null) return  null; // Se o cabeçalho não estiver presente, retorna null
        return  authHeader.replace("Bearer ", "");  // Remove a parte "Bearer " do token e retorna o valor restante
    }
}
