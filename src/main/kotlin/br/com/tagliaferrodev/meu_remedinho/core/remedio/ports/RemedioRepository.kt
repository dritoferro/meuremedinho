package br.com.tagliaferrodev.meu_remedinho.core.remedio.ports

import br.com.tagliaferrodev.meu_remedinho.core.remedio.Remedio

interface RemedioRepository {

    fun save(remedio: Remedio)

    fun findByApelidoAndUser(apelido: String, userId: String): Remedio?

    fun findByMedicamentoAndUser(medicamento: String, userId: String): Remedio?

    fun findAllMedicamentos(userId: String): List<Remedio>
}
