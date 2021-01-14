package br.com.tagliaferrodev.meu_remedinho.application.extension

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.request.RequestHelper

fun HandlerInput?.getUserId() = this?.requestEnvelope?.context?.system?.user?.userId

fun HandlerInput?.getDeviceId() = this?.requestEnvelope?.context?.system?.device?.deviceId

fun HandlerInput?.getParameter(name: String): String = RequestHelper.forHandlerInput(this).getSlotValue(name).get()
