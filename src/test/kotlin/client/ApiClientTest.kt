package client

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import models.Photo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class ApiClientTest {
    private val albumId = Random.nextInt(200)
    private val id = Random.nextInt(100)
    private val alphaNumericList : List<Char> = ('a'..'z') + ('0' .. '9')
    private val randomTitle = List(10) { alphaNumericList.random() }.joinToString("")
    private val singleResponse = """
    [
    {
        "albumId": ${albumId},
        "id": ${id},
        "title": ${randomTitle},
        "url": "https://${randomTitle}",
        "thumbnailUrl": "https://amazing${randomTitle}"
    }
    ]
    """.trimIndent()

    private val multipleAlbumIdResponse = """
    [
    {
        "albumId": ${albumId},
        "id": ${id},
        "title": ${randomTitle},
        "url": "https://${randomTitle}",
        "thumbnailUrl": "https://amazing${randomTitle}"
    },
    {
        "albumId": ${albumId},
        "id": ${id + 1},
        "title": ${randomTitle},
        "url": "https://${randomTitle}",
        "thumbnailUrl": "https://amazing${randomTitle}"
    }    
    ]
    """.trimIndent()

    private val emptyResponse = """[]"""

    @Test
    fun `given a valid album id when results present return valid response`() {
        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel(singleResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val apiClient = ApiClient(mockEngine)
        val response = runBlocking { apiClient.getPhotos(albumId) }

        assertEquals(albumId, response[0]!!.albumId)
        assertEquals(id, response[0]!!.id)
        assertEquals(randomTitle, response[0]!!.title)
    }

    @Test
    fun `given a valid album id when results not present return empty response`() {
        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel(emptyResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val apiClient = ApiClient(mockEngine)
        val response = runBlocking { apiClient.getPhotos(albumId) }
        assertEquals(response, emptyList<Photo>())
    }

    @Test
    fun `given user wants a list of available albums return distinct available album Ids`() {
        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel(multipleAlbumIdResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val apiClient = ApiClient(mockEngine)
        val response = runBlocking { apiClient.getAlbums() }
        assertEquals(albumId, response[0])
        assertEquals(1, response.size)
    }

}