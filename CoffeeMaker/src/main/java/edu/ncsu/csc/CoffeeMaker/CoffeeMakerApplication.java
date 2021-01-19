package edu.ncsu.csc.CoffeeMaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication ( scanBasePackages = { "edu.ncsu.csc.CoffeeMaker" } )
public class CoffeeMakerApplication {

    public static void main ( final String[] args ) {
        SpringApplication.run( CoffeeMakerApplication.class, args );
    }

}
