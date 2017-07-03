package com.dyndyn.mvcapp.configuration;

import com.dyndyn.mvcapp.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;
import static java.util.Collections.singletonList;

/**
 * Created by dyndyn on 19.06.2017.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.dyndyn.mvcapp")
@PropertySource({"classpath:application.properties", "classpath:message.properties"})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${application.localeCookieName}")
    private String localeCookieName;

    @Value("${application.localeCookieMaxAge}")
    private Integer localeCookieMaxAge;

    @Value("${application.maxUploadSizePerFile}")
    private Long maxUploadSizePerFile;

    /**
     * Configures static resources location paths
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("WEB-INF/resources/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("WEB-INF/resources/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("WEB-INF/resources/fonts/");
        registry.addResourceHandler("/img/**").addResourceLocations("WEB-INF/resources/img/");
    }

    /**
     * Creates new {@link org.springframework.web.multipart.MultipartResolver} instance
     *
     * @return created instance
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(maxUploadSizePerFile);  //1MB
        return resolver;
    }

    /**
     * Creates new {@link org.springframework.context.support.ReloadableResourceBundleMessageSource} instance
     *
     * @return created instance
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/WEB-INF/resources/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Creates new {@link org.springframework.web.servlet.i18n.CookieLocaleResolver} instance
     *
     * @return created instance
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("en"));
        resolver.setCookieName(localeCookieName);
        resolver.setCookieMaxAge(localeCookieMaxAge);
        return resolver;
    }

    /**
     * Creates a validator instance with custom message source
     *
     * @return validator's instance
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource());
        return validatorFactoryBean;
    }

    /**
     * Register our custom validator as a default validator
     */
    @Override
    public Validator getValidator() {
        return validator();
    }

    /**
     * Specialization of PlaceholderConfigurerSupport that resolves ${...} placeholders
     * within bean definition property values and @Value annotations against the current
     * Spring Environment and its set of PropertySources.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Instantiates new {@link TokenInterceptor} object
     *
     * @return created interceptor instance
     */
    @Bean
    public TokenInterceptor addTokenHeaderInterceptor() {
        return new TokenInterceptor();
    }

    /**
     * Creates RestTemplate Bean with injected {@link TokenInterceptor} instance
     *
     * @return created RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.setInterceptors(singletonList(addTokenHeaderInterceptor()));
        return template;
    }

}
