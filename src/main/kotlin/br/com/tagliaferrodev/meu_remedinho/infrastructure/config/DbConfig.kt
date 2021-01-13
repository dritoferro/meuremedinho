package br.com.tagliaferrodev.meu_remedinho.infrastructure.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB

object DbConfig {
    fun client(): DynamoDB = DynamoDB(
        AmazonDynamoDBClientBuilder
            .defaultClient()
    )
}