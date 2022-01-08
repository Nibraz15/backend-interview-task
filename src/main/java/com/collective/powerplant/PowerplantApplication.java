package com.collective.powerplant;

import com.collective.powerplant.authnticator.JWTAuthorizationFilter;
import com.collective.powerplant.model.User;
import com.collective.powerplant.model.request.PowerPlantBatchSaveRequest;
import com.collective.powerplant.service.impl.PowerPlantServiceImpl;
import com.collective.powerplant.service.impl.UserServiceImpl;
import com.collective.powerplant.util.SerDe;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
public class PowerplantApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowerplantApplication.class, args);
	}

	@Bean
	CommandLineRunner run(PowerPlantServiceImpl powerPlantService, UserServiceImpl userService){

		/**
		 * Preloading DataBase With DataSet if the DataBase is being created as new**/
		return args -> {
			if(powerPlantService.count() == 0){
				ClassPathResource cpr = new ClassPathResource("dataSet.json");
				BufferedReader reader = new BufferedReader(new InputStreamReader( cpr .getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				powerPlantService.batchSave(SerDe.deserialize(sb.toString(), PowerPlantBatchSaveRequest.class));

			}

			if(userService.count() == 0) {
				ClassPathResource cpr = new ClassPathResource("user.json");
				BufferedReader reader = new BufferedReader(new InputStreamReader( cpr .getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				userService.addUser(SerDe.deserialize(sb.toString(), User.class));
			}

		};

	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/user/Signup").permitAll()
					.antMatchers(HttpMethod.GET,"/user/login").permitAll()
					.anyRequest().authenticated();
		}
	}

}
