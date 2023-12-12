/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.core.jdbc.user.dao

import com.github.lolstats.core.jdbc.JdbcTemplateHelper
import com.github.lolstats.core.jdbc.user.UserEntity
import com.github.lolstats.lib.util.toByteArray
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @since 2021-08-10
 */
internal interface UserEntityDao {
    fun selectById(id: UUID): UserEntity?

    fun selectByNickname(nickname: String): UserEntity?

    fun selectByEmail(email: String): UserEntity?

    fun selectByPhoneNumber(phoneNumber: String): UserEntity?

    fun insert(userEntity: UserEntity): UserEntity

    fun update(id: UUID, userEntity: UserEntity): UserEntity
}

@Repository
internal class UserEntityDaoImpl(
    override val jdbcTemplate: JdbcTemplate
) : JdbcTemplateHelper(), UserEntityDao {
    override fun selectById(id: UUID): UserEntity? {
        val sql = """
            SELECT *
            FROM `${UserEntity.TABLE}` u
            WHERE u.`${UserEntity.COL_ID}` = ?
              AND u.`${UserEntity.COL_DELETED}` = FALSE
        """.trimIndent()

        return selectOne(sql, id.toByteArray())
    }

    override fun selectByNickname(nickname: String): UserEntity? {
        val sql = """
            SELECT *
            FROM `${UserEntity.TABLE}` u
            WHERE u.`${UserEntity.COL_NICKNAME}` = ?
              AND u.`${UserEntity.COL_DELETED}` = FALSE
        """.trimIndent()

        return selectOne(sql, nickname)
    }

    override fun selectByEmail(email: String): UserEntity? {
        val sql = """
            SELECT *
            FROM `${UserEntity.TABLE}` u
            WHERE u.`${UserEntity.COL_EMAIL}` = ?
              AND u.`${UserEntity.COL_DELETED}` = FALSE
        """.trimIndent()

        return selectOne(sql, email)
    }

    override fun selectByPhoneNumber(phoneNumber: String): UserEntity? {
        val sql = """
            SELECT *
            FROM `${UserEntity.TABLE}` u
            WHERE u.`${UserEntity.COL_PHONE_NUMBER}` = ?
              AND u.`${UserEntity.COL_DELETED}` = FALSE
        """.trimIndent()

        return selectOne(sql, phoneNumber)
    }

    override fun insert(userEntity: UserEntity): UserEntity {
        val sql = """
            INSERT INTO `${UserEntity.TABLE}` (
                `${UserEntity.COL_ID}`,
                `${UserEntity.COL_NICKNAME}`,
                `${UserEntity.COL_EMAIL}`,
                `${UserEntity.COL_PHONE_NUMBER}`,
                `${UserEntity.COL_DELETED}`,
                `${UserEntity.COL_CREATED_AT}`,
                `${UserEntity.COL_UPDATED_AT}`,
                `${UserEntity.COL_VERSION}`
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        return userEntity.apply {
            @Suppress("MagicNumber")    // Not a magic number in this context
            seq = super.doInsertAndGetId(UserEntity.COL_SEQ, sql) {
                setBinaryEx(1, userEntity.id.toByteArray())
                setStringEx(2, userEntity.nickname)
                setStringEx(3, userEntity.email)
                setStringEx(4, userEntity.phoneNumber)
                setBooleanEx(5, userEntity.deleted)
                setTimestampEx(6, userEntity.registeredAt)
                setTimestampEx(7, userEntity.lastActiveAt)
                setLongEx(8, userEntity.version)
            }.key!!.toLong()
        }
    }

    override fun update(id: UUID, userEntity: UserEntity): UserEntity {
        val sql = """
            UPDATE `${UserEntity.TABLE}`
            SET `${UserEntity.COL_NICKNAME}` = ?,
                `${UserEntity.COL_EMAIL}` = ?,
                `${UserEntity.COL_PHONE_NUMBER}` = ?,
                `${UserEntity.COL_DELETED}` = ?,
                `${UserEntity.COL_CREATED_AT}` = ?,
                `${UserEntity.COL_UPDATED_AT}` = ?,
                `${UserEntity.COL_VERSION}` = ?
            WHERE `${UserEntity.COL_ID}` = ?
        """.trimIndent()

        @Suppress("MagicNumber")    // Not a magic number in this context
        val affectedRows = super.doUpdate(UserEntity.COL_ID, sql) {
            setStringEx(1, userEntity.nickname)
            setStringEx(2, userEntity.email)
            setStringEx(3, userEntity.phoneNumber)
            setBooleanEx(4, userEntity.deleted)
            setTimestampEx(5, userEntity.registeredAt)
            setTimestampEx(6, userEntity.lastActiveAt)
            setLongEx(7, userEntity.version)
            setBinaryEx(8, id.toByteArray())
        }

        return when (affectedRows) {
            1 -> userEntity
            else -> throw IncorrectResultSizeDataAccessException(1, affectedRows)
        }
    }

    private fun selectOne(sql: String, vararg args: Any): UserEntity? {
        val users = selectMany(sql, *args)

        return when (users.size) {
            0 -> null
            1 -> users[0]
            else -> throw IncorrectResultSizeDataAccessException(1, users.size)
        }
    }

    private fun selectMany(sql: String, vararg args: Any): List<UserEntity> =
        jdbcTemplate.queryForList(sql, *args).map { UserEntity.from(this, it) }
}
