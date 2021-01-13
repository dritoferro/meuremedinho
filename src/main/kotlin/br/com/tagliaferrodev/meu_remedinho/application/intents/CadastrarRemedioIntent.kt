package br.com.tagliaferrodev.meu_remedinho.application.intents

import br.com.tagliaferrodev.meu_remedinho.application.IntentNames
import br.com.tagliaferrodev.meu_remedinho.application.request.CadastroRemedioRequest
import br.com.tagliaferrodev.meu_remedinho.core.remedio.RemedioService
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import com.amazon.ask.request.RequestHelper
import org.slf4j.LoggerFactory
import java.util.*

class CadastrarRemedioIntent : RequestHandler {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val service: RemedioService = RemedioService()

    override fun canHandle(input: HandlerInput?): Boolean {
        return input?.matches(Predicates.intentName(IntentNames.SKILL_CADASTRAR_REMEDIO_INTENT.nome)) ?: false
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        val helper = RequestHelper.forHandlerInput(input)

        val medicamento = CadastroRemedioRequest(
            userId = "",
            medicamento = helper.getSlotValue("remedio").get(),
            posologia = helper.getSlotValue("posologia").get(),
            dosagem = helper.getSlotValue("dosagem").get(),
            apelido = helper.getSlotValue("apelido").get()
        )
        logger.info("Recebido request de cadastro ----> $medicamento")

        service.cadastro(medicamento.toCadastroDTO())

        logger.info("Remédio cadastrado com sucesso!")

        val speechText = "Remédio cadastrado com sucesso!"

        return input?.responseBuilder
            ?.withSpeech(speechText)
            ?.withSimpleCard("CadastroRemedio", speechText)
            ?.withReprompt(speechText)
            ?.build()!!
    }
}