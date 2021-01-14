package br.com.tagliaferrodev.meu_remedinho.core.remedio

import br.com.tagliaferrodev.meu_remedinho.core.remedio.ports.dto.CadastroRemedioDTO
import java.util.*

data class Remedio(
    val userId: String,
    val medicamento: String,
    val posologia: String,
    val dosagem: String,
    val apelido: String?
) {

    var id: String = UUID.randomUUID().toString()

    companion object {
        fun create(dto: CadastroRemedioDTO) = Remedio(
            userId = dto.userId,
            medicamento = dto.medicamento,
            posologia = dto.posologia,
            dosagem = dto.dosagem,
            apelido = dto.apelido
        )
    }
}
