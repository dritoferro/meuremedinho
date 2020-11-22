package br.com.tagliaferrodev.meu_remedinho.core.remedio

import br.com.tagliaferrodev.meu_remedinho.core.config.DbConfig
import br.com.tagliaferrodev.meu_remedinho.core.remedio.ports.RemedioRepository
import br.com.tagliaferrodev.meu_remedinho.core.remedio.ports.dto.CadastroRemedioDTO

class RemedioService(
    private val repository: RemedioRepository
) {

    fun cadastro(remedioDTO: CadastroRemedioDTO) {
        val client = DbConfig.client()

    }
}