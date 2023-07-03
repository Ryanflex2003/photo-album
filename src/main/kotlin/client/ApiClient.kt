package client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import models.Photo
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.collections.ArrayList

class ApiClient (engine: HttpClientEngine = CIO.create()) {
    private val client = HttpClient(engine) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getPhotos(albumId: Int): List<Photo?> {
        var file = File("src/main/resources/application.properties")
        val properties = Properties()
        FileInputStream(file).use { properties.load(it) }
        val photoUrl = properties.getProperty("photoUrl")

        val photoList: List<Photo?>
        runBlocking {
            val photos: ArrayList<Photo?> =
                client.get("${photoUrl}?albumId=${albumId}").body()
            photoList = photos.toList()
        }
        return photoList
    }
}