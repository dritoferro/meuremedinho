package br.com.tagliaferrodev.meu_remedinho.core.user

import br.com.tagliaferrodev.meu_remedinho.core.user.ports.UserRepository
import br.com.tagliaferrodev.meu_remedinho.infrastructure.repository.UserRepositoryAdapter
import org.slf4j.LoggerFactory

class UserService {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val repository: UserRepository = UserRepositoryAdapter()

    fun save(id: String, deviceId: String) {
        logger.info("Saving new user with id $id and deviceId $deviceId")

        val user = User(id = id, deviceId = deviceId)

        repository.save(user)
    }

    fun findById(id: String): User? {
        logger.info("Searching user by id $id")
        return repository.findById(id)
    }
}