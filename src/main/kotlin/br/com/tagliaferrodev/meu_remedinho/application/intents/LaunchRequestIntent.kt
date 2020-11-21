package br.com.tagliaferrodev.meu_remedinho.application.intents

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import java.util.*

class LaunchRequestIntent : RequestHandler {
    override fun canHandle(input: HandlerInput?): Boolean {
        return input?.matches(Predicates.requestType(LaunchRequest::class.java)) ?: false
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        val speechText = "Bem vindo ao Meu Remedinho, você pode dizer: cadastrar um remédio OU listar os remédios OU registrar o uso de um remédio OU remédios tomados OU remédios não tomados"

        return input?.responseBuilder
                ?.withSpeech(speechText)
                ?.withSimpleCard("MeuRemedinhoSkill", speechText)
                ?.withReprompt(speechText)
                ?.build()!!
    }
}