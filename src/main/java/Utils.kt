import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import java.io.File

/**
 * Created by Partizanin on 07.06.2017 22:38:03.
 */
class Utils {
    var sData: ArrayList<String> = arrayListOf()
    var progressProperty: SimpleDoubleProperty = SimpleDoubleProperty()
    var trackName: SimpleStringProperty = SimpleStringProperty()


    private val parser = Parser()//add path to file
    private val downloader = Download(sData, progressProperty)


    fun downloadFile(trackList: ObservableList<TrackObject>) {
        return downloader.downloadData2(trackList, trackName)
    }

    private fun isFileExist(filePathName: String): Boolean {
        return File(filePathName).exists()
    }

    fun getListOfTracks(fileLines: List<String>): ArrayList<TrackObject> {
        val trackList = parser.parse(fileLines)

        for (trackObject in trackList) {
            val fileName = "c:\\Users\\topic\\Downloads\\2017-06-07.22-06\\".plus("${trackObject.trackName}.mp3")

            val fileExist = isFileExist(fileName)

            if (fileExist) {
                trackObject.isDownloaded = fileExist
                trackObject.filePath = fileName
            }
        }

        return trackList
    }

    fun isUrlActive(fileUrl: String): Boolean {
        return downloader.isUrlActive(fileUrl)

    }

    fun getFileSize(fileUrl: String): String {
        return downloader.getFileSize(fileUrl)
    }
}