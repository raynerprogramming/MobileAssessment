package software.rayner.signos.repository.models.response

import com.google.gson.annotations.SerializedName
import software.rayner.signos.repository.models.PlacesDto

class DetailsResponse (
    @SerializedName("result")
    var place: PlacesDto
)
