package br.com.tagliaferrodev.meu_remedinho

import com.amazon.ask.dispatcher.exception.ExceptionHandler
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.exception.AskSdkException
import com.amazon.ask.model.Response
import org.slf4j.LoggerFactory
import java.util.*

class MeuRemedinhoExceptionHandler : ExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun canHandle(input: HandlerInput?, throwable: Throwable?): Boolean {
        logger.error("Error for input: ${input?.requestEnvelopeJson?.toPrettyString()}")
        logger.error(throwable?.message)
        return throwable is AskSdkException
    }

    override fun handle(input: HandlerInput?, throwable: Throwable?): Optional<Response> {
        return input?.responseBuilder
                ?.withSpeech("Ops, ocorreu um erro com seu pedido, por favor, tente novamente mais tarde.")
                ?.build()!!
    }
}