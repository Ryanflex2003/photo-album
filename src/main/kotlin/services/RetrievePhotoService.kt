package services

import client.ApiClient
import kotlinx.coroutines.runBlocking
import models.Photo

class RetrievePhotoService(private val apiClient: ApiClient = ApiClient()) {

    fun getPhotos(albumId: Int): String {
        val photos = runBlocking { apiClient.getPhotos(albumId) }
        if (photos.isEmpty()) {
            return "Unable to locate album id."
        }

        return getRelevantInfo(photos).toString()
    }

    private fun getRelevantInfo(photos: List<Photo?>): MutableList<Pair<String, String>> {
        val pairedList: MutableList<Pair<String, String>> = mutableListOf()
        photos.forEach{
            pairedList.add(Pair("[${it!!.id}]", "${it.title}\n"))
        }
        return pairedList
    }
}