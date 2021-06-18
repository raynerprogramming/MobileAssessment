package software.rayner.signos

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import software.rayner.signos.repository.TextSearchRepository
import software.rayner.signos.util.TAG

data class Place(
    val id: String?,
    val address: String?,
    val name: String?,
    val type: String?,
    val phone: String?
)
class AddressViewModel: ViewModel() {
    val remoteData: MutableState<MutableList<Place>> = mutableStateOf(arrayListOf())
    val localData: MutableState<MutableList<Place>> = mutableStateOf(arrayListOf())
    var selected: MutableState<Place?> = mutableStateOf(null)
    var searchMode: MutableState<Boolean> = mutableStateOf(false)
    var repo = TextSearchRepository()
    fun GetPlaces(query: String){
        viewModelScope.launch {
            try{
                remoteData.value = repo.search(query).toMutableList()
            }catch( e: Exception){
                Log.e(TAG, " Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    fun SelectPlace(id: String){
        viewModelScope.launch {
            try{
                var details = repo.details(id)
                selected.value = details
            }catch( e: Exception){
                Log.e(TAG, " Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }
    fun addPlace(){
        selected.value?.let { localData.value.add(it) }
    }
    fun removePlace(place: Place){
        localData.value.remove(place)
    }
}