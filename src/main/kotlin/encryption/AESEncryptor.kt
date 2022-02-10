package encryption

import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class AESEncryptor {
    private val salt = "randomsalt"
    private val keyLength:Int = 256
    private val iterationCount:Int = 1000

    private val algorithm = "AES/CBC/PKCS5Padding"

    private fun generateKey( pass: String): SecretKey? {
        return try {
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec: KeySpec = PBEKeySpec(pass.toCharArray(), salt.toByteArray(), iterationCount, keyLength * 8)
            SecretKeySpec(factory.generateSecret(spec).encoded, "AES")
        } catch (e: NoSuchAlgorithmException) {
            null
        } catch (e: InvalidKeySpecException) {
            null
        }
    }

    fun concat(vararg arrays: ByteArray): ByteArray? {
        // Determine the length of the result array
        var totalLength = 0
        for (i in arrays.indices) {
            totalLength += arrays[i].size
        }
        // create the result array
        val result = ByteArray(totalLength)
        // copy the source arrays into the result array
        var currentIndex = 0
        for (i in arrays.indices) {
            System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].size)
            currentIndex += arrays[i].size
        }
        return result
    }

    fun encrypt(
        data: String, password: String,
    ): String? {
        val derivedKeyBytes = generateKey(password)?.encoded
        val keyBytes = Arrays.copyOfRange(derivedKeyBytes,0,32)
        val ivBytes = Arrays.copyOfRange(keyBytes,0,16)

        val key = SecretKeySpec(keyBytes,0,keyBytes.size,"AES")
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(ivBytes))

        val encryptedBytes = cipher.update(data.toByteArray())
        val cipherTextBytes = cipher.doFinal()
        return Base64.getEncoder().encodeToString(concat(encryptedBytes,cipherTextBytes))
    }

    fun decrypt(
        data: String, password: String,
    ): String {
        val derivedKeyBytes = generateKey(password)?.encoded
        val keyBytes = Arrays.copyOfRange(derivedKeyBytes,0,32)
        val ivBytes = Arrays.copyOfRange(keyBytes,0,16)
        val key = SecretKeySpec(keyBytes,0,keyBytes.size,"AES")

        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(ivBytes))

        return String(cipher.doFinal(Base64.getDecoder().decode(data)))
    }
}
