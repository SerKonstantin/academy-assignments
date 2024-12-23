//package org.academy.service;
//
//import lombok.AllArgsConstructor;
//import org.academy.model.Role;
//import org.academy.model.User;
//import org.academy.repository.UserRepository;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@AllArgsConstructor
//public class SocialAppService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private final UserRepository userRepository;
//
////    private static final Logger logger = LoggerFactory.getLogger(SocialAppService.class);
//
//    @Override
//    @Transactional
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        String username = oAuth2User.getAttribute("login");
//
//        User user = userRepository.findByUsername(username)
//                .orElseGet(() -> {
//                    User newUser = new User();
//                    newUser.setUsername(username);
//                    newUser.setPassword("N/A"); // Не используется
//                    newUser.setRole(Role.USER);
//                    return userRepository.save(newUser);
//                });
//
//        OAuth2User savedUser = new DefaultOAuth2User(
//                user.getAuthorities(),
//                oAuth2User.getAttributes(),
//                "login");
//
//        return savedUser;
//    }
//}
