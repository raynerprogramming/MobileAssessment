package software.rayner.signos.repository.models

import android.util.Log
import com.google.gson.annotations.SerializedName
import software.rayner.signos.AddressViewModel
import software.rayner.signos.Place
import software.rayner.signos.util.TAG

data class PlacesDto (
    @SerializedName("place_id")
    var id: String,

    @SerializedName("formatted_address")
    var address: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("types")
    var types: List<String> = emptyList(),

    @SerializedName("formatted_phone_number")
    var phone: String,
)

fun DtoToVM(dto: PlacesDto): Place {
    Log.e(TAG, dto.toString())
    return Place(dto.id,dto.address,dto.name,dto.types?.joinToString(),dto.phone )
}