package nhnis.fw.commons.configuration;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import nhnis.fw.commons.interceptor.ServicePreventationInterceptor;
import nhnis.fw.commons.resolver.RequestBodyArgumentResolver;

/**
 * 레거시 웹 설정. TCF 경로와 충돌하므로 기본 비활성.
 */
@Configuration
@ConditionalOnProperty(name = "nhnis.fw.commons.legacy-web.enabled", havingValue = "true")
public class WebConfiguration implements WebMvcConfigurer {

    private final ServicePreventationInterceptor servicePreventionInterceptor;
    private final RequestBodyArgumentResolver resolver;

    public WebConfiguration(ServicePreventationInterceptor servicePreventionInterceptor,
            RequestBodyArgumentResolver resolver) {
        this.servicePreventionInterceptor = servicePreventionInterceptor;
        this.resolver = resolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(servicePreventionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/error");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
