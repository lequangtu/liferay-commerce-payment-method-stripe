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
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.uminas.commerce.payment.method.stripe.constant.StripeCommercePaymentMethodConstants"%>
<%@ include file="/init.jsp" %>

<%
String publicKey = (String)request.getAttribute(StripeCommercePaymentMethodConstants.PUBLIC_KEY);

String stripeSessionId = (String)request.getAttribute(StripeCommercePaymentMethodConstants.STRIPE_SESSION_ID);
%>
<script src="https://js.stripe.com/v3/"></script>
<script>
var stripe = Stripe('<%= publicKey%>');
stripe.redirectToCheckout({	  
	  sessionId: '<%= stripeSessionId%>'
	}).then(function (result) {
	  log.info("Error msg:" + result.error.message);
	});
</script>
