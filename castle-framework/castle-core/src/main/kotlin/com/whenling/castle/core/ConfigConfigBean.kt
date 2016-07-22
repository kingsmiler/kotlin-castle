package com.whenling.castle.core

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = arrayOf("com.whenling.castle.core"))
open class ConfigConfigBean {

    @Bean
    open fun placeholderConfigurer(): PlaceholderConfigurerSupport {
        val placeholderConfigurer = ConfigurationPropertyResourceConfigurer(
                StaticConfigSupplier.configuration)
        placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true)

        return placeholderConfigurer
    }
}