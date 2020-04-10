/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.ticketapp.security;

import com.devs.ticketapp.dao.UserModel;
import com.devs.ticketapp.dto.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    
    private UserModel model= new UserModel();
    
    private String respuesta="";

    public LoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        

        /*
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        //InputStream body = req.getInputStream();
        /*
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
        */
        
        System.err.println("Entro a la url");
        
        
        StringBuffer jb = new StringBuffer();

        String line = null;
        
        BufferedReader reader = req.getReader();

        while ((line = reader.readLine()) != null)
          jb.append(line);
        
        System.err.println("El valor obtenido por el header es: " + jb.toString());
        
        // obtenemos el body de la peticion que asumimos viene en formato JSON

        // Asumimos que el body tendrá el siguiente JSON  {"username":"werbServiceUser", "password":"123"}
        // Realizamos un mapeo a nuestra clase User para tener ahi los datos
        ObjectMapper mapper = new ObjectMapper();
        

        
        Usuario user =  mapper.readValue(jb.toString(), Usuario.class);
        
        System.out.println("El valor recibido en el body es: ");
        System.out.println(mapper.writeValueAsString(user));
        String jsonUser=model.loginUsuario( "["+ mapper.writeValueAsString(user) + "]");
        if(jsonUser.equalsIgnoreCase("false")){
            user.setUsername("fail");
            user.setPassword("123");
            System.err.println("cayo en error de usuario invalido");
        }
        else if(jsonUser.contains("true")){
            user.setUsername("userWebservice");
            user.setPassword("1234");
            System.err.println("cayo en valido de usuario");
            respuesta = jsonUser.replace("true", " ").trim();
        }
        else{
            user.setUsername("fail");
            user.setPassword("123");
            System.err.println("cayo en error de usuario invalido");
        }

        // Finalmente autenticamos
        // Spring comparará el user/password recibidos
        // contra el que definimos en la clase SecurityConfig
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        // Si la autenticacion fue exitosa, agregamos el token a la respuesta
        //res.addHeader("Access-Control-Allow-Origin", "*");
        //res.addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
        JwtUtil.addAuthentication(res, auth.getName());
        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write(respuesta);
        res.getWriter().flush();
        res.getWriter().close();
    }
}
