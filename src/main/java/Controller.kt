import javafx.application.Platform
import javafx.concurrent.Task
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.control.ProgressIndicator
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.TransferMode
import javafx.scene.layout.AnchorPane
import tornadofx.*


/**
 * Created by Partizanin on 07.06.2017 22:10:22.
 */
class Controller : View("") {

    override val root: AnchorPane by fxml("View.fxml")

    private val button: Button by fxid("button")
    private val label: Label by fxid("label")
    private val pBar: ProgressBar by fxid("pBar")
    private val pIndicator: ProgressIndicator by fxid("pIndicator")
    private val image: ImageView by fxid("image")
    private var utils = Utils()

    init {
        image.isVisible = false
        pBar.progressProperty().bind(utils.progressProperty)
        pIndicator.progressProperty().bind(utils.progressProperty)
        root.onDragOver = EventHandler {
            if (!image.isVisible) {
                image.isVisible = true
            }
            if (it.dragboard.hasFiles()) {
                it.acceptTransferModes(TransferMode.COPY)
            } else {
                it.consume()
            }
        }

        root.onDragDropped = EventHandler {
            val db = it.dragboard
            var success = false
            if (db.hasFiles()) {
                success = true
                downloadTracks(db.files[0].readLines(Charsets.UTF_8))
            }
            it.isDropCompleted = success
            it.consume()


        }

        root.setOnDragExited {
            if (image.isVisible) {
                image.isVisible = false
            }
        }


        image.image = Image("drag-drop.jpg")
        button.setOnAction {
            clicks++
            label.text = "clicked: $clicks"
            val toDouble = ((clicks.toDouble() * 10) / 100)
            pBar.progress = toDouble
            pIndicator.progress = toDouble
        }
    }


    fun downloadTracks(fileLines: List<String>) {
        runAsync {
            val task: Task<Unit> = object : Task<Unit>() {
                override fun call() {

                    val listOfFilesUrlsAndNames = utils.getListOfFilesUrlsAndNames(fileLines)
                    for (filesUrlsAndNames in listOfFilesUrlsAndNames) {
                        val trackName = filesUrlsAndNames[0]
                        Platform.runLater { label.text = trackName }
                        val fileName = "c:\\Users\\topic\\Downloads\\2017-06-07.22-06\\".plus(trackName)
                        val url = filesUrlsAndNames[1]
                        utils.downloadFile(url, fileName)

                        if (listOfFilesUrlsAndNames.indexOf(filesUrlsAndNames) == listOfFilesUrlsAndNames.size - 1) {
                            label.text = ""
                        }
                    }

                    updateMessage("Loading customers")
                    updateProgress(0.4, 1.0)
                }

            }
            Thread(task).start()
        }
    }

    private var clicks: Int = 0
}
