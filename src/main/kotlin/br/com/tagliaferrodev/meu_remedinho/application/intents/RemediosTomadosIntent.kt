package br.com.tagliaferrodev.meu_remedinho.application.intents

import br.com.tagliaferrodev.meu_remedinho.application.IntentNames
import br.com.tagliaferrodev.meu_remedinho.application.extension.getUserId
import br.com.tagliaferrodev.meu_remedinho.core.registrouso.RegistroUsoService
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import org.slf4j.LoggerFactory
import java.util.*

class RemediosTomadosIntent : RequestHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val service = RegistroUsoService()

    override fun canHandle(input: HandlerInput?): Boolean {
        return input?.matches(Predicates.intentName(IntentNames.SKILL_REMEDIOS_TOMADOS_INTENT.nome)) ?: false
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        logger.info("Received request to list medicamentos tomados")

        val userId = input?.getUserId()

        val result = service.findRemediosTomadosToday(userId!!)

        val speechText = if (result.isNotEmpty()) {
            result.joinToString(separator = ";")
        } else {
            "Você não fez uso de nenhum medicamento hoje"

        }

        val reprompt = "O que deseja fazer agora ?"

        return input.responseBuilder
            .withSpeech(speechText)
            .withSimpleCard("MeuRemedinhoSkill", "RemediosTomados")
            .withReprompt(reprompt)
            .build()
    }
}
