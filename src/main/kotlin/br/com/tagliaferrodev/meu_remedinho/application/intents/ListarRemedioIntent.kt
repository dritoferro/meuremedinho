package br.com.tagliaferrodev.meu_remedinho.application.intents

import br.com.tagliaferrodev.meu_remedinho.application.IntentNames
import br.com.tagliaferrodev.meu_remedinho.application.extension.getUserId
import br.com.tagliaferrodev.meu_remedinho.core.remedio.RemedioService
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import org.slf4j.LoggerFactory
import java.util.*

class ListarRemedioIntent : RequestHandler {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val service = RemedioService()

    override fun canHandle(input: HandlerInput?): Boolean {
        return input?.matches(Predicates.intentName(IntentNames.SKILL_LISTAR_REMEDIO_INTENT.nome)) ?: false
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        val userId = input?.getUserId()

        logger.info("Received request to find all by user id $userId")

        val remedios = service.findAllByUser(userId!!)

        val speechText = if (remedios.isNotEmpty()) {
            remedios.joinToString { it.apelido ?: it.medicamento }
        } else {
            "você não possui medicamentos cadastrados ainda"
        }

        val repromptText = "O que deseja fazer agora ?"

        return input.responseBuilder
            .withSpeech(speechText)
            .withSimpleCard("ListarRemedios", speechText)
            .withReprompt(repromptText)
            .build()!!
    }
}