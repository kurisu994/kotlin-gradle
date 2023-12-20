package cn.hutao.buer.common

import cn.hutao.buer.enums.DemoEnum
import cn.hutao.buer.extend.logInfo
import cn.hutao.buer.pojo.DemoPojo
import cn.hutool.core.thread.ExecutorBuilder
import cn.hutool.core.thread.NamedThreadFactory
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService

/**
 *
 * @className : DemoKt
 * @description :
 * @author : kurisu
 * @date : 2023-12-20 22:07
 * @version :
 */

class DemoKt {
    private val max = 10_000
    private val executor: ExecutorService = ExecutorBuilder.create()
        .setCorePoolSize(50)
        .setMaxPoolSize(500)
        .setThreadFactory(NamedThreadFactory("demoKt-custom-", false))
        .setAllowCoreThreadTimeOut(true)
        .useArrayBlockingQueue(512000)
        .build()

    fun corTest(): List<DemoPojo> {
        val paramList: MutableList<Long> = MutableList(max) { it + 1L }
        val start = System.currentTimeMillis()
        val list = runBlocking(Dispatchers.IO) {
            val amap = paramList.map {
                async {
                    delay(20L)
                    createBeans(it)
                }
            }
            amap.awaitAll()
        }
        val end = System.currentTimeMillis()
        val dif = end - start
        logInfo("corTest 最终列表长度：${list.size} 耗时：$dif ms")
        return list
    }

    fun thredTest(): List<DemoPojo> {
        val paramList: MutableList<Long> = MutableList(max) { it + 1L }
        val list: MutableList<DemoPojo> = Collections.synchronizedList(ArrayList())
        val start = System.currentTimeMillis()
        val amap = paramList.map {
            CompletableFuture.runAsync({
                Thread.sleep(20L)
                list.add(createBeans(it))
            }, executor)
        }
        CompletableFuture.allOf(*amap.toTypedArray()).join()
        val end = System.currentTimeMillis()
        val dif = end - start
        logInfo("Thread 最终列表长度：${list.size} 耗时：$dif ms")
        return list
    }

    private fun createBeans(id: Long): DemoPojo {
        return DemoPojo(
            id = id,
            memberId = 1L,
            templateCode = "xx",
            title = "title",
            content = "",
            keywords = "xx",
            remark = "remark",
            pagePath = "path",
            pageParams = "",
            readz = false,
            sendTime = Date(),
            frequency = 0,
            state = DemoEnum.A,
            yn = true,
            createTime = Date()
        )
    }
}