import javafx.application.Platform
import javafx.concurrent.Task
import javafx.event.EventHandler
import javafx.scene.control.*
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
    private val listView: ListView<Thread> by fxid("listVIew")
    private var fileLines: List<String> = ArrayList<String>()

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
                fileLines = db.files[0].readLines(Charsets.UTF_8)
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
            downloadTracks(fileLines)
        }
    }


    fun downloadTracks(fileLines: List<String>) {
        runAsync {
            val task: Task<Unit> = object : Task<Unit>() {
                override fun call() {

                    val listOfFilesUrlsAndNames = utils.getListOfTracks(fileLines)
                    for (track in listOfFilesUrlsAndNames) {
                        val trackName = track.trackName

                        val fileName = "c:\\Users\\topic\\Downloads\\2017-06-07.22-06\\".plus("$trackName.mp3")

                        if (!utils.isFileExist(fileName)) {

                            Platform.runLater { label.text = trackName }

                            utils.downloadFile(track.trackUrl, fileName)

                            if (listOfFilesUrlsAndNames.indexOf(track) == listOfFilesUrlsAndNames.size - 1) {
                                Platform.runLater {
                                    label.text = ""
                                }
                            }
                        } else {
                            println("$trackName is exist")
                        }

                        updateMessage("Loading customers")
                        updateProgress(0.4, 1.0)
                    }
                }

            }
            Thread(task).start()
        }
    }

    private var clicks: Int = 0
}
