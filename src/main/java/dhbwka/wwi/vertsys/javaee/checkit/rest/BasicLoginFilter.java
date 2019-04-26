/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.checkit.rest;

/**
 *
 * @author BEDAAR
 */

import dhbwka.wwi.vertsys.javaee.checkit.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.checkit.common.jpa.User;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Lösungsvorschlag von Martin Kutscher. Da in der web.xml nur ein
 * Anmeldeverfahren definiert werden kann, programmieren wir den HTTP Basic Auth
 * für den Webservice hier einfach manuell aus.
 *
 * In der web.xml müssen hierfür folgende Zeilen ergänzt werden, um diese Klasse
 * zu konfigurieren:
 *
 * <filter>
 * <filter-name>BasicAuthFilter</filter-name>
 * <filter-class>dhbwka.wwi.vertsys.javaee.jtodo.tasks.rest.BasicLoginFilter</filter-class>
 * <init-param>
 * <param-name>role-names-comma-sep</param-name>
 * <param-value>app-user</param-value>
 * </init-param>
 * </filter>
 * <filter-mapping>
 * <filter-name>BasicLoginFilter</filter-name>
 * <url-pattern>/api/*</url-pattern>
 * </filter-mapping>
 *
 * Vgl.
 * https://stackoverflow.com/questions/27588665/how-do-i-configure-both-basic-and-form-authentication-methods-in-the-same-java-e
 */

public class BasicLoginFilter implements Filter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC_PREFIX = "Basic ";
    private static final String FILTER_PARAMETER_ROLE_NAMES_COMMA_SEPARATED = "role-names-comma-sep";
    private static final String ROLE_SEPARATOR = ",";
    private static final String BASIC_AUTH_SEPARATOR = ":";

    @EJB
    private UserBean userBean;

    /**
     * Liste der Benutzerrollen, mit denen sich der Anwender authentifizieren
     * muss
     */
    private List<String> roleNames;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String roleNamesParam = filterConfig.getInitParameter(FILTER_PARAMETER_ROLE_NAMES_COMMA_SEPARATED);
        String[] roleNamesParsed = null;

        if (roleNamesParam != null) {
            roleNamesParsed = roleNamesParam.split(ROLE_SEPARATOR);
        }

        if (roleNamesParsed != null) {
            this.roleNames = Arrays.asList(roleNamesParsed);
        }

        if (this.roleNames == null || this.roleNames.isEmpty()) {
            throw new IllegalArgumentException("No roles defined!");
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with, Authorization");
        response.addHeader("Access-Control-Max-Age", "3600");

        String method = request.getMethod();

        if ("OPTIONS".equals(method)) {
            chain.doFilter(request, response);
            return;
        }

        // Benutzername und Password aus den Authorozation-Header auslesen
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader == null || !authHeader.startsWith(BASIC_PREFIX)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization Header fehlt");
            return;
        }

        String autfHeaderUserPwPart = authHeader.substring(BASIC_PREFIX.length());
        if (autfHeaderUserPwPart == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Anmeldung nur über Basic-Auth möglich");
            return;
        }

        String headerDecoded = new String(Base64.getDecoder().decode(autfHeaderUserPwPart));
        if (!headerDecoded.contains(BASIC_AUTH_SEPARATOR)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Benutzername oder Passwort fehlt");
            return;
        }
        String[] userPwPair = headerDecoded.split(BASIC_AUTH_SEPARATOR);
        if (userPwPair.length != 2) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Benutzername oder Passwort fehlt");
            return;
        }
        String userDecoded = userPwPair[0];
        String passDecoded = userPwPair[1];

        request.logout();
        request.login(userDecoded, passDecoded);

        // check roles for the user
        // Logindaten und Rollenzuordnung prüfen
        User user = this.userBean.findByUsername(userDecoded);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Benutzerprofil nicht gefunden");
            return;
        }

        boolean hasRoles = !Collections.disjoint(this.roleNames, user.getGroups());

        if (hasRoles) {
            chain.doFilter(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Keine ausreichenden Berechtigungen");
        }
    }

    @Override
    public void destroy() {
    }
}
