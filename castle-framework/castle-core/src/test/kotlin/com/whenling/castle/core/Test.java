package com.whenling.castle.core;


import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.awt.*;

public class Test {

    @org.junit.Test
    public void test() {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("usergui.properties"));
        try {
            Configuration config = builder.getConfiguration();

            String backColor = config.getString("colors.background");
            Dimension size = new Dimension(config.getInt("window.width"),
                    config.getInt("window.height"));
        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }
    }
}
