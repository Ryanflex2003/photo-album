import services.PhotoService
fun main() {
    val photoService = PhotoService()
    var albumId = 0

    albumId = readInput()

    val response = photoService.getPhotos(albumId)

    println(response)
}

fun readInput() : Int {
    return try {
        print("Enter 0 to view available albums or enter an album id to view its photos:")
        readln().toInt()
    } catch (ex: Exception) {
        println("Enter a valid album Id")
        readInput()
    }
}