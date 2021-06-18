package software.rayner.signos.service

import retrofit2.http.GET;
import retrofit2.http.Query;
import software.rayner.signos.repository.models.response.DetailsResponse
import software.rayner.signos.repository.models.response.TextSearchResponse

interface AddressService {
    @GET("/maps/api/place/textsearch/json")
    suspend fun textSearch(
        @Query("query") query: String,
        @Query("inputType") inputType: String,
        @Query("key") apiKey: String
    ): TextSearchResponse
    @GET("/maps/api/place/details/json")
    suspend fun details(
        @Query("place_id") id: String,
        @Query("fields") fields: String,
        @Query("key") apiKey: String
    ): DetailsResponse
}