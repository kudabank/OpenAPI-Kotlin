package services.payments.kuda.utils

import com.google.gson.Gson
import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.ext.url
import okhttp3.Headers
import okhttp3.Response

object NetworkUtils {
    //R-Request
    // T-Response
    inline fun <reified T : Any, R : Any> syncPOSTRequests(json: R, url: String, header: Map<String,String>): T? {
        //POST with form body
        val patientClient = defaultHttpClient.fork {
            readTimeout = 60000
            connectTimeout = 60000
            writeTimeout = 60000
        }
        val response: Response = httpPost(client = patientClient) {
            header {
                "Content-Type" to "application/json"
                header.forEach{
                    it.key to it.value
                }
            }
            url("$url")
            body {
                json(
                    """
${Gson().toJson(json)}
"""
                )
            }
        }
        println("response <> $response")

        println(Gson().toJson(json))
        response.use {
            return Gson().fromJson(it.asString(), T::class.java)
        }
    }

}


inline fun <R : Any, reified T : Any> String.syncPost(request: R, headers: Map<String,String>): T? {
    return NetworkUtils.syncPOSTRequests(request, this, headers)
}


