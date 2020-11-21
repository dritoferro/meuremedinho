package br.com.tagliaferrodev.meu_remedinho.application.request

data class CadastroRemedioRequest(
        val userId: String,
        val medicamento: String?,
        val posologia: String?,
        val dosagem: String?,
        val apelido: String?
)
