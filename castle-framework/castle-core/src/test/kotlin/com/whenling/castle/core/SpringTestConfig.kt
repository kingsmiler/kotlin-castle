package com.whenling.castle.core

import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner


@RunWith(SpringJUnit4ClassRunner::class)
@ContextHierarchy(ContextConfiguration(classes = arrayOf(ConfigConfigBean::class)))
open class SpringTestConfig {
}