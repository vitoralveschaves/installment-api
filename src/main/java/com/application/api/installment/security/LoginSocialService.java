package com.application.api.installment.security;

import com.application.api.installment.entities.User;
import com.application.api.installment.repositories.UserRepository;
import com.application.api.installment.services.RoleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoginSocialService extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        var user = userRepository.findByEmail(email);

        if(user.isEmpty()) {
            user = Optional.of(createNewUser(oAuth2User));
        }

        String userToken = tokenService.generateToken(user.get());

        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + userToken + "\"}");
        response.getWriter().flush();
    }

    private User createNewUser(OAuth2User oAuth2User) {
        User user = new User();
        user.setEmail(oAuth2User.getAttribute("email"));
        user.setName(oAuth2User.getAttribute("name"));
        user.setPassword(UUID.randomUUID().toString());
        user.setActive(true);
        userRepository.save(user);
        roleService.addRoleToUser(user.getId(), "BASIC");
        return user;
    }

}
