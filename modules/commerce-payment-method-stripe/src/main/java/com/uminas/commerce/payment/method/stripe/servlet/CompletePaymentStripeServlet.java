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
package com.uminas.commerce.payment.method.stripe.servlet;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.uminas.commerce.payment.method.stripe.configuration.StripeGroupServiceConfiguration;
import com.uminas.commerce.payment.method.stripe.constant.StripeCommercePaymentMethodConstants;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = {
		"osgi.http.whiteboard.context.path=/" + StripeCommercePaymentMethodConstants.COMPLETE_PAYMENT_SERVLET_PATH,
		"osgi.http.whiteboard.servlet.name=com.uminas.commerce.payment.method.stripe.servlet.CompletePaymentStripeServlet",
		"osgi.http.whiteboard.servlet.pattern=/" + StripeCommercePaymentMethodConstants.COMPLETE_PAYMENT_SERVLET_PATH
				+ "/*" }, service = Servlet.class)
public class CompletePaymentStripeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ServletException, IOException {
		try {
			HttpSession httpSession = httpServletRequest.getSession();

			if (PortalSessionThreadLocal.getHttpSession() == null) {
				PortalSessionThreadLocal.setHttpSession(httpSession);
			}

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(
					_portal.getUser(httpServletRequest));

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
			String uuid = ParamUtil.getString(httpServletRequest, "uuid");

			CommerceOrder commerceOrder =
					_commerceOrderLocalService.getCommerceOrderByUuidAndGroupId(
					uuid, groupId);

			boolean cancel = ParamUtil.getBoolean(httpServletRequest, "cancel");

			String redirect = ParamUtil.getString(
				httpServletRequest, "redirect");

			if (cancel) {
				_commercePaymentEngine.cancelPayment(
					commerceOrder.getCommerceOrderId(), null,
					httpServletRequest);
			}
			else {
				_commercePaymentEngine.completePayment(
					commerceOrder.getCommerceOrderId(), null,
					httpServletRequest);
			}

			httpServletResponse.sendRedirect(redirect);
		}
		catch (Exception e) {
			_portal.sendError(e, httpServletRequest, httpServletResponse);
		}
	}


	private static final Log _log = LogFactoryUtil.getLog(CompletePaymentStripeServlet.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;
	

	@Reference
	private CommercePaymentEngine _commercePaymentEngine;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.uminas.commerce.payment.method.stripe)")
	private ServletContext _servletContext;

}
