package br.com.tagliaferrodev.meu_remedinho.core.registrouso

import br.com.tagliaferrodev.meu_remedinho.core.registrouso.ports.RegistroUsoRepository
import br.com.tagliaferrodev.meu_remedinho.core.remedio.RemedioService
import br.com.tagliaferrodev.meu_remedinho.infrastructure.repository.RegistroUsoRepositoryAdapter
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class RegistroUsoService {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val remedioService = RemedioService()

    private val repository: RegistroUsoRepository = RegistroUsoRepositoryAdapter()

    fun save(medicamento: String, hora: String, userId: String) {
        logger.info("Start searching medicamento")

        val hourSplitted = hora.split(":")
        val now = LocalDateTime.now()
        val datetime =
            LocalDateTime.of(now.year, now.month, now.dayOfMonth, hourSplitted[0].toInt(), hourSplitted[1].toInt())

        val remedio = remedioService.findByApelido(medicamento, userId)
            ?: remedioService.findByMedicamento(medicamento, userId) ?: throw RuntimeException("Remédio não encontrado")

        logger.info("Medicamento found: ${remedio.medicamento}")

        val newRegistro = RegistroUso(remedio.id, userId, datetime)

        repository.save(newRegistro)
    }

    fun findRemediosTomadosToday(userId: String): List<String> {
        logger.info("Searching for medicamentos tomados for user $userId")

        val medicamentos = repository.findByUserForToday(userId)

        if (medicamentos.isEmpty()) {
            return emptyList()
        }

        val remedios = remedioService.findAllByUser(userId)

        return medicamentos.mapNotNull { tomado ->
            val remedio = remedios.find { it.id == tomado.medicamentoId }

            remedio?.let {
                it.apelido ?: it.medicamento
            }
        }
    }

    fun findRemediosNaoTomadosToday(userId: String): List<String> {
        logger.info("Searching for medicamentos não tomados for user $userId")

        val medicamentos = repository.findByUserForToday(userId)

        val remedios = remedioService.findAllByUser(userId)

        return remedios.mapNotNull { remedio ->
            val medicamentoNaoTomado = medicamentos.find { it.medicamentoId == remedio.id } == null

            if (medicamentoNaoTomado) {
                remedio.apelido ?: remedio.medicamento
            } else {
                null
            }
        }
    }
}