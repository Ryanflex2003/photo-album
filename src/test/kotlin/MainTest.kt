import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import services.PhotoService

class MainTest {
    @Test
    fun `Verify given no album ids when album id of 0 entered album ids are listed`() {
        val mockReadWrapper = mockk<ReadWrapper>()
        val mockPhotoService = mockk<PhotoService>()
        every { mockReadWrapper.readLine() } returns "0"
        every { mockPhotoService.getAlbumIds() } returns ""
        every { mockPhotoService.getPhotos(any()) } returns ""
        val program = Program(mockReadWrapper, mockPhotoService)
        program.execute()
        verify (exactly = 1) { mockPhotoService.getAlbumIds()}
    }

    @Test
    fun `Verify given an album id when album id NOT 0 entered album ids are not returned`() {
        val mockReadWrapper = mockk<ReadWrapper>()
        val mockPhotoService = mockk<PhotoService>()
        every { mockReadWrapper.readLine() } returns "1"
        every { mockPhotoService.getPhotos(1) } returns ""
        val program = Program(mockReadWrapper, mockPhotoService)
        program.execute()
        verify (exactly = 0) { mockPhotoService.getAlbumIds()}
        verify (exactly = 1) { mockPhotoService.getPhotos(1) }
    }
}