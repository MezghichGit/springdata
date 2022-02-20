package com.sip.ams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.sip.ams.controllers.ArticleController;

import java.io.File;
@SpringBootApplication
public class AmsdataApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new File(ArticleController.uploadDirectory).mkdir();
		SpringApplication.run(AmsdataApplication.class, args);
		//System.out.println(Long.MAX_VALUE);
	}

}
