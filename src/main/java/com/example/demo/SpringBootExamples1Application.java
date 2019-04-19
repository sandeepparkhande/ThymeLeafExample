package com.example.demo;

import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@SpringBootApplication
public class SpringBootExamples1Application {

	public static void main(String[] args) {
		ApplicationContext applicationContext=SpringApplication.run(SpringBootExamples1Application.class, args);
		
		final Context ctx = new Context();
		ctx.setVariable("name", "Sandeep");
		ctx.setVariable("subscriptionDate", new Date());
		ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
		//ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML
		TemplateEngine templateEngine=applicationContext.getBean("emailTemplateEngine",TemplateEngine.class);
		final String htmlContent = templateEngine.process("index", ctx);
		System.out.println(" HTML "+htmlContent);
	}
	
	   @Bean
	    public TemplateEngine emailTemplateEngine() {
	        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	        // Resolver for TEXT emails
	       // templateEngine.addTemplateResolver(textTemplateResolver());
	        // Resolver for HTML emails (except the editable one)
	        templateEngine.addTemplateResolver(htmlTemplateResolver());
	        // Resolver for HTML editable emails (which will be treated as a String)
//	        templateEngine.addTemplateResolver(stringTemplateResolver());
	        // Message source, internationalization specific to emails
	       // templateEngine.setTemplateEngineMessageSource(emailMessageSource());
	        return templateEngine;
	    }

	    private ITemplateResolver htmlTemplateResolver() {
	        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	        //templateResolver.setResourceResolver(new StringAndClassLoaderResourceResolver());
	        templateResolver.setPrefix("templates/"); // src/test/resources/mail
	        templateResolver.setSuffix(".html");
	        templateResolver.setTemplateMode(TemplateMode.HTML);
	      //  templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
	        templateResolver.setCacheable(false);
	        return templateResolver;
	    }
}
