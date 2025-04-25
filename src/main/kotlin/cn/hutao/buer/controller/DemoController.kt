package cn.hutao.buer.controller

import cn.hutao.buer.utils.AESUtils
import org.springframework.http.ResponseCookie
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @author : kurisu
 * @className : TestController
 * @description :
 * @date: 2020-09-08 14:22
 */
@RestController
@Validated
@RequestMapping("/demo")
@CrossOrigin
class DemoController {
    /**
     * [集团1](local.feewee.cn:8080/demo/test?userId=65&userName=集团1管理员&groupId=1)
     *
     * [集团2](local.feewee.cn:8080/demo/test?userId=67&userName=集团2管理员&groupId=2)
     *
     * [集团3](local.feewee.cn:8080/demo/test?userId=77&userName=集团3管理员&groupId=3)
     *
     * [集团4](local.feewee.cn:8080/demo/test?userId=80&userName=集团4管理员&groupId=4)
     *
     * [集团5](local.feewee.cn:8080/demo/test?userId=82&userName=集团5管理员&groupId=5)
     *
     * [集团6](local.feewee.cn:8080/demo/test?userId=61&userName=集团6管理员&groupId=6)
     *
     * @param response
     * @param userId
     * @param userName
     * @param groupId
     * @throws IOException
     */
    @GetMapping("/test")
    @Throws(IOException::class)
    fun loginTest(response: ServerHttpResponse, userId: Long?, userName: String?, groupId: Long) {
        val cookieStr = AESUtils().getToken(userId, userName, groupId)
        val cookie = ResponseCookie.from("fwtk", cookieStr)
            .domain("feewee.cn")
            .maxAge(TimeUnit.DAYS.toSeconds(30L))
            .path("/")
            .build()

        response.addCookie(cookie)
        val newUrl = if (groupId <= 2) "https://admin.feewee.cn" else "https://erp.feewee.cn"
        response.statusCode = org.springframework.http.HttpStatus.SEE_OTHER
        response.headers.location = java.net.URI.create(newUrl)
    }
}