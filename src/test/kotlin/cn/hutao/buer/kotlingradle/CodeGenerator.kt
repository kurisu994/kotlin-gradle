package cn.hutao.buer.kotlingradle

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.generator.FastAutoGenerator
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine
import com.baomidou.mybatisplus.generator.fill.Column


/**
 * 代码生成器
 *
 * @author : kurisu
 * @version : 2.0
 * @desc : 代码生成器
 * @date : 2024-08-19 11:47
 */
class CodeGenerator

private const val url =
    "jdbc:mysql://172.26.154.169:3301/fw_assess?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&autoReconnect=true&rewriteBatchedStatements=true&allowMultiQueries=true&useServerPrepStmts=true&cachePrepStmts=true&tinyInt1isBit=true"
private const val userName = "test"
private const val password = "mysql@pwd123"
private const val driverClassName = "com.mysql.cj.jdbc.Driver"
private const val useKt = false

private const val author = "Kurisu"
private const val parentPackageName = "cn.fw.assess"
private val projectDir = System.getProperty("user.dir")
private const val tableName = ""


fun main() {
    FastAutoGenerator.create(
        url,
        userName,
        password
    ).globalConfig { builder ->
        builder.author(author)
        builder.commentDate("yyyy-MM-dd")
        builder.disableOpenDir()
        if (useKt) {
            builder.enableKotlin()
            builder.outputDir("$projectDir/opt/src/main/kotlin")
        } else {
            builder.outputDir("$projectDir/opt/src/main/java")
        }
    }.packageConfig { builder ->
        builder.parent(parentPackageName)
            .entity("domain.db")
            .mapper("dao.mapper")
            .service("service.data")
            .serviceImpl("service.data.impl")
            .controller("controller")
            .xml("resources.mapper")
    }.strategyConfig { builder ->
        builder
            .entityBuilder()
            .enableLombok()
            .addTableFills(Column("create_time", FieldFill.INSERT), Column("update_time", FieldFill.INSERT_UPDATE))
        if (!tableName.isNullOrEmpty()) {
            builder.addInclude(tableName)
        }
    }.templateEngine(VelocityTemplateEngine()).execute()
}