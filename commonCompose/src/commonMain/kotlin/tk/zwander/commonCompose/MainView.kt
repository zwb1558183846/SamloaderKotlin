package tk.zwander.commonCompose

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.utils.io.core.internal.*
import tk.zwander.common.model.DecryptModel
import tk.zwander.common.model.DownloadModel
import tk.zwander.common.model.HistoryModel
import tk.zwander.commonCompose.view.components.CustomMaterialTheme
import tk.zwander.commonCompose.view.components.FooterView
import tk.zwander.commonCompose.view.components.Page
import tk.zwander.commonCompose.view.components.TabView
import tk.zwander.commonCompose.view.pages.DecryptView
import tk.zwander.commonCompose.view.pages.DownloadView
import tk.zwander.commonCompose.view.pages.HistoryView
import kotlin.time.ExperimentalTime

val downloadModel = DownloadModel()
val decryptModel = DecryptModel()
val historyModel = HistoryModel()

/**
 * The main UI view.
 */
@OptIn(DangerousInternalIoApi::class)
@ExperimentalTime
@Composable
fun MainView() {
    val page = remember { mutableStateOf(Page.DOWNLOADER) }

    val scrollState = rememberScrollState(0)

    CustomMaterialTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TabView(page)

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .widthIn(max = 1200.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Crossfade(
                        targetState = page.value,
                        animationSpec = tween(300)
                    ) {
                        when (it) {
                            Page.DOWNLOADER -> DownloadView(downloadModel, scrollState)
                            Page.DECRYPTER -> DecryptView(decryptModel, scrollState)
                            Page.HISTORY -> HistoryView(
                                historyModel,
                                { model, region, fw ->
                                    downloadModel.manual = true
                                    downloadModel.osCode = ""
                                    downloadModel.model = model
                                    downloadModel.region = region
                                    downloadModel.fw = fw

                                    page.value = Page.DOWNLOADER
                                },
                                { model, region, fw ->
                                    decryptModel.fileToDecrypt = null
                                    decryptModel.model = model
                                    decryptModel.region = region
                                    decryptModel.fw = fw

                                    page.value = Page.DECRYPTER
                                }
                            )
                        }
                    }
                }

                FooterView()
            }
        }
    }
}