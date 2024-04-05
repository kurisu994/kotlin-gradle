package cn.hutao.buer.controller

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.delay
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author : kurisu
 * @className : MessageCenterController
 * @description :
 * @date: 2021-09-23 15:39
 */
@Validated
@RestController
@RequestMapping("/common/message")
class CommonController() {
    @GetMapping("/send")
    suspend fun manualSend(sceneToken: Long?): String {
        delay(2000L)
        return sceneToken?.toString() ?: "null"
    }

    @GetMapping("/demo")
    suspend fun manualSend2(sceneToken: Long?): String {
        val result = coroutineScope {
            async {
                // 耗时操作
                delay(5000)
                "Hello, Coroutines!"
            }
        }

        return result.await()
    }
}