package com.chicman

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
open class ChicmanVaadinApplication : SpringBootServletInitializer() {

//    @Bean
//    open fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner {
//        return CommandLineRunner {
//            println("Let's inspect the beans provided by Spring Boot:")
//
//            val beanNames = ctx.beanDefinitionNames
//            Arrays.sort(beanNames)
//            for (beanName in beanNames) {
//                println(beanName)
//            }
//        }
//    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ChicmanVaadinApplication::class.java, *args)
        }
    }
}