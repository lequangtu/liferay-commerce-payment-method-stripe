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

package com.uminas.commerce.payment.method.stripe.portlet.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.uminas.commerce.payment.method.stripe.constant.StripeCommercePaymentMethodConstants;

/**
 * @author qtle
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PAYMENT_METHODS,
		"mvc.command.name=editStripeCommercePaymentMethodConfiguration"
	},
	service = MVCActionCommand.class
)
public class EditStripeCommercePaymentMethodConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.UPDATE)) {
			_updateCommercePaymentMethod(actionRequest);
		}
	}

	private void _updateCommercePaymentMethod(ActionRequest actionRequest)
		throws Exception {

		long commerceChannelId = ParamUtil.getLong(
				actionRequest, "commerceChannelId");

			CommerceChannel commerceChannel =
				_commerceChannelService.getCommerceChannel(commerceChannelId);

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
				StripeCommercePaymentMethodConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		String publicKey = ParamUtil.getString(
			actionRequest, "settings--publicKey--");

		modifiableSettings.setValue("publicKey", publicKey);

		String secretKey = ParamUtil.getString(
			actionRequest, "settings--secretKey--");

		modifiableSettings.setValue("secretKey", secretKey);


		modifiableSettings.store();
	}

	@Reference
	private CommerceChannelService _commerceChannelService;
	
	@Reference
	private SettingsFactory _settingsFactory;

}