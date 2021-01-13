package br.com.tagliaferrodev.meu_remedinho.application.intents

import br.com.tagliaferrodev.meu_remedinho.application.extension.getDeviceId
import br.com.tagliaferrodev.meu_remedinho.application.extension.getUserId
import br.com.tagliaferrodev.meu_remedinho.core.user.UserService
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import org.slf4j.LoggerFactory
import java.util.*

class LaunchRequestIntent : RequestHandler {

    private val service = UserService()
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun canHandle(input: HandlerInput?): Boolean {
        return input?.matches(Predicates.requestType(LaunchRequest::class.java)) ?: false
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        val userId = input?.getUserId()
        val deviceId = input?.getDeviceId()

        var speechText = "Bom te ver novamente, o que deseja fazer ?"

        logger.info("Searching user by id")

        val user = service.findById(userId!!)

        if (user == null) {
            speechText =
                """Bem vindo ao Meu Remedinho, você pode começar dizendo: cadastrar um remédio ou comecei a tomar
                    um novo remédio.Diga ajuda para saber a lista completa de ações""".trimMargin()

            logger.info("Saving a new user")

            service.save(userId, deviceId!!)
        }

        return input.responseBuilder
            ?.withSpeech(speechText)
            ?.withSimpleCard("MeuRemedinhoSkill", speechText)
            ?.withReprompt(speechText)
            ?.build()!!
    }
}