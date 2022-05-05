package services.payments.kuda


import com.google.gson.Gson
import encryption.AESEncryptor
import encryption.RSAEncryptor
import model.KudaDevelopmentCred
import model.RsaEncryptResponse
import services.payments.kuda.model.*
import services.payments.kuda.utils.generateKudaCredientials
import services.payments.kuda.utils.getRandNumeric
import services.payments.kuda.utils.requestIdGeneratorAsLong
import services.payments.kuda.utils.syncPost


class KudaAPIService {


   private inline fun <R:Any, reified T:Any> kudaApiRequest (request: R, serviceName:String,credentials: KudaDevelopmentCred): T {
        val KUDA_BASE_URL:String = credentials.baseURL ?: ""
        val password: String = "${credentials.clientKey}-${getRandNumeric(5)}"

        val aesEncryptor = AESEncryptor()
        val requestJsonString = Gson().toJson(request)
        val encryptedData = aesEncryptor.encrypt(data = Gson().toJson(request), password= password) ?: ""
        val rsaEncryptor = RSAEncryptor(credentials)
        val encryptedPassword: RsaEncryptResponse = rsaEncryptor.rsaEncrypt(password)

        val encryptedResponse: KudaBaseRequest? = KUDA_BASE_URL.syncPost(KudaPayload(encryptedData),headers = mapOf("password" to encryptedPassword.encryptedDataAsString))

        val decryptedPassword = rsaEncryptor.decrypt(encryptedResponse?.password ?: "")
        val decryptedPayload: String = aesEncryptor.decrypt(encryptedResponse?.data ?: "", decryptedPassword.decryptedDataAsString)
        println(decryptedPayload)
        val responseJsonString = decryptedPayload
        val kudaResponse: T = Gson().fromJson(decryptedPayload, T::class.java)
        return kudaResponse
    }



    fun createKudaWallet(firstName:String, lastName:String, email:String, phoneNumber:String, trackingReference: String,credentials: KudaDevelopmentCred): KudaCreateWalletServiceResponseData {
        return kudaApiRequest(
            KudaCreateWalletServiceRequest(
            serviceType = KUDA_CREATE_WALLET_SERVICE, requestRef = requestIdGeneratorAsLong(),
            data = KudaCustomer(email= email, phoneNumber= phoneNumber,
                firstName= firstName, lastName= lastName, trackingReference= trackingReference)
        ),KUDA_CREATE_WALLET_SERVICE,credentials)
    }




    companion object{
        val KUDA_CREATE_WALLET_SERVICE:String = "ADMIN_CREATE_VIRTUAL_ACCOUNT"
        val KUDA_FUND_WALLET_SERVICE:String = "FUND_VIRTUAL_ACCOUNT"
        val KUDA_BANK_LIST_SERVICE:String = "BANK_LIST"
        val KUDA_NAME_ENQUIRY_SERVICE:String = "NAME_ENQUIRY"
        val KUDA_SEND_MONEY_FROM_MASTER_ACCOUNT_SERVICE:String = "SINGLE_FUND_TRANSFER"
        val KUDA_SEND_MONEY_FROM_VIRTUAL_WALLET_SERVICE:String = "VIRTUAL_ACCOUNT_FUND_TRANSFER"
        val KUDA_CHECK_TRANSFER_STATUS_SERVICE:String = "TRANSACTION_STATUS_QUERY"
        val KUDA_MASTER_ACCOUNT_BALANCE_ENQUIRY_SERVICE:String = "ADMIN_RETRIEVE_MAIN_ACCOUNT_BALANCE"
        val KUDA_VIRTUAL_WALLET_BALANCE_ENQUIRY_SERVICE:String = "RETRIEVE_VIRTUAL_ACCOUNT_BALANCE"
        val KUDA_WITHDRAW_VIRTUAL_ACCOUNT_SERVICE:String = "WITHDRAW_VIRTUAL_ACCOUNT"
        val ADMIN_VIRTUAL_ACCOUNT_FILTERED_TRANSACTIONS_SERVICE:String = "ADMIN_VIRTUAL_ACCOUNT_FILTERED_TRANSACTIONS"
    }

}


fun main(){
    val privateKeyXML = ""
    val publicKeyXML = ""
    val clientKey = ""
    val baseURL = ""
    val credientials = generateKudaCredientials(clientKey,baseURL,publicKeyXML,privateKeyXML)
    val apiService = KudaAPIService()
   val response = apiService.createKudaWallet("","","","","",credientials)
    println(response)
}