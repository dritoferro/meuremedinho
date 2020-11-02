package br.com.tagliaferrodev.meu_remedinho

enum class IntentNames(val nome: String) {
    SKILL_CADASTRAR_REMEDIO_INTENT("CadastrarRemedioIntent"),
    SKILL_LISTAR_REMEDIO_INTENT("ListarRemedioIntent"),
    SKILL_REGISTRAR_USO_INTENT("RegistrarUsoIntent"),
    SKILL_REMEDIOS_TOMADOS_INTENT("RemediosTomadosIntent"),
    SKILL_REMEDIOS_NAO_TOMADOS_INTENT("RemediosNaoTomadosIntent"),
    AMAZON_CANCEL_INTENT("AMAZON.CancelIntent"),
    AMAZON_STOP_INTENT("AMAZON.StopIntent"),
    AMAZON_HELP_INTENT("AMAZON.HelpIntent"),
    AMAZON_HOME_INTENT("AMAZON.NavigateHomeIntent")
}