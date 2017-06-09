import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.control.ProgressIndicator
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

    init {
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