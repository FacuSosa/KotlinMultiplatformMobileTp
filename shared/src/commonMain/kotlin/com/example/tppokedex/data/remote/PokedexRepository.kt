package com.example.tppokedex.data.remote

import com.example.tppokedex.data.model.Pokedex
import com.example.tppokedex.initLogger
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class PokedexRepository {

    private val httpClient = HttpClient {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(tag = "HttpClient", message = message)
                }
            }
        }
        install(ContentNegotiation) {

            json(
                Json {

                    ignoreUnknownKeys = true
                }
            )
        }


    }.also {
        initLogger()
    }
    suspend fun get() : Pokedex{
        val result = httpClient.get("https://pokeapi.co/api/v2/pokemon/?limit=800"){
        }.body<Pokedex>()
        return result
    }
}