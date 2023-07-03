package services

import client.ApiClient
import io.mockk.*
import models.Photo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class RetrievePhotoServiceTest {

    @Test
    fun `given a valid album id when no photos return then expected response displayed`() {
        val expectedResponse = "Unable to locate album id."
        val randomAlbumId = Random.nextInt(1000)

        val mockApiClient = mockk<ApiClient>()
        val retrievePhotoService = RetrievePhotoService(mockApiClient)

        coEvery { mockApiClient.getPhotos(randomAlbumId) }  returns emptyList()

        val result = retrievePhotoService.getPhotos(randomAlbumId)

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `given a valid album id when a photo is returned then response displayed`() {
        val randomAlbumId = Random.nextInt(1000)

        val photoList = listOf(
            Photo(
                albumId = randomAlbumId,
                id = randomAlbumId,
                title = "test title",
                url = "http://test",
                thumbnailUrl = "http://thumbnailtest"
            )
        )

        val pairList = listOf(Pair("[${photoList[0].id}]", "${photoList[0].title}\n"))

        val expectedResponse = pairList.toString().replace("),", "")
            .replace("(", "")
            .replace("[[", "[")
            .replace(")", "")
            .replace("],", "]")
            .replace("\n]", "")

        val mockApiClient = mockk<ApiClient>()
        val retrievePhotoService = RetrievePhotoService(mockApiClient)

        coEvery { mockApiClient.getPhotos(randomAlbumId) }  returns photoList

        val result = retrievePhotoService.getPhotos(randomAlbumId)

        assertEquals(expectedResponse, result)
    }
}