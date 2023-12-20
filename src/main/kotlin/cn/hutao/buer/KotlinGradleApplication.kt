package cn.hutao.buer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinGradleApplication

fun main(args: Array<String>) {
    runApplication<KotlinGradleApplication>(*args)
}
