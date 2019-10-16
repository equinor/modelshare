package com.equinor.modelshare.app;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.StringUtils;

import com.equinor.modelshare.security.AzureRepositoryAccessControl;
import com.microsoft.azure.spring.autoconfigure.aad.AADAuthenticationProperties;
import com.microsoft.azure.spring.autoconfigure.aad.ServiceEndpointsProperties;

/**
 * <p>
 * This type is a fork of AADOAuth2UserService which does <i>not</i> attempt to
 * map user groups to authorities since that will end with the following error
 * message when authenticating using the Equinor Azure AD:
 * </p>
 * 
 * <pre>
 * Server returned HTTP response code: 403 for 
 *   URL: https://graph.windows.net/me/memberOf?api-version=1.6
 * </pre>
 * <p>
 * The problem is related to use of the permission "Access the directory as the
 * signed-in user", which should not require <i>Administrator</i> consent, but
 * in practice there are some limitations.
 * </p>
 * <p>
 * The issue has been circumvented by granting the <b>ROLE_USER</b> authority to
 * all logged in users, as this application currently does not make use of
 * roles. However it could be implemented by different means. See
 * {@link AzureRepositoryAccessControl} for hints.
 * </p>
 * 
 * @author Torkild Ulvøy Resheim, Itema AS
 * @see com.microsoft.azure.spring.autoconfigure.aad.AADOAuth2UserService
 */
public class DefaultAuthorityAADOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private static final String DEFAULT_USERNAME_ATTR_NAME = "name";
    
    private static final SimpleGrantedAuthority DEFAULT_AUTHORITY = new SimpleGrantedAuthority("ROLE_USER");
    
    public DefaultAuthorityAADOAuth2UserService(AADAuthenticationProperties aadAuthProps,
                                ServiceEndpointsProperties serviceEndpointsProps) {
    }

    @Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		final OidcUserService delegate = new OidcUserService();

		// Delegate to the default implementation for loading a user
		OidcUser oidcUser = delegate.loadUser(userRequest);
		
		final Set<GrantedAuthority> mappedAuthorities = Set.of(DEFAULT_AUTHORITY);
		// Create a copy of oidcUser but use the mappedAuthorities instead
		oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), 
				getUserNameAttrName(userRequest));
		return oidcUser;
	}

    private String getUserNameAttrName(OAuth2UserRequest userRequest) {
        String userNameAttrName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        if (StringUtils.isEmpty(userNameAttrName)) {
            userNameAttrName = DEFAULT_USERNAME_ATTR_NAME;
        }

        return userNameAttrName;
    }
}
