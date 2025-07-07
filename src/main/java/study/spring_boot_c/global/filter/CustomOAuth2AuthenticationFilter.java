package study.spring_boot_c.global.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import study.spring_boot_c.domain.member.domain.entity.Member;
import study.spring_boot_c.domain.member.domain.repository.MemberRepository;
import study.spring_boot_c.global.jwt.JwtTokenProvider;

import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomOAuth2AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String DEFAULT_FILTER_PROCESSING_URI = "/oauth2Login/**";

    private DefaultOAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    private OAuth2AuthorizationSuccessHandler successHandler;

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;

    private MemberRepository memberRepository;

    private JwtTokenProvider jwtTokenProvider;

    private Duration clockSkew = Duration.ofSeconds(60);

    private Clock clock = Clock.systemUTC();

    public CustomOAuth2AuthenticationFilter(DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager,
                                            OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository,
                                            JwtTokenProvider jwtTokenProvider
                                            , MemberRepository memberRepository) {
        super(DEFAULT_FILTER_PROCESSING_URI);
        this.jwtTokenProvider = jwtTokenProvider;
        this.oAuth2AuthorizedClientManager = defaultOAuth2AuthorizedClientManager;
        this.oAuth2AuthorizedClientRepository = oAuth2AuthorizedClientRepository;
        this.oAuth2UserService = new DefaultOAuth2UserService();
        this.memberRepository = memberRepository;
        this.successHandler = (authorizedClient, principal, attributes) -> {
            HttpServletRequest httpServletRequest = (HttpServletRequest) attributes.get(HttpServletRequest.class.getName());
            HttpServletResponse httpServletResponse = (HttpServletResponse) attributes.get(HttpServletResponse.class.getName());
            oAuth2AuthorizedClientRepository
                    .saveAuthorizedClient(authorizedClient, principal,
                            httpServletRequest, httpServletResponse);
            System.out.println("authorizedClient = " + authorizedClient);
            System.out.println("principal = " + principal);
            System.out.println("attributes = " + attributes);

            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
            String email = (String)token.getPrincipal().getAttribute("email");
//            Optional<Member> memberOptional = memberRepository.findByEmail(email);
//            if (memberOptional.isPresent()) {
                String accessToken = jwtTokenProvider.generateAccessToken(email);
                String refreshToken = jwtTokenProvider.generateRefreshToken(email);
                httpServletRequest.setAttribute("Authorization", "Bearer " + accessToken);
                httpServletRequest.setAttribute("RefreshToken", refreshToken);

//                httpServletResponse.setHeader("Authorization", "Bearer " + accessToken);
//                httpServletResponse.setHeader("RefreshToken", refreshToken);
//            }
//            else {
                //자동 회원가입 처리를 하거나, 부가적으로 입력받을 수 있도록 하거나...
                return;
//            }

        };

//        oAuth2AuthorizedClientManager.setAuthorizationSuccessHandler(successHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) { // FIlter기 때문에 익명사용자가 authentication에 저장되어있지 않을 수 있음
            authentication = new AnonymousAuthenticationToken("anonymous", "anonymousUser",
                    AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        }

        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("keycloak")
                .principal(authentication)
                .attribute(HttpServletRequest.class.getName(), request)
                .attribute(HttpServletResponse.class.getName(), response)
                .build();

        OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClientManager.authorize(authorizeRequest);

        // 권한 부여 타입을 변경하지 않고 토큰 재발금
        if (authorizedClient != null && hasTokenExpired(authorizedClient.getAccessToken())
                && authorizedClient.getRefreshToken() != null) {
            authorizedClient = oAuth2AuthorizedClientManager.authorize(authorizeRequest);
        }
        if (authorizedClient != null) {
            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();


            OAuth2User oAuth2User = oAuth2UserService.loadUser
                    (new OAuth2UserRequest(authorizedClient.getClientRegistration(), accessToken));

            OAuth2AuthenticationToken authenticationToken = new OAuth2AuthenticationToken
                    (oAuth2User, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")),
                            authorizedClient.getClientRegistration().getRegistrationId());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            this.successHandler.onAuthorizationSuccess(authorizedClient, authenticationToken,
                    createAttributes(request, response));
            return authenticationToken;
        }
        return null;
    }

    private boolean hasTokenExpired(OAuth2Token token) {
        return this.clock.instant().isAfter(token.getExpiresAt().minus(this.clockSkew));
    }

    private static Map<String, Object> createAttributes(HttpServletRequest servletRequest,
                                                        HttpServletResponse servletResponse) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(HttpServletRequest.class.getName(), servletRequest);
        attributes.put(HttpServletResponse.class.getName(), servletResponse);
        return attributes;
    }

    private void sendRedirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
