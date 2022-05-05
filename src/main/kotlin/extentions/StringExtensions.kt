package extentions

import org.apache.commons.codec.binary.Base64
import java.math.BigInteger

fun String.toRsaBigInt(): BigInteger {
    return  BigInteger(1, Base64.decodeBase64(this.trim()))
}