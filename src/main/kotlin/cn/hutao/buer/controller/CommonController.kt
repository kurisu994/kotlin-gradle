package cn.hutao.buer.controller

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
    fun manualSend(sceneToken: Long?): String {
        return sceneToken?.toString() ?: "null"
    }
}