import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.io.core.internal.DangerousInternalIoApi
import tk.zwander.common.model.DecryptModel
import tk.zwander.common.model.DownloadModel
import tk.zwander.common.view.*
import javax.swing.UIManager
import kotlin.time.ExperimentalTime

@ExperimentalTime
@OptIn(DangerousInternalIoApi::class)
fun main() = Window(
    title = "Samsung Firmware Downloader",
    icon = getImage("icon.png")
) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val page = remember { mutableStateOf(Page.DOWNLOADER) }

    val downloadModel = remember { DownloadModel() }
    val decryptModel = remember { DecryptModel() }

    CustomMaterialTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth()
                ) {
                    TabView(page)

                    Divider(
                        thickness = 1.dp,
                        color = MaterialTheme.colors.onSurface
                    )

                    Spacer(Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(8.dp)
                    ) {
                        when (page.value) {
                            Page.DOWNLOADER -> DownloadView(downloadModel)
                            Page.DECRYPTER -> DecryptView(decryptModel)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                FooterView()
            }
        }
    }
}