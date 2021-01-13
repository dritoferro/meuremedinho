package br.com.tagliaferrodev.meu_remedinho.application.default_intents

import br.com.tagliaferrodev.meu_remedinho.application.IntentNames
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import java.util.*

class HelpIntent : RequestHandler {
    override fun canHandle(input: HandlerInput?): Boolean {
        return input?.matches(Predicates.intentName(IntentNames.AMAZON_HELP_INTENT.nome)) ?: false
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        val speechText =
            """As seguintes ações estão disponíveis no momento: Cadastro de um remédio; Registro de Uso; 
                Listar remédios cadastros; Listar remédios tomados e não tomados.
            """.trimMargin()

        return input?.responseBuilder
            ?.withSpeech(speechText)
            ?.withSimpleCard("MeuRemedinhoSkill", speechText)
            ?.withReprompt(speechText)
            ?.build()!!
    }
}