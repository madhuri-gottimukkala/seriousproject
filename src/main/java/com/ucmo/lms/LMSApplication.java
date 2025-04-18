package com.ucmo.lms;

import java.sql.*;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ucmo.lms.dao.MemberRepository;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackageClasses = MemberRepository.class)
@EnableAutoConfiguration
public class LMSApplication {
	// for how to do this: http://zetcode.com/springboot/bean/
	// for the code below: https://stackoverflow.com/questions/27623405/thymeleaf-add-parameter-to-current-url
	@Bean
	public Function<String, String> currentUrlWithoutParam() {
		return param -> ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(param).toUriString();
	}

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(LMSApplication.class, args);
	}
}
