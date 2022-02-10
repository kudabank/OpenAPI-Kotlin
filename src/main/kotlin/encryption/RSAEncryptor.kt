package encryption



import extentions.toRsaBigInt
import model.KudaDevelopmentCred
import model.RsaDecryptResponse
import model.RsaEncryptResponse
import org.apache.commons.codec.binary.Base64
import java.security.KeyFactory
import java.security.spec.RSAPrivateKeySpec
import java.security.spec.RSAPublicKeySpec
import javax.crypto.Cipher


class RSAEncryptor (val credentials: KudaDevelopmentCred){



    fun rsaEncrypt(input: String): RsaEncryptResponse {
        val factory = KeyFactory.getInstance("RSA")
        val exponent = credentials.publicExponentElem.toRsaBigInt()
        val modulus = credentials.publicModulusElem.toRsaBigInt()
        val publicKey = factory.generatePublic(RSAPublicKeySpec(modulus, exponent))
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedByteArray = cipher.doFinal(input.toByteArray(charset("UTF-8")))
        return RsaEncryptResponse(encryptedByteArray,Base64.encodeBase64String(encryptedByteArray))
    }

    fun decrypt(password:String): RsaDecryptResponse {
        val encryptedData = java.util.Base64.getDecoder().decode(password)
        val factory = KeyFactory.getInstance("RSA")
        val modulus = credentials.privateModulusElem.toRsaBigInt()
        val d = credentials.privateDElem.toRsaBigInt()
        val privateKey = factory.generatePrivate(RSAPrivateKeySpec(modulus, d))
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decrypted = cipher.doFinal(encryptedData)
        return RsaDecryptResponse(decrypted, String(decrypted))
    }


}






