package br.com.tagliaferrodev.meu_remedinho.core.registrouso.ports

import br.com.tagliaferrodev.meu_remedinho.core.registrouso.RegistroUso

interface RegistroUsoRepository {

    fun save(registroUso: RegistroUso)

    fun findByUserForToday(userId: String): List<RegistroUso>
}