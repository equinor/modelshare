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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@EnableConfigurationProperties
@Order(Ordered.LOWEST_PRECEDENCE - 50) // ensure this takes precedence over the Actuator (-10)
@ComponentScan(basePackages= {"com.equinor.modelshare.app", "com.equinor.modelshare.app.web", "com.equinor.modelshare.security"})
public class Application  {
	
	public static void main(String[] args) {		
		SpringApplication.run(Application.class, args);
	}

}

