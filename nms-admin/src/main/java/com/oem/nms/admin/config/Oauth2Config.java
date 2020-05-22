package com.oem.nms.admin.config;

import com.oem.nms.admin.service.UserDetailsServiceImpl;
import com.oem.nms.common.entity.db.admin.RoleType;
import com.oem.nms.common.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Seaway John
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    private static final String OAUTH2_GRANT_TYPE_PASSWORD = "password";
    private static final String OAUTH2_GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    private static final String OAUTH2_CLIENT_01_SCOPE = "nms-client-01";
    private static final String OAUTH2_CLIENT_01_USERNAME = "nms-client-01-admin";
    private static final String OAUTH2_CLIENT_01_PASSWORD = "nms-client-01-123456";

    private static final String OAUTH2_CLIENT_02_SCOPE = "nms-client-02-me";
    private static final String OAUTH2_CLIENT_02_USERNAME = "nms-client-02-admin";
    private static final String OAUTH2_CLIENT_02_PASSWORD = "nms-client-02-123456";

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public Oauth2Config(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(Constants.JWT_SIGNATURE_KEY);

        TokenStore tokenStore = new JwtTokenStore(converter);

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(converter)
                .userDetailsService(userDetailsServiceImpl);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(OAUTH2_CLIENT_01_USERNAME)
                .secret(passwordEncoder.encode(OAUTH2_CLIENT_01_PASSWORD))
                .scopes(OAUTH2_CLIENT_01_SCOPE)
                .authorities(String.valueOf(RoleType.ROLE_GUEST))
                .authorizedGrantTypes(OAUTH2_GRANT_TYPE_PASSWORD, OAUTH2_GRANT_TYPE_REFRESH_TOKEN)
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(86400)
                .and()
                .withClient(OAUTH2_CLIENT_02_USERNAME)
                .secret(passwordEncoder.encode(OAUTH2_CLIENT_02_PASSWORD))
                .scopes(OAUTH2_CLIENT_02_SCOPE)
                .authorities(String.valueOf(RoleType.ROLE_USER))
                .authorizedGrantTypes(OAUTH2_GRANT_TYPE_PASSWORD, OAUTH2_GRANT_TYPE_REFRESH_TOKEN)
                .accessTokenValiditySeconds(86400 * 7)
                .refreshTokenValiditySeconds(86400 * 30);
    }

}
