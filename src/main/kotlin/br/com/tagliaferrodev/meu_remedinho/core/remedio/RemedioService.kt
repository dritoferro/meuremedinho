package br.com.tagliaferrodev.meu_remedinho.core.remedio

import br.com.tagliaferrodev.meu_remedinho.core.remedio.ports.RemedioRepository
import br.com.tagliaferrodev.meu_remedinho.core.remedio.ports.dto.CadastroRemedioDTO
import br.com.tagliaferrodev.meu_remedinho.infrastructure.repository.RemedioRepositoryAdapter

class RemedioService {

    private val repository: RemedioRepository = RemedioRepositoryAdapter()

    fun cadastro(remedioDTO: CadastroRemedioDTO) {
        repository.save(Remedio.create(remedioDTO))
    }

    fun findByMedicamento(medicamento: String, userId: String): Remedio? {
        return repository.findByMedicamentoAndUser(medicamento, userId)
    }

    fun findByApelido(apelido: String?, userId: String): Remedio? {
        if (apelido.isNullOrBlank()) {
            return null
        }

        return repository.findByApelidoAndUser(apelido, userId)
    }

    fun findAllByUser(userId: String): List<Remedio> {
        return repository.findAllMedicamentos(userId)
    }
}
