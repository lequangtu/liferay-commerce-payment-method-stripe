<%--
#-------------------------------------------------------------------------------
# Copyright (C) 2020 qtle
# 
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
# 
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#-------------------------------------------------------------------------------
--%>
<%@page import="com.uminas.commerce.payment.method.stripe.configuration.StripeGroupServiceConfiguration"%>
<%@ include file="/init.jsp" %>

<%
StripeGroupServiceConfiguration stripeGroupServiceConfiguration = (StripeGroupServiceConfiguration)request.getAttribute(StripeGroupServiceConfiguration.class.getName());
long commerceChannelId = ParamUtil.getLong(request, "commerceChannelId");
%>

<portlet:actionURL name="editStripeCommercePaymentMethodConfiguration" var="editCommercePaymentMethodActionURL" />

<aui:form action="<%= editCommercePaymentMethodActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelId %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset label="authentication">
			
			<div class="alert alert-info">
				<%= LanguageUtil.format(resourceBundle, "stripe-configuration-help", new Object[] {"<a href=\"https://dashboard.stripe.com/dashboard\" target=\"_blank\">", "</a>"}, false) %>
			</div>

			<aui:input id="stripe-public-key" label="stripe-public-key" name="settings--publicKey--" value="<%= stripeGroupServiceConfiguration.publicKey() %>" />

			<aui:input id="stripe-secret-key" label="stripe-secret-key" name="settings--secretKey--" value="<%= stripeGroupServiceConfiguration.secretKey() %>" />

	
		</aui:fieldset>
	</aui:fieldset-group>
	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>
