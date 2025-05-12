package com.app.travelsync.ui.view

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.travelsync.R
import com.app.travelsync.domain.model.Trip
import com.app.travelsync.ui.viewmodel.GalleryViewModel
import com.app.travelsync.utils.copyUriInternal
import com.app.travelsync.utils.saveBitmapInternal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    tripId: Int,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: GalleryViewModel = hiltViewModel()
    val trips by viewModel.trips.collectAsState(initial = emptyList())
    val trip = trips.find { it.tripId == tripId }

    var showSheet by remember { mutableStateOf(false) }
    var zoomUri by remember { mutableStateOf<Uri?>(null) }
    var infoUri by remember { mutableStateOf<Uri?>(null) }
    var pendingDelete by remember { mutableStateOf<Uri?>(null) }

    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bmp -> bmp?.let { viewModel.addImage(tripId, saveBitmapInternal(context, it)) } }

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { viewModel.addImage(tripId, copyUriInternal(context, it)) } }

    if (trip == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading trip...")
        }
        return
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(trip.title) },
                navigationIcon = { IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { showSheet = true },
                shape = CircleShape,
                containerColor = colorResource(id = R.color.backgroundIcon),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        if (trip.images.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize(), Alignment.Center) {
                Text("No images yet") //no_images
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(padding).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(trip.images) { uri ->
                    Box {

                        AsyncImage(
                            model = uri, contentDescription = null,
                            modifier = Modifier.aspectRatio(1f).fillMaxWidth()
                                .clickable { zoomUri = uri },
                            contentScale = ContentScale.Crop
                        )

                        Row(
                            Modifier.align(Alignment.TopEnd)
                                .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(.4f))
                        ) {
                            IconButton(onClick = { zoomUri = uri }, modifier = Modifier.size(28.dp)) {
                                Icon(
                                    Icons.Default.Search, contentDescription = "Zoom",
                                    tint = MaterialTheme.colorScheme.surface)
                            }
                            IconButton(onClick = { infoUri = uri }, modifier = Modifier.size(28.dp)) {
                                Icon(
                                    Icons.Default.Info, contentDescription = "Info",
                                    tint = MaterialTheme.colorScheme.surface)
                            }
                            IconButton(
                                onClick = { pendingDelete = uri },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete, contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.surface)
                            }
                        }
                    }
                }
            }
        }
    }


    pendingDelete?.let { uri ->
        AlertDialog(
            onDismissRequest = { pendingDelete = null },
            title  = { Text(stringResource(id = R.string.delete_image)) },
            text   = { Text(stringResource(id = R.string.info_image)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteImage(uri)
                    pendingDelete = null
                }) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { pendingDelete = null }) { Text(stringResource(id = R.string.cancel_gallery)) }
            }
        )
    }


    if (showSheet) {
        ModalBottomSheet(onDismissRequest = { showSheet = false }) {
            ListItem(headlineContent = { Text(stringResource(id = R.string.take_photo)) },
                modifier = Modifier.clickable {
                    takePicture.launch(null); showSheet = false
                })
            ListItem(headlineContent = { Text(stringResource(id = R.string.choose_photo)) },
                modifier = Modifier.clickable {
                    pickImage.launch("image/*"); showSheet = false
                })
        }
    }


    zoomUri?.let { uri ->
        AlertDialog(onDismissRequest = { zoomUri = null },
            confirmButton = {},
            text = {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(uri).crossfade(true).build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            })
    }


    infoUri?.let { uri ->
        val meta by produceState(initialValue = "Loading…", uri) {
            value = getMeta(context, uri)
        }
        AlertDialog(
            onDismissRequest = { infoUri = null },
            confirmButton = { TextButton(onClick = { infoUri = null }) { Text(stringResource(id = R.string.close_image)) } },
            title = { Text("Image info") },
            text  = { Text(meta) }
        )
    }
}


private suspend fun getMeta(context: Context, uri: Uri): String =
    withContext(Dispatchers.IO) {
        runCatching {
            uri.path?.let { path ->
                val file = File(path)
                val sizeKb = file.length() / 1024
                val date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    .format(Date(file.lastModified()))

                "URI:\n$uri\n\nSize: $sizeKb KB\nDate: $date"
            }
        }.getOrNull() ?: "Could not read metadata"
    }