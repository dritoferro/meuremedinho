package br.com.tagliaferrodev.meu_remedinho.core.user.ports

import br.com.tagliaferrodev.meu_remedinho.core.user.User

interface UserRepository {

    fun save(user: User)

    fun findById(id: String): User?
}