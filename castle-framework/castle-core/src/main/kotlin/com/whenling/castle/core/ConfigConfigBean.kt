package com.whenling.castle.core

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport
import org.springframework.context.annotation.Bean


class ConfigConfigBean {
    @Bean
    fun placeholderConfigurer(): PlaceholderConfigurerSupport {
        val placeholderConfigurer = ConfigurationPropertyResourceConfigurer(
                StaticConfigSupplier.configuration)
        placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true)

        return placeholderConfigurer
    }
}