/*******************************************************************************
 * Copyright (C) 2020 qtle
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.uminas.commerce.payment.method.stripe.constant;

public interface StripeCommercePaymentMethodConstants {
	
	public static final String SERVICE_NAME =
			"com.liferay.commerce.payment.engine.method.stripe";

	public static final String START_PAYMENT_SERVLET_PATH = "start-stripe-payment";

	public static final String COMPLETE_PAYMENT_SERVLET_PATH = "complete-stripe-payment";
	
	public static final String STRIPE_SESSION_ID = "stripeSessionId";


	public static final String AUTHORIZATION_STATE_COMPLETED = "completed";

	public static final String AUTHORIZATION_STATE_CREATED = "created";

	public static final String GROUP_ID = "groupId";

	public static final String PUBLIC_KEY = "publicKey";

}
