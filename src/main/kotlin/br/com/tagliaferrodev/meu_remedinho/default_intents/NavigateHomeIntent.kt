package br.com.tagliaferrodev.meu_remedinho.default_intents

import br.com.tagliaferrodev.meu_remedinho.IntentNames
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import java.util.*

class NavigateHomeIntent : RequestHandler {
    override fun canHandle(input: HandlerInput?): Boolean {
        return input?.matches(Predicates.intentName(IntentNames.AMAZON_HOME_INTENT.nome)) ?: false
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        TODO("Not yet implemented")
    }
}