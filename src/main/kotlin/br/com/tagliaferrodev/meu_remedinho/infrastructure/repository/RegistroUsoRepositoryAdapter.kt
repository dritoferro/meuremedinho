package br.com.tagliaferrodev.meu_remedinho.infrastructure.repository

import br.com.tagliaferrodev.meu_remedinho.core.registrouso.RegistroUso
import br.com.tagliaferrodev.meu_remedinho.core.registrouso.ports.RegistroUsoRepository
import br.com.tagliaferrodev.meu_remedinho.infrastructure.config.DbConfig
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.QueryFilter
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class RegistroUsoTable(
    val id: String,
    val medicamentoId: String,
    val userId: String,
    val dateTime: String
) {
    fun toRegistroUso() = RegistroUso(
        medicamentoId = medicamentoId,
        userId = userId,
        dateTime = LocalDateTime.parse(dateTime)
    )

    companion object {
        fun create(item: Item) = RegistroUsoTable(
            id = item.getString("Id"),
            medicamentoId = item.getString("MedicamentoId"),
            userId = item.getString("UserId"),
            dateTime = item.getString("Datetime")
        )
    }
}

private const val TABLE_NAME = "RegistroUso"

class RegistroUsoRepositoryAdapter : RegistroUsoRepository {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun save(registroUso: RegistroUso) {
        logger.info("Saving new registro de uso for userId ${registroUso.userId}")

        val client = DbConfig.client()
        val table = client.getTable(TABLE_NAME)

        val item = fillColumns(registroUso)

        table.putItem(item)

        logger.info("Successfully saved new registro de uso")
    }

    override fun findByUserForToday(userId: String): List<RegistroUso> {
        logger.info("Searching registros for userId $userId")

        val client = DbConfig.client()
        val table = client.getTable(TABLE_NAME)
        val index = table.getIndex("UserId-index")

        val start = LocalDate.now().atStartOfDay()
        val end = start.plusHours(23).plusMinutes(59).plusSeconds(59)

        val query = QuerySpec()
            .withHashKey("UserId", userId)
            .withQueryFilters(QueryFilter("Datetime").between(start.toString(), end.toString()))

        val result = index.query(query) ?: return emptyList()

        logger.info("Found registers for this user")

        return result.map { RegistroUsoTable.create(it).toRegistroUso() }
    }

    private fun fillColumns(registroUso: RegistroUso) = Item()
        .withPrimaryKey("Id", UUID.randomUUID().toString())
        .withString("MedicamentoId", registroUso.medicamentoId)
        .withString("UserId", registroUso.userId)
        .withString("Datetime", registroUso.dateTime.toString())
}
