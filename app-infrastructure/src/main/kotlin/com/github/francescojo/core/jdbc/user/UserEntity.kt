/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.francescojo.core.jdbc.user

import com.github.francescojo.core.domain.user.User
import com.github.francescojo.core.jdbc.JdbcTemplateHelper
import com.github.francescojo.lib.util.decrypt
import com.github.francescojo.lib.util.encrypt
import com.github.francescojo.lib.util.toUUID
import com.github.francescojo.lib.util.toUUID
import java.security.SecureRandom
import java.time.Instant
import java.util.*

/**
 * [equals] and [hashCode] implementation is inspired by the article as follows:
 * [Martin Fowler's blog: EvansClassification](https://martinfowler.com/bliki/EvansClassification.html)
 *
 * @since 2021-08-10
 */
internal class UserEntity(
    val id: UUID,
    var nickname: String,
    var email: String,
    var phoneNumber: String,
    var zipCode: String?,
    var streetAdr: String?,
    var detailAdr: String?,
    var registeredAt: Instant,
    var lastActiveAt: Instant,
    var deleted: Boolean,
) {
    var seq: Long? = null

    var version: Long = 0L

    var salt: ByteArray = ByteArray(SALT_LENGTH).apply {
        SecureRandom().nextBytes(this)
    }

    fun toUser(): User = User.create(
        id = this.id,
        nickname = this.nickname,
        email = this.email.decrypt(salt),
        phoneNumber = this.phoneNumber.decrypt(salt),
        zipCode = this.zipCode?.decrypt(salt),
        streetAdr = this.streetAdr?.decrypt(salt),
        detailAdr = this.detailAdr?.decrypt(salt),
        registeredAt = this.registeredAt,
        lastActiveAt = this.lastActiveAt,
        deleted = this.deleted
    )

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is UserEntity -> false
        else -> this.seq == other.seq
    }

    override fun hashCode(): Int = Objects.hash(this.seq)

    companion object {
        const val TABLE = "users"

        const val COL_SEQ = "seq"
        const val COL_ID = "id"
        const val COL_NICKNAME = "nickname"
        const val COL_EMAIL = "email"
        const val COL_PHONE_NUMBER = "phone_number"
        const val COL_ZIP_CODE = "zip_code"
        const val COL_STREET_ADR = "street_adr"
        const val COL_DETAIL_ADR = "detail_adr"
        const val COL_DELETED = "deleted"
        const val COL_CREATED_AT = "created_at"
        const val COL_UPDATED_AT = "updated_at"
        const val COL_VERSION = "version"
        const val COL_SALT = "salt"
        private const val SALT_LENGTH = 16
        fun from(user: User): UserEntity = with(user) {
            val newSalt = ByteArray(SALT_LENGTH).apply { SecureRandom().nextBytes(this) }
            UserEntity(
                id = id,
                nickname = nickname,
                email = email.encrypt(newSalt),
                phoneNumber = phoneNumber.encrypt(newSalt),
                zipCode = address?.zipCode?.encrypt(newSalt),
                streetAdr = address?.streetAdr?.encrypt(newSalt),
                detailAdr = address?.detailAdr?.encrypt(newSalt),
                registeredAt = registeredAt,
                lastActiveAt = lastActiveAt,
                deleted = deleted
            ).apply {
                this.salt = newSalt
            }
        }
        fun from(
            deserialisationContext: JdbcTemplateHelper,
            map: Map<String, Any?>,
            prefix: String = ""
        ) = with(deserialisationContext) {
            UserEntity(
                id = (map[prefix + COL_ID] as ByteArray).toUUID(),
                nickname = map[prefix + COL_NICKNAME] as String,
                email = (map[prefix + COL_EMAIL] as String).encrypt(map[prefix + COL_SALT] as ByteArray),
                phoneNumber = (map[prefix + COL_PHONE_NUMBER] as String).encrypt(map[prefix + COL_SALT] as ByteArray),
                zipCode = (map[prefix + COL_ZIP_CODE] as String?)?.encrypt(map[prefix + COL_SALT] as ByteArray),
                streetAdr = (map[prefix + COL_STREET_ADR] as String?)?.encrypt(map[prefix + COL_SALT] as ByteArray),
                detailAdr = (map[prefix + COL_DETAIL_ADR] as String?)?.encrypt(map[prefix + COL_SALT] as ByteArray),
                registeredAt = map[prefix + COL_CREATED_AT]!!.coerceToInstant(),
                lastActiveAt = map[prefix + COL_UPDATED_AT]!!.coerceToInstant(),
                deleted = map[prefix + COL_DELETED] as Boolean
            ).apply {
                this.seq = map[prefix + COL_SEQ] as Long
                this.version = map[prefix + COL_VERSION] as Long
                this.salt = map[prefix + COL_SALT] as ByteArray
            }
        }
    }
}
