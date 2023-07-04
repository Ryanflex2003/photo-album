import services.PhotoService

fun main() {
    val program = Program()
    program.execute()
}

class Program(
    private var readWrapper: ReadWrapper = ReadWrapper(),
    private var photoService: PhotoService = PhotoService()
) {
    fun execute() {
        var albumId : Int
        print("Enter 0 to view available albums or enter an album id to view its photos:")
        albumId = readInput()

        if (albumId == 0) {
            println("Available album ids:${photoService.getAlbumIds()}")
            albumId = readInput(true)
        }

        val response = photoService.getPhotos(albumId)
        println(response)
    }

    fun readInput(askToEnter: Boolean = false) : Int {
        return try {
            if (askToEnter)
                print("Enter an album id to view its photos:")
            readWrapper.readLine().toInt()
        } catch (ex: Exception) {
            print("Enter a valid album id:")
            readInput()
        }
    }
}

class ReadWrapper() {
    fun readLine() : String {
         return readln()
    }
}