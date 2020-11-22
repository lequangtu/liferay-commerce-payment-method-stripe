/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.uminas.commerce.payment.method.stripe.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author qtle
 */
@ExtendedObjectClassDefinition(
	category = "payment", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.uminas.commerce.payment.method.paypal.configuration.StripeGroupServiceConfiguration",
	localization = "content/Language",
	name = "commerce-payment-method-stripe-group-service-configuration-name"
)
public interface StripeGroupServiceConfiguration {

	@Meta.AD(name = "public-key", required = false)
	public String publicKey();
	
	@Meta.AD(name = "secret-key", required = false)
	public String secretKey();

}