package models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.random.Random

class PhotoTest {
    private lateinit var randomPhoto : Photo
    private val alphaNumericList : List<Char> = ('a'..'z') + ('0' .. '9')

    @BeforeEach
    fun generatePhoto() {
        randomPhoto = Photo (
            albumId = Random.nextInt(1000),
            id = Random.nextInt(0,100),
            title = List(10) { alphaNumericList.random() }.joinToString(""),
            thumbnailUrl = "http://${List(10) { alphaNumericList.random() }.joinToString("")}",
            url = "http://${List(10) { alphaNumericList.random() }.joinToString("")}"
        )
    }

    @Test
    fun `Given a random photo object when generated returns expected`() {
        val expectedResult = randomPhoto
        assertEquals(expectedResult.id, randomPhoto.id)
        assertEquals(expectedResult.albumId, randomPhoto.albumId)
        assertEquals(expectedResult.title, randomPhoto.title)
        assertEquals(expectedResult.thumbnailUrl, randomPhoto.thumbnailUrl)
        assertEquals(expectedResult.url, randomPhoto.url)
    }

}
