package br.com.tagliaferrodev.meu_remedinho

import br.com.tagliaferrodev.meu_remedinho.application.MeuRemedinhoExceptionHandler
import br.com.tagliaferrodev.meu_remedinho.application.default_intents.CancelIntent
import br.com.tagliaferrodev.meu_remedinho.application.default_intents.HelpIntent
import br.com.tagliaferrodev.meu_remedinho.application.default_intents.NavigateHomeIntent
import br.com.tagliaferrodev.meu_remedinho.application.default_intents.StopIntent
import br.com.tagliaferrodev.meu_remedinho.application.intents.*
import com.amazon.ask.Skill
import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills

class MeuRemedinhoStreamHandler : SkillStreamHandler(buildSkill()) {

    companion object {
        fun buildSkill(): Skill {
            return Skills.standard()
                .addRequestHandlers(
                    LaunchRequestIntent(),
                    CadastrarRemedioIntent(),
                    ListarRemedioIntent(),
                    RegistrarUsoIntent(),
                    RemediosNaoTomadosIntent(),
                    RemediosTomadosIntent(),
                    CancelIntent(),
                    HelpIntent(),
                    NavigateHomeIntent(),
                    StopIntent()
                )
                .addExceptionHandler(MeuRemedinhoExceptionHandler())
                .build()
        }
    }

}