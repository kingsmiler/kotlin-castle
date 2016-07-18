package com.whenling.castle.core.helper

import java.util.regex.Pattern

object Patterns {

    @JvmField val REGEX_USERNAME = "^$|^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{4,16}$"
    val REGEX_MAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"
    val REGEX_MOBILE = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$"

    val PATTERN_USERNAME = Pattern.compile(REGEX_USERNAME)
    val PATTERN_MAIL = Pattern.compile(REGEX_MAIL)
    val PATTENR_MOBILE = Pattern.compile(REGEX_MOBILE)

    fun isUsername(username: String): Boolean {
        return PATTERN_USERNAME.matcher(username).matches()
    }

    fun isEmail(email: String): Boolean {
        return PATTERN_MAIL.matcher(email).matches()
    }

    fun isMobile(mobile: String): Boolean {
        return PATTENR_MOBILE.matcher(mobile).matches()
    }
}
