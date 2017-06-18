import javafx.beans.property.SimpleDoubleProperty
import java.io.File

/**
 * Created by Partizanin on 07.06.2017 22:38:03.
 */
class Utils() {
    var sData: ArrayList<String> = arrayListOf()
    var progressProperty: SimpleDoubleProperty = SimpleDoubleProperty()
    var trackName = ""


    private val parser = Parser()//add path to file
    private val downloader = Download(sData, progressProperty)

    constructor(sData: ArrayList<String>) : this() {
        this.sData = sData
        this.progressProperty = progressProperty
    }

    fun downloadFile(url: String, fileName: String) {
        downloader.downloadData2(url, fileName)
    }

    fun isFileExist(filePathName: String): Boolean {
        return File(filePathName).exists()
    }

    fun getListOfTracks(fileLines: List<String>): ArrayList<TrackObcject> {
        return parser.parse(fileLines)
    }

    fun getFileSize(fileUrl: String): Int {
        return downloader.getFileSize(fileUrl)
    }
}