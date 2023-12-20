package cn.hutao.buer.enums

import com.baomidou.mybatisplus.annotation.IEnum
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author : kurisu
 * @className : DemoEnum
 * @description : demo枚举
 * @date: 2020-08-11 17:37
 */
enum class DemoEnum(
    /**
     * 值
     */
    private val value: Int,
    /**
     * 名称
     */
    private val desc: String
) : IEnum<Int> {
    A(1, "A"),
    B(2, "B"),
    ;

    /**
     * 获取值
     *
     * @return 值
     */
    @JsonValue
    override fun getValue(): Int {
        return value
    }

    companion object {
        /**
         * 根据枚举值获取枚举对象
         */
        @JsonCreator
        fun ofValue(value: Int): DemoEnum? {
            for (stateEnum in entries) {
                if (stateEnum.value == value) {
                    return stateEnum
                }
            }
            return null
        }

        /**
         * 获取描述
         *
         * @return 值
         */
        @JsonCreator
        fun getNameByVale(value: Int): String {
            for (stateEnum in entries) {
                if (stateEnum.value == value) {
                    return stateEnum.name
                }
            }
            return ""
        }
    }
}