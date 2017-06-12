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

    val button: Button by fxid("button")
    val label: Label by fxid("label")
    val pBar: ProgressBar by fxid("pBar")
    val pIndicator: ProgressIndicator by fxid("pIndicator")
    val image: ImageView by fxid("image")

    init {
        image.isVisible = false

        root.onDragOver = EventHandler {
            if (!image.isVisible) {
                image.isVisible = true
            }
            if (it.dragboard.hasFiles()) {
                println("hasFile")
                it.acceptTransferModes(TransferMode.COPY)
            } else {
                it.consume()
            }
        }

        root.onDragDropped = EventHandler {
            val db = it.dragboard
            var success = false
            println("hasFile2")
            if (db.hasFiles()) {
                success = true
                var filePath: String?
                for (file in db.files) {
                    filePath = file.absolutePath
                    println(filePath)
                }
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


    private var clicks: Int = 0
}