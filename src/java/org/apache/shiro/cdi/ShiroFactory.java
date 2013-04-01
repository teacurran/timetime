/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.apache.shiro.cdi;

import com.approachingpi.timetime.security.TimeTimeAuthorizingRealm;
import com.approachingpi.timetime.security.TimeTimePasswordMatcher;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

import static org.apache.shiro.SecurityUtils.getSecurityManager;
import static org.apache.shiro.SecurityUtils.getSubject;

@ApplicationScoped
public class ShiroFactory {

	Logger LOGGER = LoggerFactory.getLogger(ShiroFactory.class);

	@Inject
	TimeTimeAuthorizingRealm timeTimeAuthorizingRealm;
	
	@PostConstruct
	public void init() {

		LOGGER.info("Initializing Shiro SecurityManager using ");

		SecurityManager securityManager = new DefaultSecurityManager(timeTimeAuthorizingRealm);

		timeTimeAuthorizingRealm.setCredentialsMatcher(new TimeTimePasswordMatcher());
		
		//Make the SecurityManager instance available to the entire application via static memory:
		SecurityUtils.setSecurityManager(securityManager);
		
	}

    @Produces
    public Subject subject() {
        return proxy(Subject.class, new SubjectInvocationhandler());
    }

    @Produces
    public SecurityManager securityManager() {
        return proxy(SecurityManager.class, new SecurityManagerInvocationhandler());
    }

    @Produces
    public Session session() {
        return proxy(Session.class, new SessionInvocationhandler());
    }

    private <T> T proxy(final Class<T> clazz, final InvocationHandler ih) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{ clazz }, ih);
    }

    private static class SubjectInvocationhandler extends Handler {
        public Object handlerInvoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            return method.invoke(getSubject(), args);
        }
    }

    private class SecurityManagerInvocationhandler extends Handler {
        public Object handlerInvoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(getSecurityManager(), args);
        }
    }

    private class SessionInvocationhandler extends Handler {
        public Object handlerInvoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(getSubject().getSession(), args);
        }
    }

    private static abstract class Handler implements InvocationHandler {
        public abstract Object handlerInvoke(Object proxy, Method method, Object[] args) throws Throwable;

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                return handlerInvoke(proxy, method, args);
            } catch (InvocationTargetException ite) {
                throw ite.getTargetException();
            }
        }
    }
}

