package cn.hutao.buer.pojo

import cn.hutao.buer.enums.DemoEnum
import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.FieldStrategy
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import java.util.*

/**
 * 样本数据类
 * @className : DemoPojo
 * @description : 样本数据类
 * @author : kurisu
 * @date : 2023-12-20 23:46
 * @version : 1.0
 */

data class DemoPojo(
    @TableId(type = IdType.ASSIGN_ID) var id: Long?,
    var memberId: Long?,
    var templateCode: String?,
    var title: String?,
    var content: String?,
    var keywords: String?,
    var remark: String?,
    var pagePath: String?,
    var pageParams: String?,
    var readz: Boolean?,
    var sendTime: Date?,
    var frequency: Int?,
    var state: DemoEnum?,
    var yn: Boolean?,
    @TableField(insertStrategy = FieldStrategy.NOT_NULL, fill = FieldFill.INSERT)
    var createTime: Date?
)
