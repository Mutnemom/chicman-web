package com.chicman.utility

import com.chicman.ChicmanVaadinApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LogUtils {

    private val logger: Logger
        by lazy { LoggerFactory.getLogger(ChicmanVaadinApplication::class.java.simpleName) }

    @Suppress("unused")
    fun debug(message: String) {
        logger.debug(message)
    }

    @Suppress("unused")
    fun info(message: String) {
        logger.info(message)
    }

    @Suppress("unused")
    fun error(message: String) {
        logger.error(message)
    }

}
