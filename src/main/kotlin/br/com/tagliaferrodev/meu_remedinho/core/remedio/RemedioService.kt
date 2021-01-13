package br.com.tagliaferrodev.meu_remedinho.core.remedio

import br.com.tagliaferrodev.meu_remedinho.core.remedio.ports.RemedioRepository
import br.com.tagliaferrodev.meu_remedinho.core.remedio.ports.dto.CadastroRemedioDTO
import br.com.tagliaferrodev.meu_remedinho.infrastructure.repository.RemedioRepositoryAdapter

class RemedioService {

    private val repository: RemedioRepository = RemedioRepositoryAdapter()

    fun cadastro(remedioDTO: CadastroRemedioDTO) {


    }
}