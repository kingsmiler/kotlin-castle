package com.whenling.castle.core.helper

import org.junit.Assert
import org.junit.Test


class PatternsTest {

    @Test fun testEmailOK() {
        val mail = "aaa@163.com"
        Assert.assertTrue(Patterns.isEmail(mail))
    }

    @Test fun testEmailFail() {
        val mail = "aaa163.com"
        Assert.assertFalse(Patterns.isEmail(mail))
    }
}