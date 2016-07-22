package com.whenling.castle.core

import java.util.ArrayList

import org.apache.commons.configuration2.plist.PropertyListConfiguration
import org.apache.commons.configuration2.tree.OverrideCombiner
import org.apache.commons.io.FilenameUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.core.io.ClassPathResource

import com.google.common.base.Preconditions
import org.apache.commons.configuration2.*
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder


object StaticConfigSupplier {
    private var CONFIGURATION: CombinedConfiguration? = null
    private val _configLocations = ArrayList<String>()
    private var _frozen: Boolean = false

    fun prepend(configLocation: String) {
        Preconditions.checkState(!_frozen)
        Preconditions.checkArgument(!StringUtils.isBlank(configLocation))

        _configLocations.add(0, configLocation)
    }

    fun append(configLocation: String) {
        Preconditions.checkState(!_frozen)
        Preconditions.checkArgument(!StringUtils.isBlank(configLocation))

        _configLocations.add(configLocation)
    }

    val configuration: Configuration
        get() {
            if (CONFIGURATION == null) {
                val combinedConfig = CombinedConfiguration()

                combinedConfig.nodeCombiner = OverrideCombiner()

                for (configLocation in _configLocations) {
                    var subConfig: AbstractConfiguration? = null

                    if (StringUtils.endsWith(configLocation, ".xml")) {
                        subConfig = XMLConfiguration(ClassPathResource(configLocation).path)

                    } else if (StringUtils.endsWith(configLocation, ".plist")) {
                        subConfig = PropertyListConfiguration(ClassPathResource(configLocation).path)

                    } else if (StringUtils.endsWith(configLocation, ".properties")) {
                        subConfig = PropertiesConfiguration(ClassPathResource(configLocation).path)

                    } else {
                        throw IllegalStateException("unsupport configuration file type '"
                                + FilenameUtils.getExtension(configLocation) + '"')
                    }

                    if (subConfig is FileBasedConfigurationBuilder<FileBasedConfiguration>) {

                    }

                    combinedConfig.addConfiguration(subConfig)
                }


                combinedConfig.setForceReloadCheck(false)

                CONFIGURATION = combinedConfig

                _frozen = true
            }
            return CombinedConfiguration()
        }



}
