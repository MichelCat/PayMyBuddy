package com.paymybuddy.paymybuddy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@SpringBootApplication
public class PaymybuddyApplication {
    
    @Autowired
    private ThymeleafProperties properties;
    
    @Value("${spring.thymeleaf.templates_root:}")
    private String templatesRoot;
    
    public static void main(String[] args) {
      SpringApplication.run(PaymybuddyApplication.class, args);
    }
    
    @Bean
    public ITemplateResolver defaultTemplateResolver() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setSuffix(properties.getSuffix());
        resolver.setPrefix(templatesRoot);
        resolver.setTemplateMode(properties.getMode());
        resolver.setCacheable(properties.isCache());
        return resolver;
    }
}
