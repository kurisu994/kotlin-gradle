package cn.hutao.buer.utils

import org.apache.commons.lang3.RandomUtils
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


/**
 * userid: 77 集团3
 * userID: 80 集团4
 */
class AESUtils {
    @Throws(Exception::class)
    fun encrypt(src: ByteArray?, key: String): ByteArray {
        val cipher = Cipher.getInstance("AES")
        val securekey = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(1, securekey)
        return cipher.doFinal(src)
    }

    fun encrypt(data: String, key: String): String {
        try {
            return byte2hex(encrypt(data.toByteArray(), key))
        } catch (var3: java.lang.Exception) {
            throw RuntimeException("加密失败", var3)
        }
    }

    fun getToken(id: Long?, name: String?, group: Long?): String {
        val userId = id ?: 67L
        val userName = name ?: "超级管理员1"
        val groupId = group ?: 2L
        val AES_KEY = if (groupId == 2L || groupId == 1L) {
            AES_KEY_1
        } else {
            AES_KEY_2
        }

        val info: String = "${RandomUtils.nextInt()}$TICKET_SEPARATOR$userId$TICKET_SEPARATOR" +
                "$userName$TICKET_SEPARATOR$groupId$TICKET_SEPARATOR${System.currentTimeMillis()}"
        val cookieStr = encrypt(info, AES_KEY)
        return cookieStr
    }

    private fun byte2hex(b: ByteArray): String {
        val hs = StringBuilder()
        var stmp = ""

        for (value in b) {
            stmp = Integer.toHexString(value.toInt() and 255)
            if (stmp.length == 1) {
                hs.append("0").append(stmp)
            } else {
                hs.append(stmp)
            }
        }

        return hs.toString().uppercase(Locale.getDefault())
    }

    companion object {
        const val AES_KEY_2 = "bN7EVjFMJPOmWDcB"// 非银川集团
        const val AES_KEY_1 = "bN7DVjFMJPcoWDcQ" // 银川集团
        const val TICKET_SEPARATOR: String = "##"
    }
}


fun main() {
    val userId = 105L
    val userName = "曾凯"
    val groupId = 2L

    val AES_KEY = if (groupId == 2L || groupId == 1L) {
        AESUtils.AES_KEY_1
    } else {
        AESUtils.AES_KEY_2
    }

    val info: String = "${RandomUtils.nextInt()}${AESUtils.TICKET_SEPARATOR}$userId" +
            "${AESUtils.TICKET_SEPARATOR}$userName${AESUtils.TICKET_SEPARATOR}$groupId" +
            "${AESUtils.TICKET_SEPARATOR}${(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30))}"
    val cookieStr = AESUtils().encrypt(info, AES_KEY)
    println(cookieStr)
}