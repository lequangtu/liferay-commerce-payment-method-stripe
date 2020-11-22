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
package com.uminas.commerce.payment.method.stripe;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.request.CommercePaymentRequest;
import com.liferay.commerce.payment.request.CommercePaymentRequestProvider;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.uminas.commerce.payment.method.stripe.constant.StripeCommercePaymentMethodConstants;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author qtle
 */
@Component(
	immediate = true,
	property = "commerce.payment.engine.method.key=" + StripeCommercePaymentMethod.KEY,
	service = CommercePaymentRequestProvider.class
)
public class StripeCommercePaymentRequestProvider
	implements CommercePaymentRequestProvider {

	@Override
	public CommercePaymentRequest getCommercePaymentRequest(
			String cancelUrl, long commerceOrderId,
			HttpServletRequest httpServletRequest, Locale locale,
			String returnUrl, String transactionId)
		throws PortalException {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		String sessionId = null;

		if (httpServletRequest != null) {
			sessionId = ParamUtil.getString(httpServletRequest, StripeCommercePaymentMethodConstants.STRIPE_SESSION_ID);
		}

		return new StripeCommercePaymentRequest(
			commerceOrder.getTotal(), cancelUrl, commerceOrderId, locale,
			sessionId,  httpServletRequest,returnUrl, transactionId);
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

}
