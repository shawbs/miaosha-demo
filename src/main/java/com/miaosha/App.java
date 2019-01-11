package com.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */

@EnableAutoConfiguration
@RestController
public class App
{
    @RequestMapping("/")
    public String Home(){
        return "hello wolrd";
    }

    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        System.out.print("asdasd");

        SpringApplication.run(App.class, args);
    }
}
