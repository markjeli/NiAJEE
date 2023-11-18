package com.jelinski.niajee.motorcycle.interceptor;

import com.jelinski.niajee.motorcycle.entity.Motorcycle;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import lombok.extern.java.Log;
import com.jelinski.niajee.motorcycle.interceptor.binding.LogMethodCall;

import java.util.UUID;

@Interceptor
@LogMethodCall
@Priority(10)
@Log
public class LogMethodCallInterceptor {
    /**
     * Security context.
     */
    private final SecurityContext securityContext;

    /**
     * @param securityContext security context
     */
    @Inject
    public LogMethodCallInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        UUID id = null;
        if ((context.getParameters()[0] instanceof Motorcycle)) {
            Motorcycle motorcycle = (Motorcycle) context.getParameters()[0];
            id = motorcycle.getId();
        } else if ((context.getParameters()[0] instanceof UUID)) {
            id = (UUID) context.getParameters()[0];
        }

        log.info(
                "User " + securityContext.getCallerPrincipal().getName() +
                        " called method " + context.getMethod().getName() +
                        " with parameter " + id
        );
        return context.proceed();
    }
}
