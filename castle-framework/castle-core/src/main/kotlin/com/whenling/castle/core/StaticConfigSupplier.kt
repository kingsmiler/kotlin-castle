package com.whenling.castle.core

import java.util.ArrayList

import org.apache.commons.configuration2.AbstractConfiguration
import org.apache.commons.configuration2.CombinedConfiguration

import org.apache.commons.configuration2.PropertiesConfiguration
import org.apache.commons.configuration2.XMLConfiguration
import org.apache.commons.configuration2.plist.PropertyListConfiguration
import org.apache.commons.configuration2.tree.OverrideCombiner
import org.apache.commons.io.FilenameUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.core.io.ClassPathResource

import com.google.common.base.Preconditions


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

    val configuration: CombinedConfiguration
        get() {
            if (CONFIGURATION == null) {
                try {
                    val combinedConfig = CombinedConfiguration()
                    combinedConfig.setNodeCombiner(OverrideCombiner())

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

                        if (subConfig is FileConfiguration) {
                            (subConfig as FileConfiguration).setAutoSave(false)
                        }

                        combinedConfig.addConfiguration(subConfig)
                    }

                    combinedConfig.setForceReloadCheck(false)

                    CONFIGURATION = combinedConfig

                    _frozen = true
                } catch (ex: ConfigurationException) {
                    throw RuntimeException(ex)
                }

            }

            return CONFIGURATION
        }
}
