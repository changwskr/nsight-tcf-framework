package nhnis.fw.commons.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nhnis.fw.commons.filter.DefaultFilter;

/**
 * 레거시 DefaultFilter 등록.
 *
 * <p>TCF {@code TcfTraceFilter}와 MDC/요청 전처리가 겹치므로 기본은 비활성이다.
 * 레거시 헤더({@code hdr_nhnis}) 경로가 필요할 때만
 * {@code nhnis.fw.commons.filter.enabled=true}로 켠다.
 */
@Configuration
@ConditionalOnProperty(name = "nhnis.fw.commons.filter.enabled", havingValue = "true")
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<DefaultFilter> defaultFilterRefrigration(DefaultFilter defaultFilter) {
        FilterRegistrationBean<DefaultFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(defaultFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
