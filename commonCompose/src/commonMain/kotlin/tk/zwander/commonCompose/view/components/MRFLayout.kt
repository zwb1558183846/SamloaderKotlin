package tk.zwander.commonCompose.view.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import tk.zwander.common.model.BaseModel
import tk.zwander.common.model.DownloadModel
import tk.zwander.commonCompose.util.DPScale

/**
 * A common container for the model, region, and firmware text inputs used in [DownloadView] and [DecryptView]
 * @param model the view model.
 * @param canChangeOption whether the model and region fields should be editable.
 * @param canChangeFirmware whether the firmware field should be editable.
 * @param showFirmware whether to show the firmware field.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MRFLayout(model: BaseModel, canChangeOption: Boolean, canChangeFirmware: Boolean, showFirmware: Boolean = true) {
    val fontScale = LocalDensity.current.fontScale
    val size = remember { mutableStateOf(0.dp) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
            .onSizeChanged { size.value = it.width.dp }
    ) {
        val modelField = @Composable() {
            OutlinedTextField(
                value = model.model,
                onValueChange = {
                    model.model = it.toUpperCase().trim()
                    if ((model is DownloadModel && !model.manual)) {
                        model.fw = ""
                        model.osCode = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Model (e.g. SM-N986U1)") },
                readOnly = !canChangeOption,
                keyboardOptions = KeyboardOptions(KeyboardCapitalization.Characters),
                singleLine = true
            )
        }

        val regionField = @Composable() {
            OutlinedTextField(
                value = model.region,
                onValueChange = {
                    model.region = it.toUpperCase().trim()
                    if ((model is DownloadModel && !model.manual)) {
                        model.fw = ""
                        model.osCode = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Region (e.g. XAA)") },
                readOnly = !canChangeOption,
                keyboardOptions = KeyboardOptions(KeyboardCapitalization.Characters),
                singleLine = true
            )
        }

        if (size.value / fontScale * DPScale.dpScale >= 800.dp) {
            Row {
                Box(
                    modifier = Modifier.weight(0.6f, true)
                ) {
                    modelField()
                }

                Spacer(Modifier.size(8.dp))

                Box(
                    modifier = Modifier.weight(0.4f, true)
                ) {
                    regionField()
                }
            }
        } else {
            Column {
                modelField()

                Spacer(Modifier.size(8.dp))

                regionField()
            }
        }
    }

    if (showFirmware) {
        Spacer(Modifier.size(8.dp))

        OutlinedTextField(
            value = model.fw,
            onValueChange = { model.fw = it.toUpperCase().trim() },
            label = { Text("Firmware (PDA/CSC/CP/AP)") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = !canChangeFirmware,
            keyboardOptions = KeyboardOptions(KeyboardCapitalization.Characters),
            singleLine = true
        )
    }
}