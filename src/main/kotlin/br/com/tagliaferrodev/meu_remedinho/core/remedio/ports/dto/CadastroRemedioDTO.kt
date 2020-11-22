package br.com.tagliaferrodev.meu_remedinho.core.remedio.ports.dto

class CadastroRemedioDTO(
    val userId: String,
    val medicamento: String,
    val posologia: String,
    val dosagem: String,
    val apelido: String?
)
