package services.payments.kuda.utils

import model.KudaDevelopmentCred
import java.util.regex.Matcher
import java.util.regex.Pattern

fun getRandNumeric(len: Int): String {
    val sb = StringBuffer(len)
    /*  55 */for (i in 0 until len) { /*  56 */
        val ndx = (Math.random() * "1234567890".length).toInt()
        /*  57 */sb.append("1234567890"[ndx])
    }
    /*  59 */return sb.toString()
}

fun requestIdGeneratorAsLong(): Long {
    return Math.floor(Math.random() * 9_000_000_000L).toLong() + 1_000_000_000L
}

private val xmlExp = Pattern.compile("\\s*<RSAKeyValue>([<>\\/\\+=\\w\\s]+)</RSAKeyValue>\\s*")
private val xmlTagExp = Pattern.compile("<(.+?)>\\s*([^<]+?)\\s*</")

fun generateKudaCredientials(clientKey:String,baseUrl:String,publicKey:String,privateKey:String):KudaDevelopmentCred{
    val devCreds = KudaDevelopmentCred(clientKey,"","","","",baseUrl)
    val xmlM: Matcher = xmlExp.matcher(publicKey)
    if (!xmlM.find()) {
        throw Exception("Invalid public Key")
    }
    val tagM: Matcher = xmlTagExp.matcher(xmlM.group(1))
    while (tagM.find()) {
        val tag = tagM.group(1)
        val b64 = tagM.group(2)
        when (tag) {
            "Modulus" -> devCreds.publicModulusElem = b64
            "Exponent" -> devCreds.publicExponentElem = b64
        }
    }

    val xmlPrivate: Matcher = xmlExp.matcher(privateKey)
    if (!xmlPrivate.find()) {
        throw Exception("Invalid private Key")
    }
    val tagPrivate: Matcher = xmlTagExp.matcher(xmlPrivate.group(1))
    while (tagPrivate.find()) {
        val tag = tagPrivate.group(1)
        val b64 = tagPrivate.group(2)
        when (tag) {
            "Modulus" -> devCreds.privateModulusElem = b64
            "D" -> devCreds.privateDElem = b64
        }
    }
    return devCreds
}

