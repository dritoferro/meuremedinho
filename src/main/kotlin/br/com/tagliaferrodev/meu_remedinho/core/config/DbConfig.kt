package br.com.tagliaferrodev.meu_remedinho.core.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder

object DbConfig {
    fun client(): AmazonDynamoDBAsync = AmazonDynamoDBAsyncClientBuilder
        .standard()
        .build()
}