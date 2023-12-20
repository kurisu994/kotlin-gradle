package cn.hutao.buer.extend

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 日志拓展
 *
 * @author : kurisu
 * @version : 1.0
 * @desc : 日志拓展
 * @date : 2023-12-16 15:46
 */

fun <T : Any> T.logger(): Logger {
    return LoggerFactory.getLogger(this.javaClass)
}

fun <T : Any> T.logTrace(msg: String) {
    logger().trace(msg)
}

fun <T : Any> T.logTrace(msg: String, vararg var2: Any?) {
    logger().trace(msg, var2)
}

fun <T : Any> T.logDebug(msg: String) {
    logger().debug(msg)
}

fun <T : Any> T.logDebug(msg: String, vararg var2: Any?) {
    logger().debug(msg, var2)
}

fun <T : Any> T.logInfo(msg: String) {
    logger().info(msg)
}

fun <T : Any> T.logInfo(msg: String, vararg var2: Any?) {
    logger().info(msg, var2)
}

fun <T : Any> T.logWarn(msg: String) {
    logger().warn(msg)
}

fun <T : Any> T.logWarn(msg: String, vararg var2: Any?) {
    logger().warn(msg, var2)
}

fun <T : Any> T.logError(msg: String) {
    logger().error(msg)
}

fun <T : Any> T.logError(e: Throwable) {
    logger().error("", e)
}

fun <T : Any> T.logError(msg: String, vararg var2: Any?) {
    logger().error(msg, var2)
}

fun <T : Any> T.logError(msg: String, e: Throwable) {
    logger().error(msg, e)
}