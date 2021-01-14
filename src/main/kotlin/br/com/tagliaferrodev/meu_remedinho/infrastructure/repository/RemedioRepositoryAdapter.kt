package br.com.tagliaferrodev.meu_remedinho.infrastructure.repository

import br.com.tagliaferrodev.meu_remedinho.core.remedio.Remedio
import br.com.tagliaferrodev.meu_remedinho.core.remedio.ports.RemedioRepository
import br.com.tagliaferrodev.meu_remedinho.infrastructure.config.DbConfig
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec
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

class RemedioRepositoryAdapter : RemedioRepository {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun save(remedio: Remedio) {
        val client = DbConfig.client()

        logger.info("Saving a new medicamento ${remedio.medicamento} for user ${remedio.userId}")

        val item = fillColumns(remedio)

        client.getTable(TABLE_NAME).putItem(item)
    }

    override fun findByApelidoAndUser(apelido: String, userId: String): Remedio? {
        val client = DbConfig.client()

        logger.info("Search medicamento by apelido $apelido")

        val query = GetItemSpec().withPrimaryKey(
            "UserId",
            userId,
            "Apelido",
            apelido
        )

        val result = client.getTable(TABLE_NAME).getItem(query) ?: return null

        return RemedioTable.fromItem(result).toRemedio()
    }

    override fun findByMedicamentoAndUser(medicamento: String, userId: String): Remedio? {
        val client = DbConfig.client()

        logger.info("Search medicamento by name $medicamento")

        val query = GetItemSpec().withPrimaryKey(
            "UserId",
            userId,
            "Medicamento",
            medicamento
        )

        val result = client.getTable(TABLE_NAME).getItem(query) ?: return null

        return RemedioTable.fromItem(result).toRemedio()
    }

    override fun findAllMedicamentos(userId: String): List<Remedio> {
        val client = DbConfig.client()
        val table = client.getTable(TABLE_NAME)
        val index = table.getIndex("UserId-index")

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
