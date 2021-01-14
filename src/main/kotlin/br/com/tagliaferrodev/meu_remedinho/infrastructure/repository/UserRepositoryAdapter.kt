package br.com.tagliaferrodev.meu_remedinho.infrastructure.repository

import br.com.tagliaferrodev.meu_remedinho.core.user.User
import br.com.tagliaferrodev.meu_remedinho.core.user.ports.UserRepository
import br.com.tagliaferrodev.meu_remedinho.infrastructure.config.DbConfig
import com.amazonaws.services.dynamodbv2.document.Item
import org.slf4j.LoggerFactory

data class UserTable(val id: String, val deviceId: String) {

    fun toUser() = User(
        id = id,
        deviceId = deviceId
    )
}

private const val TABLE_NAME = "Users"

class UserRepositoryAdapter : UserRepository {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun save(user: User) {
        logger.info("Start saving user")

        val client = DbConfig.client()
        val table = client.getTable(TABLE_NAME)

        val item = Item().withPrimaryKey("Id", user.id).withString("DeviceId", user.deviceId)

        table.putItem(item)

        logger.info("Saved user with id ${user.id}")
    }

    override fun findById(id: String): User? {
        logger.info("Start search by id")

        val client = DbConfig.client()

        val search = client.getTable(TABLE_NAME).getItem("Id", id) ?: return null

        logger.info("Founded user with id $id")

        return UserTable(
            id = search.getString("Id"),
            deviceId = search.getString("DeviceId")
        ).toUser()
    }
}
