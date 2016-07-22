package com.whenling.castle.core

import org.apache.commons.configuration2.FileBasedConfiguration
import org.apache.commons.configuration2.PropertiesConfiguration
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder
import org.apache.commons.configuration2.builder.fluent.Parameters
import org.apache.commons.configuration2.ex.ConfigurationException
import org.junit.Test
import java.awt.Dimension


class Configuration2Test {

    @Test fun testProperties() {

        val params = Parameters()
        val builder = FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration::class.java)
                .configure(params.properties()
                        .setFileName("usergui.properties"))
        try {
            val config = builder.configuration

            val backColor = config.getString("colors.background")
            val size = Dimension(config.getInt("window.width"), config.getInt("window.height"))

            println(size)
            println(backColor)
        } catch (cex: ConfigurationException) {
            // loading of the configuration file failed
            println(cex)
        }

    }
}