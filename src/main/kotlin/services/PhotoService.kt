package services

import client.ApiClient
import kotlinx.coroutines.runBlocking
import models.Photo

class PhotoService(private val apiClient: ApiClient = ApiClient()) {

    fun getPhotos(albumId: Int): String {
        val photos = runBlocking { apiClient.getPhotos(albumId) }
        if (photos.isEmpty()) {
            return "Unable to locate album id."
        }

        val relevantPairList = getRelevantInfo(photos)
        return formatRelevantInfo(relevantPairList)
    }

    private fun getRelevantInfo(photos: List<Photo?>): List<Pair<String, String>> {
        val pairedList: MutableList<Pair<String, String>> = mutableListOf()
        photos.forEach{
            val tempList = Pair("[${it!!.id}]", "${it.title}\n")
            pairedList.add(tempList)
        }
        return pairedList
    }

    private fun formatRelevantInfo(photos: List<Pair<String, String>>): String {
       return photos.toString().replace("),", "")
            .replace("(", "")
            .replace("[[", "[")
            .replace(")", "")
            .replace("],", "]")
            .replace("\n]", "")
    }
}