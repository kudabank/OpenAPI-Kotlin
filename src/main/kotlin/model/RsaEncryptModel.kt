package model

data class RsaEncryptResponse (
    val encryptedDataAsByteArray: ByteArray,
    val encryptedDataAsString:String
)

data class RsaDecryptResponse   (
    val decryptedDataAsByteArray:ByteArray,
    val decryptedDataAsString:String
)