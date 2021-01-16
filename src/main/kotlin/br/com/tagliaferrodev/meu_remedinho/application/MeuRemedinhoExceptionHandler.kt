package br.com.tagliaferrodev.meu_remedinho.application

import com.amazon.ask.dispatcher.exception.ExceptionHandler
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Response
import org.slf4j.LoggerFactory
import java.util.*

class MeuRemedinhoExceptionHandler : ExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun canHandle(input: HandlerInput?, throwable: Throwable?): Boolean {
        logger.error("Error for input: ${input?.requestEnvelopeJson?.toPrettyString()}")
        logger.error(throwable?.message)
        return throwable is RuntimeException
    }

    override fun handle(input: HandlerInput?, throwable: Throwable?): Optional<Response> {
        return input?.responseBuilder
            ?.withSpeech("Ops, ocorreu um erro com seu pedido, por favor, tente novamente mais tarde. ${throwable?.message}")
            ?.build()!!
    }
}

/*Unable to find a suitable request handler
* [main] ERROR br.com.tagliaferrodev.meu_remedinho.application.MeuRemedinhoExceptionHandler - Remédio não encontrado
* [main] ERROR br.com.tagliaferrodev.meu_remedinho.application.MeuRemedinhoExceptionHandler - Error for input:*/