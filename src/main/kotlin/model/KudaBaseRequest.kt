package services.payments.kuda.model

data class KudaBaseRequest (
    val data:String,
    val password:String
)



data class KudaPayload(val data:String)


data class KudaCreateWalletServiceRequest (
    var serviceType:String = "",
    var requestRef:Long = 0,
    var data:KudaCustomer,
)
data class KudaCustomer(
    val firstName:String, val lastName:String,
    val email:String, val phoneNumber:String,
    var trackingReference: String
)
data class KudaCreateWalletServiceResponse (
    val data:String,
    val password:String
)
data class KudaCreateWalletServiceResponseData (
    val Status:Boolean,
    val Message:String,
    val Data:Data,
)
data class Data(
    val AccountNumber: String
)
data class KudaCreateWalletExtraData(
    val trackingReference:String, val phoneNumber:String,
)



data class KudaFundVirtualAccountRequest (
    var serviceType:String = "",
    var requestRef:Long = 0,
    var data:KudaFundData,
)
data class KudaFundData(
    val amount:String,
    val narration:String,
    var trackingReference: String
)


data class KudaGetBankListRequest (
    var serviceType:String = "",
    var requestRef:Long = 0
)
data class KudaGetBankListResponse(
    val Status:Boolean,
    val Message:String,
    val Data:KudaGetBankListResponseData,
)
data class KudaGetBankListResponseData(
    val banks: List<KudaBankListData>
)
data class KudaBankListData(
    val bankCode: String,
    val bankName: String
)


data class KudaNameEnquiryRequest (
    var serviceType:String = "",
    var requestRef:Long = 0,
    var data: KudaNameEnquiryRequestData
)
data class KudaNameEnquiryRequestData (
    var beneficiaryAccountNumber:String = "",
    var beneficiaryBankCode:String = "",
    var SenderTrackingReference:String = "",
    var isRequestFromVirtualAccount:String = ""
)
enum class IsRequestFromVirtualAccount(val value:String){
    TRUE("True"),FALSE("False")
}
data class KudaNameEnquiryResponse(
    val Status:Boolean,
    val Message:String,
    val Data:KudaNameEnquiryResponseData,
)
data class KudaNameEnquiryResponseData(
    val BeneficiaryAccountNumber: String,
    val BeneficiaryName: String,
    var SenderAccountNumber: String = "",
    var SenderName: String = "",
    var BeneficiaryCustomerID: Int = 0,
    val BeneficiaryBankCode: String,
    var NameEnquiryID: Int = 0,
    var ResponseCode: String = "00",
    var TransferCharge: Int = 0,
    var SessionID: String = "",
)


data class KudaSendMoneyFormMasterAccountRequest (
    var serviceType:String = "",
    var requestRef:Long = 0,
    var data: KudaSendMoneyFormMasterAccountRequestData
)
data class KudaSendMoneyFormMasterAccountRequestData (
    var beneficiarybankCode:String = "",
    var beneficiaryAccount:String = "",
    var beneficiaryName:String = "",
    var amount:String = "",
    var narration:String = "",
    var nameEnquirySessionID:String = "",
    var trackingReference:String = "",
    var senderName:String = ""
)

data class KudaSendMoneyFormMasterAccountResponse(
    val RequestReference:String,
    val TransactionReference: String,
    val ResponseCode:String,
    val Status:Boolean,
    val Message:String,
    var Data:String = "",
)


data class KudaSendMoneyFormVirtualWalletRequest (
    var serviceType:String = "",
    var requestRef:Long = 0,
    var data: KudaSendMoneyFormVirtualWalletRequestData
)
data class KudaSendMoneyFormVirtualWalletRequestData (
    var trackingReference:String = "",
    var beneficiaryAccount:String = "",
    var amount:String = "",
    var narration:String = "",
    var beneficiaryBankCode:String = "",
    var beneficiaryName:String = "",
    var senderName:String = "",
    var nameEnquiryId:String = ""
)

data class KudaSendMoneyFormVirtualWalletResponse(
    val TransactionReference: String,
    val ResponseCode:String,
    val Status:Boolean,
    val Message:String,
    var Data:String = "",
)


data class KudaCheckTransferStatusRequest (
    var serviceType:String = "",
    var requestRef:Long = 0,
    var data: KudaCheckTransferStatusRequestData
)
data class KudaCheckTransferStatusRequestData (
    var isThirdPartyBankTransfer:Boolean = false,
    var transactionRequestReference:String = ""
)
enum class IsThirdPartyBankTransfer(val value:Boolean){
    TRUE(true),FALSE(false)
}
data class KudaCheckTransferStatusResponse(
    val ResponseCode:String,
    val Status:Boolean,
    val Message:String,
    var Data:String = "",
)


data class KudaMasterAccountBalanceEnquiryRequest (
    var serviceType:String = "",
    var data: String = ""
)
data class KudaMasterAccountBalanceEnquiryResponse(
    val ResponseCode:String,
    val Status:Boolean,
    val Message:String,
    var Data:KudaMasterAccountBalanceEnquiryResponseData,
)
data class KudaMasterAccountBalanceEnquiryResponseData(
    val LedgerBalance: Double,
    val AvailableBalance: Double,
    val WithdrawableBalance: Double,
)


data class KudaVirtualWalletBalanceEnquiryRequest (
    var serviceType:String = "",
    var requestRef:Long = 0,
    var data: KudaVirtualWalletBalanceEnquiryRequestData
)
data class KudaVirtualWalletBalanceEnquiryRequestData(
    val trackingReference: String = ""
)
data class KudaVirtualWalletBalanceEnquiryResponse(
    val ResponseCode:String,
    val Status:Boolean,
    val Message:String,
    var Data:KudaVirtualWalletBalanceEnquiryResponseData,
)
data class KudaVirtualWalletBalanceEnquiryResponseData(
    val LedgerBalance: Double,
    val AvailableBalance: Double,
    val WithdrawableBalance: Double,
)


data class WithdrawFromVirtualAccountRequest(
    val `data`: WithdrawData,
    val requestRef: String,
    val serviceType: String
)

data class WithdrawData(
    val amount: String,
    val narration: String,
    val trackingReference: String
)




data class WithdrawFromVirtualAccountResponse(
    val Data: String?,
    val Message: String,
    val RequestReference: String,
    val ResponseCode: String,
    val Status: Boolean,
    val TransactionReference: String
)


data class GetVirtualAccountTransactionsRequest(
    val `data`: GetVirtualAccountTransactionsData,
    val requestRef: String,
    val serviceType: String
)

data class GetVirtualAccountTransactionsData(
    val endDate: String,
    val pageNumber: Int,
    val pageSize: Int,
    val startDate: String,
    val trackingReference: String
)
data class VirtualAccountTransactionsResponse(
    val Data: VirtualAccountTransactionsResponseData,
    val Message: String,
    val Status: Boolean
)

data class VirtualAccountTransactionsResponseData(
    val Message: Any,
    val PostingsHistory: List<PostingsHistory>,
    val StatusCode: String,
    val TotalCredit: Double,
    val TotalDebit: Double,
    val TotalRecordInStore: Int
)

data class PostingsHistory(
    val AccountNumber: String,
    val AllowChangeCategory: Boolean,
    val Amount: Double,
    val BalanceAfter: Double,
    val BeneficiaryName: String,
    val BeneficiaryReference: String,
    val BillId: Any,
    val CategoryId: Int,
    val CategorySet: Boolean,
    val Charge: Double,
    val ClosedBy: Any,
    val DetailOfClosure: Any,
    val FinancialDate: String,
    val FinancialDateToBackdate: Any,
    val ForceDebit: Boolean,
    val GoalTitle: Any,
    val HasCOTWaiver: Boolean,
    val IPAddress: Any,
    val InstrumentNumber: String,
    val LinkedAccountNumber: Any,
    val Merchant: String,
    val Narration: String,
    val OpeningBalance: Double,
    val PhoneNumberRecharged: Any,
    val PostedBy: String,
    val PostingRecordType: Int,
    val PostingType: Int,
    val RealDate: String,
    val ReasonForClosure: Any,
    val RecipientBank: Any,
    val RecipientName: Any,
    val ReferenceNumber: String,
    val ReversalReferenceNumber: Any,
    val SenderBank: Any,
    val SenderName: Any,
    val SessionId: Any,
    val TagId: Int,
    val Tier0Waiver: Boolean,
    val TransactionMethod: Int,
    val UserID: Any
)


data class KudaDepositPayload(
    val accountName: String,
    val accountNumber: String,
    val amount: Double,
    val narrations: String,
    val payingBank: String,
    val recipientName: String,
    val senderName: String,
    val transactionDate: String,
    val transactionReference: String,
    val transactionType: String
)