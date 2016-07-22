package com.whenling.castle.core.helper

import java.util.regex.Pattern

object Patterns {

    @JvmField
    val REGEX_USERNAME = "^$|^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{4,16}$"

    @JvmField
    val REGEX_MAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"

    @JvmField
    val REGEX_MOBILE = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$"

    @JvmField
    val PATTERN_USERNAME: Pattern = Pattern.compile(REGEX_USERNAME)

    @JvmField
    val PATTERN_MAIL: Pattern = Pattern.compile(REGEX_MAIL)

    @JvmField
    val PATTERN_MOBILE: Pattern = Pattern.compile(REGEX_MOBILE)

    @JvmStatic
    fun isUsername(username: String): Boolean {
        return PATTERN_USERNAME.matcher(username).matches()
    }

    @JvmStatic
    fun isEmail(email: String): Boolean {
        return PATTERN_MAIL.matcher(email).matches()
    }

    @JvmStatic
    fun isMobile(mobile: String): Boolean {
        return PATTERN_MOBILE.matcher(mobile).matches()
    }
}
