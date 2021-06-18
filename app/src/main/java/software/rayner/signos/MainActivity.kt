package software.rayner.signos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import software.rayner.signos.ui.theme.SignosTheme
import software.rayner.signos.ui.theme.components.AddressItem

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignosTheme {
                SignosApp(AddressViewModel())
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SignosApp(viewModel: AddressViewModel) {
    Scaffold(topBar = {}, bottomBar = { SignosBottomBar(viewModel) }) {
        SignosAppBody(viewModel)
    }

}

@ExperimentalMaterialApi
@Composable
fun SignosAppBody(viewModel: AddressViewModel) {
    Surface(color = Color.LightGray) {
        var places =
            if (viewModel.searchMode.value) viewModel.remoteData.value else viewModel.localData.value

        Column(Modifier.padding(8.dp)) {
            if (viewModel.searchMode.value) {
                LocationSearchBar(viewModel)
            }
            if (places.isEmpty()) {
                if (viewModel.searchMode.value) {
                    Text("No Results")
                } else {
                    Text("No saved places")
                }

            }
            LazyColumn(Modifier.fillMaxSize()) {
                items(places) { place ->
                    GetAddressItem(viewModel, place)
                }
            }
        }
    }
}

@Composable
fun SignosBottomBar(viewModel: AddressViewModel) {
    BottomNavigation(elevation = 12.dp) {
        BottomNavigationItem(icon = {
            Icon(
                Icons.Filled.Home,
                contentDescription = "Home",
                tint = Color.Black
            )
        }, selected = !viewModel.searchMode.value, onClick = { viewModel.searchMode.value = false })

        BottomNavigationItem(icon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color.Black
            )
        }, selected = viewModel.searchMode.value, onClick = { viewModel.searchMode.value = true })
    }
}

@Composable
fun LocationSearchBar(viewModel: AddressViewModel) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    Row(Modifier.fillMaxWidth()) {
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            singleLine = true
        )
        IconButton(onClick = {
            viewModel.GetPlaces(textState.value.text)
        }) {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color.Blue
            )
        }
    }

}

@ExperimentalMaterialApi
@Composable
fun GetAddressItem(viewModel: AddressViewModel, place: Place) {
    val selected = viewModel.selected.value?.id.equals(place.id)
    var model = if (selected) viewModel.selected?.value else place
    if (model == null) model = place

    if (viewModel.searchMode.value) {
        AddressItem(
            model,
            !viewModel.searchMode.value,
            selected,
            {
                place.id?.let { viewModel.SelectPlace(it) }
            },
            { viewModel.addPlace() })
    } else {
        val dismissState = rememberDismissState()
        SwipeToDismiss(
            state = dismissState,
            background = {
                if (dismissState.isDismissed(DismissDirection.StartToEnd)) viewModel.removePlace(
                    place
                )
            }) {
            viewModel.selected.value?.let {
                AddressItem(
                    model,
                    !viewModel.searchMode.value,
                    selected,
                    {
                        place.id?.let { viewModel.SelectPlace(it) }
                    },
                    { })
            }
        }
    }
}
