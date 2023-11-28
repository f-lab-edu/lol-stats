package com.github.francescojo.core

import io.github.cdimascio.dotenv.Dotenv
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

fun String.encrypt(salt: ByteArray) = Encryption(salt).encrypt(this)
fun String.decrypt(salt: ByteArray) = Encryption(salt).decrypt(this)
internal class Encryption(salt: ByteArray)  {
    private val secretKey: String =  Dotenv.load().get("SECRET_KEY")
    private val secretKeySpec: SecretKeySpec
    private val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
    private val iterationCount = 65536
    private val keyLength = 256
    init {
        val spec = PBEKeySpec(secretKey.toCharArray(), salt, iterationCount, keyLength)
        val tmp = factory.generateSecret(spec)
        secretKeySpec = SecretKeySpec(tmp.encoded, "AES")
    }
    fun getAlgorithm(): String? {
        return "AES/CBC/PKCS5PADDING"
    }
    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val iv = ByteArray(cipher.blockSize)
        SecureRandom().nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)
        val encrypted = cipher.doFinal(data.toByteArray())

        return Base64.getEncoder().encodeToString(iv + encrypted)
    }

    fun decrypt(encryptedData: String): String {
        val encrypted = Base64.getDecoder().decode(encryptedData.toByteArray())
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val ivSpec = IvParameterSpec(encrypted.take(cipher.blockSize).toByteArray())
        val cipherText = encrypted.drop(cipher.blockSize).toByteArray()

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)
        return String(cipher.doFinal(cipherText))
    }
}