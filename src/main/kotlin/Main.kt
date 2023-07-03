import services.RetrievePhotoService
fun main() {
    val retrievePhotoService = RetrievePhotoService()
    print("Enter an album id to view its photos:")
    val albumId = readln().toInt()

    val response = retrievePhotoService.getPhotos(albumId)

    println(response)
}