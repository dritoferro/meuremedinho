package br.com.tagliaferrodev.meu_remedinho.application.request

import br.com.tagliaferrodev.meu_remedinho.core.remedio.ports.dto.CadastroRemedioDTO

data class CadastroRemedioRequest(
    val userId: String,
    val medicamento: String,
    val posologia: String,
    val dosagem: String,
    val apelido: String?
) {
    fun toCadastroDTO() = CadastroRemedioDTO(
        userId = userId,
        medicamento = medicamento,
        posologia = posologia,
        dosagem = dosagem,
        apelido = apelido
    )
}
