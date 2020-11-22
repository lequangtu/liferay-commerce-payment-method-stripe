/**
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

package com.uminas.commerce.payment.method.stripe.taglib.ui;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.commerce.payment.constants.CommercePaymentScreenNavigationConstants;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.uminas.commerce.payment.method.stripe.StripeCommercePaymentMethod;
import com.uminas.commerce.payment.method.stripe.configuration.StripeGroupServiceConfiguration;
import com.uminas.commerce.payment.method.stripe.constant.StripeCommercePaymentMethodConstants;

/**
 * @author qtle
 */
@Component(
	property = "screen.navigation.entry.order:Integer=20",
	service = ScreenNavigationEntry.class
)
public class StripeCommercePaymentMethodConfigurationScreenNavigationEntry
	implements ScreenNavigationEntry<CommercePaymentMethodGroupRel> {

	public static final String
		ENTRY_KEY_STRIPE_COMMERCE_PAYMENT_METHOD_CONFIGURATION =
			"stripe-configuration";

	@Override
	public String getCategoryKey() {
		return CommercePaymentScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_PAYMENT_METHOD_CONFIGURATION;
	}

	@Override
	public String getEntryKey() {
		return ENTRY_KEY_STRIPE_COMMERCE_PAYMENT_METHOD_CONFIGURATION;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale,
			CommercePaymentScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_PAYMENT_METHOD_CONFIGURATION);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommercePaymentScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_PAYMENT_METHOD;
	}

	@Override
	public boolean isVisible(
		User user, CommercePaymentMethodGroupRel commercePaymentMethod) {
		if (commercePaymentMethod == null) {
			return false;
		}

		if (StripeCommercePaymentMethod.KEY.equals(
				commercePaymentMethod.getEngineKey())) {

			return true;
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			long commerceChannelId = ParamUtil.getLong(
					httpServletRequest, "commerceChannelId");
			
			CommerceChannel commerceChannel =
					_commerceChannelService.getCommerceChannel(commerceChannelId);

			StripeGroupServiceConfiguration StripeGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					StripeGroupServiceConfiguration.class,
					new ParameterMapSettingsLocator(
						httpServletRequest.getParameterMap(),
						new GroupServiceSettingsLocator(
								commerceChannel.getGroupId(),
							StripeCommercePaymentMethodConstants.
								SERVICE_NAME)));

			httpServletRequest.setAttribute(
				StripeGroupServiceConfiguration.class.getName(),
				StripeGroupServiceConfiguration);
		}
		catch (Exception e) {
			throw new IOException(e);
		}

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/configuration.jsp");
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference(
		target = "(osgi.web.symbolicname=com.uminas.commerce.payment.method.stripe)"
	)
	private ServletContext _servletContext;

}