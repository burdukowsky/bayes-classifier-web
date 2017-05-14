package tk.burdukowsky.BayesClassifierWeb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Создано пользователем STANISLAV 14.05.2017 18:24.
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("css/**").
                addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("js/**").
                addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("fonts/**").
                addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("favicon.ico").
                addResourceLocations("classpath:/static/favicon.ico");
    }
}
