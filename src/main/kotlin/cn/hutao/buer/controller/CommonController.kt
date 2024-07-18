package cn.hutao.buer.controller

import cn.hutao.buer.extend.logInfo
import kotlinx.coroutines.*
import kotlinx.coroutines.future.future
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author : kurisu
 * @className : CommonController
 * @description :
 * @date: 2021-09-23 15:39
 */
@Validated
@RestController
@RequestMapping("/common/message")
class CommonController {
    @GetMapping("/send")
    fun manualSend(sceneToken: Long?): String {
        return sceneToken?.toString() ?: "null"
    }

    @GetMapping("/demo")
    suspend fun manualSend2(): String {
        val result = coroutineScope {
            async {
                // 耗时操作
                delay(5000)
                "Hello, Coroutines!"
            }
        }

        return result.await()
    }

    @GetMapping("/demo2")
    fun save(): String {
        logInfo("调用挂起函数测试方法")
        val fet = CoroutineScope(Dispatchers.IO).future {
            async {
                // 耗时操作
                delay(3000)
                "Hello, Coroutines!"
            }.await()
        }
        val fet2 = CoroutineScope(Dispatchers.IO).future {
            async {
                // 耗时操作
                delay(5000)
                "Hello, Coroutines!"
            }.await()
        }

        fet2.join()
        val s2 = fet.join()
        return s2
    }
}