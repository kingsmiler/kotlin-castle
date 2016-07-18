package com.whenling.castle.core

import org.apache.commons.configuration2.Configuration
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport
import org.springframework.context.EnvironmentAware
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.env.*
import org.springframework.util.Assert
import org.springframework.util.StringValueResolver
import java.util.*


class ConfigurationPropertyResourceConfigurer(private val configuration: Configuration) : PlaceholderConfigurerSupport(), EnvironmentAware {
    override fun processProperties(beanFactory: ConfigurableListableBeanFactory?, props: Properties?) {
        throw UnsupportedOperationException()
    }

    private var propertySources: MutablePropertySources? = null

    private var appliedPropertySources: PropertySources? = null

    private var environment: Environment? = null

    fun setPropertySources(propertySources: PropertySources) {
        this.propertySources = MutablePropertySources(propertySources)
    }

    override fun setEnvironment(environment: Environment) {
        this.environment = environment
    }

    @Throws(BeansException::class)
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        if (this.propertySources == null) {
            this.propertySources = MutablePropertySources()
            if (this.environment != null) {
                this.propertySources!!.addLast(
                        object : PropertySource<Environment>(ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME, this.environment!!) {
                            override fun getProperty(key: String): String {
                                return this.source.getProperty(key)
                            }
                        })
            }
            val localPropertySource = object : PropertySource<Configuration>(
                    PropertySourcesPlaceholderConfigurer.LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME, configuration) {
                override fun getProperty(name: String?): Any {
                    var name = name
                    if (name != null && name.indexOf("?:") > 0) {
                        name = name.substring(0, name.indexOf("?:"))
                    }
                    return this.source.getProperty(name)
                }
            }
            propertySources!!.addFirst(localPropertySource)
        }

        processProperties(beanFactory, PropertySourcesPropertyResolver(this.propertySources))
        this.appliedPropertySources = this.propertySources
    }

    @Throws(BeansException::class)
     fun processProperties(beanFactoryToProcess: ConfigurableListableBeanFactory,
                                    propertyResolver: ConfigurablePropertyResolver) {

        propertyResolver.setPlaceholderPrefix(this.placeholderPrefix)
        propertyResolver.setPlaceholderSuffix(this.placeholderSuffix)
        propertyResolver.setValueSeparator(this.valueSeparator)

        val valueResolver = StringValueResolver { strVal ->
            val resolved = if (ignoreUnresolvablePlaceholders)
                propertyResolver.resolvePlaceholders(strVal)
            else
                propertyResolver.resolveRequiredPlaceholders(strVal)
            if (resolved == nullValue) null else resolved
        }

        doProcessProperties(beanFactoryToProcess, valueResolver)
    }



    @Throws(IllegalStateException::class)
    fun getAppliedPropertySources(): PropertySources? {
        Assert.state(this.appliedPropertySources != null, "PropertySources have not get been applied")
        return this.appliedPropertySources
    }

    companion object {

        val LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME = "localProperties"

        val ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME = "environmentProperties"
    }

}