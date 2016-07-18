package com.whenling.castle.core

import java.util.ArrayList

import org.springframework.beans.BeansException
import org.springframework.beans.factory.BeanFactoryUtils
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import org.springframework.util.Assert

@Component
class SpringContext : ApplicationContextAware {

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        SpringContext.applicationContext = applicationContext
    }

    companion object {
        private var applicationContext: ApplicationContext? = null

        fun autowireBean(existingBean: Any) {
            applicationContext?.autowireCapableBeanFactory?.autowireBean(existingBean)
        }

        fun getBean(name: String): Any {
            Assert.hasText(name)
            return applicationContext!!.getBean(name)
        }

        fun <T> getBean(type: Class<T>): T {
            Assert.notNull(type)
            return applicationContext!!.getBean(type)
        }

        fun <T> getBean(name: String, type: Class<T>): T {
            Assert.hasText(name)
            Assert.notNull(type)
            return applicationContext!!.getBean(name, type)
        }

        fun <T : Annotation, F> findAnnotatedBeans(annotationType: Class<T>, elementType: Class<F>): List<F> {
            val beans = ArrayList<F>()
            for (name in BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext!!, Any::class.java)) {
                if (applicationContext!!.findAnnotationOnBean(name, annotationType) != null) {
                    beans.add(applicationContext!!.getBean(name, elementType))
                }
            }

            return beans
        }

        fun <T> findBeansByType(beanType: Class<T>): List<T> {
            val beans = ArrayList<T>()
            beans.addAll(applicationContext!!.getBeansOfType(beanType).values)

            return beans
        }
    }
}
