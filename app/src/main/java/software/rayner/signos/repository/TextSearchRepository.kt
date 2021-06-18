package software.rayner.signos.repository

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import software.rayner.signos.AddressViewModel
import software.rayner.signos.Place
import software.rayner.signos.repository.models.DtoToVM
import software.rayner.signos.service.AddressService
import software.rayner.signos.util.TAG

interface SearchRepository {
    suspend fun search(query: String): List<Place>
    suspend fun details(id: String): Place
}

class TextSearchRepository: SearchRepository {
    override suspend fun search(query: String): List<Place> {
        var service = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(AddressService::class.java)
    //This key is IP restricted
        var results = service.textSearch(query,"textquery","AIzaSyB9nOz4xacFDFqDP4deRVD0Ckgv5uqmFNQ")
        return results.places.map { DtoToVM(it) }
    }
    override suspend fun details(id: String): Place {
        var service = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(AddressService::class.java)
//This key is IP restricted
        var result = service.details(id,"place_id,formatted_address,name,rating,formatted_phone_number,photo,types","AIzaSyB9nOz4xacFDFqDP4deRVD0Ckgv5uqmFNQ")
        Log.e(TAG, result.place.toString())
        return DtoToVM(result.place)
    }


}