package de.gesellix.websocket.demo.atmosphere;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Meteor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AtmosphereArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return AtmosphereResource.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Meteor meteor = Meteor.build(webRequest.getNativeRequest(HttpServletRequest.class));
    return meteor.getAtmosphereResource();
  }
}
