/*
 * Copyright 2016 notrevequadrosc@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package filtros;

import controle.OperacaoPerfil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Perfil;

/**
 * Classe que realiza o filtro da navegação por cookies no sistema, fazendo o
 * redirecionamento quando necessário para um perfil que não está autenticado
 * no sistema.
 * @author notrevequadrosc@gmail.com
 */
@WebFilter(filterName = "LoginFiltro", urlPatterns = {"*.xhtml"})
public class LoginFiltro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        Perfil cookie = null;
        
        boolean redirecionar = false;
        if(!req.getRequestURI().endsWith("/index.xhtml") 
                && !req.getRequestURI().endsWith("/emailpagina.xhtml")) {
            
            HttpSession sessao = req.getSession(false);
            if(sessao == null) {
                redirecionar = true;
            } 
            else {

                cookie = (Perfil)sessao.getAttribute("Perfil");
         
                if(cookie == null){
                    redirecionar = true;
                }
                
            }
            
            if(req.getRequestURI().endsWith("/fadministrativa.xhtml") 
                    && sessao != null 
                    && cookie != null){
                
                if(!(cookie.getTipo().equals("ADMINISTRADOR"))){
                    
                    redirecionar = true;
                    sessao.setAttribute("message"
                            ,"VOCÊ NÃO TEM PERMISSÕES SUFICIENTES PARA ACESSAR ESTA PÁGINA!");
                     sessao.setAttribute("Perfil", null);
                     
                }
                
            }
            else if(sessao != null && redirecionar){
                sessao.setAttribute("message"
                        ,"VOCÊ DEVE ESTAR AUTENTICADO PARA ACESSAR ESTA PÁGINA!");
            }
            
        }
        
        if(redirecionar){
            ((HttpServletResponse)response).sendRedirect(req.getContextPath()
                    +"/faces/index.xhtml");
        }
        else{
            chain.doFilter(request, response);
        }
            
    }
        
    @Override
    public void destroy(){}    
    
}
