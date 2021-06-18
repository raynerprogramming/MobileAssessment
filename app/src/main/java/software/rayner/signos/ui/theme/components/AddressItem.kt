package software.rayner.signos.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import software.rayner.signos.Place

@Composable
fun AddressItem(
    item: Place,
    saved: Boolean,
    selected: Boolean,
    onClick: () -> Unit,
    onButtonClick: () -> Unit
) {
    Box(
        Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .clickable { onClick() }) {
        Column {
            Text("${item.address}", Modifier.padding(8.dp))
            if (selected) {
                item.name?.let { name -> Text("$name", Modifier.padding(4.dp)) }
                item.phone?.let { phone -> Text("$phone", Modifier.padding(4.dp)) }
                item.type?.let { type -> Text("$type", Modifier.padding(4.dp)) }
                if (!saved) {
                    IconButton(onClick = onButtonClick) {
                        Icon(
                            Icons.Filled.AddCircle,
                            contentDescription = "Add",
                            tint = Color.Blue
                        )
                    }
                }
            }

        }
    }

}

@Preview
@Composable
fun AddressItemSelectedPreview() {
    var vm = Place("1", "123 test lane", "test", "GYM", "111-222-3333")
    AddressItem(item = vm, false, true, {}, {})
}

@Preview
@Composable
fun AddressItemPreview() {
    var vm = Place("1", "123 test lane", "test", "GYM", "111-222-3333")
    AddressItem(item = vm, false, false, {}, {})
}

@Preview
@Composable
fun SavedAddressItemSelectedPreview() {
    var vm = Place("1", "123 test lane", "test", "GYM", "111-222-3333")
    AddressItem(item = vm, true, true, {}, {})
}

@Preview
@Composable
fun SavedAddressItemPreview() {
    var vm = Place("1", "123 test lane", "test", "GYM", "111-222-3333")
    AddressItem(item = vm, true, false, {}, {})
}