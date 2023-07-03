package services

import client.ApiClient
import io.mockk.*
import models.Photo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class PhotoServiceTest {

    @Test
    fun `given a valid album id when no photos return then expected response displayed`() {
        val expectedResponse = "Unable to locate album id."
        val randomAlbumId = Random.nextInt(1000)

        val mockApiClient = mockk<ApiClient>()
        val photoService = PhotoService(mockApiClient)

        coEvery { mockApiClient.getPhotos(randomAlbumId) }  returns emptyList()

        val result = photoService.getPhotos(randomAlbumId)

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
        val photoService = PhotoService(mockApiClient)

        coEvery { mockApiClient.getPhotos(randomAlbumId) }  returns photoList

        val result = photoService.getPhotos(randomAlbumId)

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `given album ids only distinct album ids are returned`() {
        val albumList = listOf(1, 2, 3)

        val mockApiClient = mockk<ApiClient>()
        val photoService = PhotoService(mockApiClient)

        coEvery { mockApiClient.getAlbums() }  returns albumList

        val expected = "1, 2, 3"
        val result = photoService.getAlbumIds()
        assertEquals( expected,result)

    }
}