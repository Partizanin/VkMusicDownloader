import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.event.EventHandler
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.DataFormat
import javafx.scene.input.DragEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.AnchorPane
import tornadofx.*


/**
 * Created by Partizanin on 07.06.2017 22:10:22.
 */
class Controller : View("") {

    override val root: AnchorPane by fxml("View.fxml")

    private val downloadButton: Button by fxid("downloadButton")
    private val label: Label by fxid("label")
    private val pBar: ProgressBar by fxid("pBar")
    private val pIndicator: ProgressIndicator by fxid("pIndicator")
    private val image: ImageView by fxid("image")
    private val listView: ListView<TrackObject> by fxid("listView")

    private var utils = Utils()
    var trackList: ObservableList<TrackObject> = FXCollections.observableArrayList()

    init {
        listView.items = trackList
        downloadButton.isDisable = true

        image.image = Image("drag-drop.jpg")
        image.isVisible = false
        pBar.progressProperty().bind(utils.progressProperty)
        pIndicator.progressProperty().bind(utils.progressProperty)


        utils.trackName.onChange {
            Platform.runLater { label.text = utils.trackName.value }
        }

        root.onDragOver = EventHandler { handleDraggedContent(it) }

        root.onDragDropped = EventHandler { getDroppedContent(it) }

        root.setOnDragExited {
            if (image.isVisible) {
                image.isVisible = false
            }
        }


        downloadButton.setOnAction { downloadTracks() }
    }

    private fun getDroppedContent(it: DragEvent) {

        Runnable {
            trackList.clear()
            val db = it.dragboard
            var success = false
            if (db.hasFiles()) {
                success = true

                val fileLines = db.files[0].readLines(Charsets.UTF_8)

                runAsync {
                    val tempTrackList = utils.getListOfTracks(fileLines)
                    trackList.addAll(tempTrackList)
                }


                downloadButton.isDisable = !trackList.filter { it.trackStatus == "readyForDownloading" }.isNotEmpty()

            }
            it.isDropCompleted = success
            it.consume()

        }.run()


    }

    private fun handleDraggedContent(it: DragEvent) {
        if (!image.isVisible) {
            image.isVisible = true
        }

        var isTrueContent = false

        if (it.dragboard.hasFiles()) {
            when (it.dragboard.files[0].extension) {
                "m3u" -> {
                    isTrueContent = true
                }
                "txt" -> {
                    isTrueContent = true
                }
                else -> {
                    isTrueContent = false
                }
            }
        } else if (it.dragboard.contentTypes.elementAt(0) == DataFormat.PLAIN_TEXT) {
            isTrueContent = false/*true todo: add feature dropped text*/
        }

        if (isTrueContent) {
            it.acceptTransferModes(TransferMode.COPY)
        } else {
            it.consume()
        }
    }


    fun downloadTracks() {
        println(trackList)

        runAsync {
            val task: Task<Unit> = object : Task<Unit>() {
                override fun call() {
                    utils.downloadFile(trackList, utils)
                    updateMessage("Loading customers")
                    updateProgress(0.4, 1.0)
                }

            }
            Thread(task).start()
        }
    }

}
