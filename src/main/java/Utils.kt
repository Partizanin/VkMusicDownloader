import javafx.beans.property.SimpleDoubleProperty

/**
 * Created by Partizanin on 07.06.2017 22:38:03.
 */
class Utils() {
    var sData: ArrayList<String> = arrayListOf()
    var progressProperty: SimpleDoubleProperty = SimpleDoubleProperty()

    private val parser = Parser()//add path to file
    private val downloader = Download(sData, progressProperty)

    constructor(sData: ArrayList<String>) : this() {
        this.sData = sData
        this.progressProperty = progressProperty
    }

    fun downloadFile(url: String, fileName: String) {
        downloader.downloadData2(url, fileName)
    }

    fun getListOfFilesUrlsAndNames(fileLines: List<String>): ArrayList<Array<String>> {
        return parser.parse(fileLines)
    }
}