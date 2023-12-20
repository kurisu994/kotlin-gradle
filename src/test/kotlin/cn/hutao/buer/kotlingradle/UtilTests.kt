package cn.hutao.buer.kotlingradle

import cn.hutao.buer.common.DemoKt
import org.junit.jupiter.api.Test

/**
 * 工具方法测试类
 * @className : UtilTests
 * @description : 工具方法测试类
 * @author : kurisu
 * @date : 2023-12-20 23:56
 * @version : 1.0
 */

class UtilTests {
    @Test
    fun demoKtTest() {
        val demoKt = DemoKt()
        demoKt.thredTest()
        demoKt.corTest()
    }

}