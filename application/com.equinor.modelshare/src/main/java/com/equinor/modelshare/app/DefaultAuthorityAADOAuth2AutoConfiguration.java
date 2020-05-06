/*******************************************************************************
 * Copyright © 2020 Equinor ASA
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.equinor.modelshare.app;

import static com.microsoft.azure.telemetry.TelemetryData.SERVICE_NAME;
import static com.microsoft.azure.telemetry.TelemetryData.getClassPackageSimpleName;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.ClassUtils;

import com.microsoft.azure.spring.autoconfigure.aad.AADAuthenticationProperties;
import com.microsoft.azure.spring.autoconfigure.aad.AADOAuth2AutoConfiguration;
import com.microsoft.azure.spring.autoconfigure.aad.ServiceEndpointsProperties;
import com.microsoft.azure.telemetry.TelemetrySender;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "azure.activedirectory", value = "tenant-id")
@PropertySource("classpath:/aad-oauth2-common.properties")
@PropertySource(value = "classpath:serviceEndpoints.properties")
@EnableConfigurationProperties({AADAuthenticationProperties.class, ServiceEndpointsProperties.class})
public class DefaultAuthorityAADOAuth2AutoConfiguration {

    private final AADAuthenticationProperties aadAuthProps;

    private final ServiceEndpointsProperties serviceEndpointsProps;

    public DefaultAuthorityAADOAuth2AutoConfiguration(AADAuthenticationProperties aadAuthProperties,
                                      ServiceEndpointsProperties serviceEndpointsProps) {
        this.aadAuthProps = aadAuthProperties;
        this.serviceEndpointsProps = serviceEndpointsProps;
    }

    @Bean
    @ConditionalOnProperty(prefix = "azure.activedirectory", value = "active-directory-groups")
    public OAuth2UserService<OidcUserRequest, OidcUser> workingOidcUserService() {
        return new DefaultAuthorityAADOAuth2UserService(aadAuthProps, serviceEndpointsProps);
    }

    @PostConstruct
    private void sendTelemetry() {
        if (aadAuthProps.isAllowTelemetry()) {
            final Map<String, String> events = new HashMap<>();
            final TelemetrySender sender = new TelemetrySender();

            events.put(SERVICE_NAME, getClassPackageSimpleName(AADOAuth2AutoConfiguration.class));

            sender.send(ClassUtils.getUserClass(getClass()).getSimpleName(), events);
        }
    }
}
