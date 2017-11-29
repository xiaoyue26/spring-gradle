import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import readinglist.ctrl.ReaderHandlerMethodArgumentResolver;

@SpringBootApplication
@ComponentScan("readinglist")
public class ReadingListApplication extends WebMvcConfigurerAdapter {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ReadingListApplication.class);
        // To disabled web environment, change `true` to `false`
        application.setWebEnvironment(true);
        application.run(args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/login").setViewName("login");
        logger.info("add login controller ok ");
    }

    @Override
    public void addArgumentResolvers(// 增加前端参数解析器
                                     List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ReaderHandlerMethodArgumentResolver());
    }

}
