package de.htwkleipzig.mmdb.vaadin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.ui.Component;

public class Injector {

    public static void inject(Component component) {
        inject(component, getApplicationContext());
    }

    private static ApplicationContext getApplicationContext() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession(false);
        return WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
    }

    private static void inject(Component component, ApplicationContext applicationContext) {
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBeanProperties(component, AutowireCapableBeanFactory.AUTOWIRE_AUTODETECT, false);
    }
}
