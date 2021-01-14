package br.com.tagliaferrodev.meu_remedinho.application.intents

import br.com.tagliaferrodev.meu_remedinho.application.IntentNames
import br.com.tagliaferrodev.meu_remedinho.application.extension.getParameter
import br.com.tagliaferrodev.meu_remedinho.application.extension.getUserId
import br.com.tagliaferrodev.meu_remedinho.application.request.CadastroRemedioRequest
import br.com.tagliaferrodev.meu_remedinho.core.remedio.RemedioService
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import org.slf4j.LoggerFactory
import java.util.*

class CadastrarRemedioIntent : RequestHandler {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val service: RemedioService = RemedioService()

    override fun canHandle(input: HandlerInput?): Boolean {
        return input?.matches(Predicates.intentName(IntentNames.SKILL_CADASTRAR_REMEDIO_INTENT.nome)) ?: false
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        val medicamento = CadastroRemedioRequest(
            userId = input.getUserId()!!,
            medicamento = input.getParameter("remedio"),
            posologia = input.getParameter("posologia"),
            dosagem = input.getParameter("dosagem"),
            apelido = input.getParameter("apelido")
        )

        logger.info("Recebido request de cadastro ----> $medicamento")

        service.cadastro(medicamento.toCadastroDTO())

        logger.info("Remédio cadastrado com sucesso!")

        val speechText = "Remédio cadastrado com sucesso!"

        val repromptText = "O que deseja fazer agora ?"

        return input?.responseBuilder
            ?.withSpeech(speechText)
            ?.withSimpleCard("CadastroRemedio", speechText)
            ?.withReprompt(repromptText)
            ?.build()!!
    }
}