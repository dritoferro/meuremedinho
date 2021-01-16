package br.com.tagliaferrodev.meu_remedinho.infrastructure.repository

import br.com.tagliaferrodev.meu_remedinho.core.remedio.Remedio
import br.com.tagliaferrodev.meu_remedinho.core.remedio.ports.RemedioRepository
import br.com.tagliaferrodev.meu_remedinho.infrastructure.config.DbConfig
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.QueryFilter
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import org.slf4j.LoggerFactory

data class RemedioTable(
    val id: String,
    val userId: String,
    val medicamento: String,
    val posologia: String,
    val dosagem: String,
    val apelido: String?
) {
    fun toRemedio() = Remedio(
        userId = userId,
        medicamento = medicamento,
        posologia = posologia,
        dosagem = dosagem,
        apelido = apelido
    ).also {
        it.id = id
    }

    companion object {
        fun fromItem(item: Item) = RemedioTable(
            id = item.getString("Id"),
            userId = item.getString("UserId"),
            medicamento = item.getString("Medicamento"),
            posologia = item.getString("Posologia"),
            dosagem = item.getString("Dosagem"),
            apelido = item.getString("Apelido")
        )
    }
}

private const val TABLE_NAME = "CadastroRemedios"
private const val USERID_INDEX = "UserId-index"

class RemedioRepositoryAdapter : RemedioRepository {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun save(remedio: Remedio) {
        val client = DbConfig.client()

        logger.info("Saving a new medicamento ${remedio.medicamento} for user ${remedio.userId}")

        val item = fillColumns(remedio)

        client.getTable(TABLE_NAME).putItem(item)

        logger.info("Successfully saved a new medicamento with id ${remedio.id}")
    }

    override fun findByApelidoAndUser(apelido: String, userId: String): Remedio? {
        logger.info("Search medicamento by apelido $apelido")

        val client = DbConfig.client()
        val table = client.getTable(TABLE_NAME)
        val index = table.getIndex(USERID_INDEX)

        val query = QuerySpec().withHashKey("UserId", userId)
            .withQueryFilters(QueryFilter("Apelido").eq(apelido))

        val result = index.query(query)

        if (result.firstOrNull() == null) {
            return null
        }

        logger.info("Found a medicamento with this apelido")

        return RemedioTable.fromItem(result.first()).toRemedio()
    }

    override fun findByMedicamentoAndUser(medicamento: String, userId: String): Remedio? {
        logger.info("Search medicamento by name $medicamento")

        val client = DbConfig.client()
        val table = client.getTable(TABLE_NAME)
        val index = table.getIndex(USERID_INDEX)

        val query = QuerySpec().withHashKey("UserId", userId)
            .withQueryFilters(QueryFilter("Medicamento").eq(medicamento))

        val result = index.query(query)

        if (result.firstOrNull() == null) {
            return null
        }

        logger.info("Found a medicamento with this name")

        return RemedioTable.fromItem(result.first()).toRemedio()
    }

    override fun findAllMedicamentos(userId: String): List<Remedio> {
        val client = DbConfig.client()
        val table = client.getTable(TABLE_NAME)
        val index = table.getIndex(USERID_INDEX)

        logger.info("Search all medicamentos by user $userId")

        val query = QuerySpec().withHashKey("UserId", userId)

        val result = index.query(query) ?: return emptyList()

        return result.map { RemedioTable.fromItem(it).toRemedio() }
    }

    private fun fillColumns(remedio: Remedio): Item = Item()
        .withPrimaryKey("Id", remedio.id)
        .withString("UserId", remedio.userId)
        .withString("Medicamento", remedio.medicamento)
        .withString("Posologia", remedio.posologia)
        .withString("Dosagem", remedio.dosagem)
        .withString("Apelido", remedio.apelido)
}
