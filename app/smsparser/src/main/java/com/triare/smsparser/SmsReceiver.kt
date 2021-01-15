package com.triare.smsparser

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage

class SmsReceiver : BroadcastReceiver {

    private var senderAddress: String
    private var onSmsReceive: (message: String?) -> Unit
    private var pattern: String? = null

    constructor(
        senderAddress: String,
        onSmsReceive: (sms: String?) -> Unit
    ) {
        this.senderAddress = senderAddress
        this.onSmsReceive = onSmsReceive
    }

    constructor(
        senderAddress: String,
        pattern: String,
        onSmsReceive: (sms: String?) -> Unit
    ) {
        this.senderAddress = senderAddress
        this.pattern = pattern
        this.onSmsReceive = onSmsReceive
    }

    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            intent.extras?.let {
                val smsModel = getSmsFromBundle(it)
                setVerificationCode(smsModel)
            }
        }
    }

    private fun getSmsFromBundle(bundle: Bundle): SmsModel {
        val protocolDataUnit = bundle.get(KEY_PDUS) as Array<*>

        var serviceAddress = EMPTY_STRING
        var message = EMPTY_STRING

        for (protocol in protocolDataUnit.indices) {
            val smsMessage = SmsMessage.createFromPdu(
                protocolDataUnit[protocol] as ByteArray,
                bundle.getString(KEY_FORMAT)
            )

            serviceAddress = smsMessage.originatingAddress ?: EMPTY_STRING
            message += smsMessage.displayMessageBody ?: EMPTY_STRING
        }
        return SmsModel(serviceAddress, message)
    }

    private fun setVerificationCode(smsModel: SmsModel) {
        if (senderAddress == smsModel.serviceAddress) {
            if (!pattern.isNullOrBlank()) {
                val value = Regex(pattern!!).find(smsModel.message)?.value
                onSmsReceive.invoke(value)
            } else {
                onSmsReceive.invoke(smsModel.message)
            }
        }
    }

    companion object {
        const val KEY_PDUS = "pdus"
        const val KEY_FORMAT = "format"

        const val EMPTY_STRING = ""
    }
}

private data class SmsModel(
    var serviceAddress: String,
    var message: String
)
