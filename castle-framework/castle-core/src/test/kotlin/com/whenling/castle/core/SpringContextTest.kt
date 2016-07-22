package com.whenling.castle.core

import org.junit.Assert
import org.junit.Test


class SpringContextTest : SpringTestConfig(){

    @Test fun testGetBean() {
        val name = "placeholderConfigurer"
        Assert.assertNotNull(SpringContext.getBean(name))
    }
}