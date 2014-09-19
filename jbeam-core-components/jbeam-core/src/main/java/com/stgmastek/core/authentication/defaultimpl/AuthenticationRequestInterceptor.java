/*
 * Copyright (c) 2014 Mastek Ltd. All rights reserved.
 * 
 * This file is part of JBEAM. JBEAM is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * JBEAM is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for the specific language governing permissions and 
 * limitations.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 */
package com.stgmastek.core.authentication.defaultimpl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.stgmastek.core.interfaces.IRequestInterceptor;

/**
 * This class is default implementation of IAuthenticationRequestFilter which
 * can be used in case project specific filter class is not required.
 * 
 * @author shantanuc
 * 
 */
public class AuthenticationRequestInterceptor implements IRequestInterceptor {

    private static final Logger logger = Logger.getLogger(AuthenticationRequestInterceptor.class);

    private static final String USER_PASSWORD_START_ELEMENT = "<UsernamePasswordAuth>".toLowerCase();

    private static final String USER_PASSWORD_END_ELEMENT = "</UsernamePasswordAuth>".toLowerCase();

    private static final String TOKEN_AUTHENTICATION_START_ELEMENTS = "<TokenAuthentication> <Token>";

    private static final String TOKEN_AUTHENTICATION_END_ELEMENTS = "</Token> </TokenAuthentication>";
    
    public static AuthenticationRequestInterceptor newInstance(String token) {
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("Token cannot be blank..");
        }
        return new AuthenticationRequestInterceptor(token);
    }

    private final String token;
    
    private AuthenticationRequestInterceptor(final String token) {
        this.token = token;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.stgmastek.core.interfaces.IAuthenticationRequestFilter#
     * filterRequestForAuth(com.stgmastek.core.util.BatchContext,
     * java.lang.String)
     */
    public String intercept(String sourceRequest) {
        
        try {
            String request = sourceRequest;

            int beginIndex = request.toLowerCase().indexOf(USER_PASSWORD_START_ELEMENT);
            int endIndex = request.toLowerCase().indexOf(USER_PASSWORD_END_ELEMENT) + USER_PASSWORD_END_ELEMENT.length();
            if (beginIndex != -1 && endIndex != -1) {
                String replaceTarget = request.substring(beginIndex, endIndex);
                String replacement = TOKEN_AUTHENTICATION_START_ELEMENTS + token + TOKEN_AUTHENTICATION_END_ELEMENTS;

                return request.replace(replaceTarget, replacement);
            }
        } catch (Exception e) {
            logger.warn("Error replacing USER/PASSWORD authorization with token authorization.", e);
        }
        return sourceRequest;
    }

}
