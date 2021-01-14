package br.com.tagliaferrodev.meu_remedinho.core.registrouso

import java.time.LocalDateTime

data class RegistroUso(
    val medicamentoId: String,
    val userId: String,
    val dateTime: LocalDateTime
)
