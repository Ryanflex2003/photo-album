import services.PhotoService

fun main() {
    val photoService = PhotoService()
    var albumId = 0

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
        readln().toInt()
    } catch (ex: Exception) {
        print("Enter a valid album id:")
        readInput()
    }
}