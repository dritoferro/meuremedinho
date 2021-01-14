package br.com.tagliaferrodev.meu_remedinho.application.intents

import br.com.tagliaferrodev.meu_remedinho.application.IntentNames
import br.com.tagliaferrodev.meu_remedinho.application.extension.getParameter
import br.com.tagliaferrodev.meu_remedinho.application.extension.getUserId
import br.com.tagliaferrodev.meu_remedinho.core.registrouso.RegistroUsoService
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import org.slf4j.LoggerFactory
import java.util.*

class RegistrarUsoIntent : RequestHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val service = RegistroUsoService()

    override fun canHandle(input: HandlerInput?): Boolean {
        return input?.matches(Predicates.intentName(IntentNames.SKILL_REGISTRAR_USO_INTENT.nome)) ?: false
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        logger.info("Received request for registro de uso")

        val userId = input?.getUserId()
        val medicamento = input.getParameter("remedioUsado")
        val hora = input.getParameter("hora")

        service.save(medicamento, hora, userId!!)

        val speechText = "Registro realizado com sucesso"

        val repromptText = "O que deseja fazer agora ?"

        return input.responseBuilder
            .withSpeech(speechText)
            .withSimpleCard("MeuRemedinhoSkill", "RegistroUso")
            .withReprompt(repromptText)
            .build()
    }
}
