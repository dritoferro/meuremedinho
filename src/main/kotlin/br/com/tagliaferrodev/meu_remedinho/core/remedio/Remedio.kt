package br.com.tagliaferrodev.meu_remedinho.core.remedio

data class Remedio(
        val userId: String,
        val medicamento: String?,
        val posologia: String?,
        val dosagem: String?,
        val apelido: String?
)
