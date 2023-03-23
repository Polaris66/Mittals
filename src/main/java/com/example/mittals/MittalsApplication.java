// package com.example.mittals;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// @SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// public class MittalsApplication {

//     public static void main(String[] args) {
//         SpringApplication.run(MittalsApplication.class, args);
//     }

// }
package com.example.mittals;  
import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.SpringBootApplication;  
import org.springframework.boot.builder.SpringApplicationBuilder;  
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;  
@SpringBootApplication  
public class MittalsApplication extends SpringBootServletInitializer  
{  
@Override  
protected SpringApplicationBuilder configure(SpringApplicationBuilder application)   
{  
return application.sources(MittalsApplication.class);  
}  
public static void main(String[] args)   
{  
SpringApplication.run(MittalsApplication.class, args);  
}  
}  
