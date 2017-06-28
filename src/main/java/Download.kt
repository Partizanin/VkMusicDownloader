import javafx.application.Platform
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import sun.net.www.protocol.https.HttpsURLConnectionImpl
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL

/**
 * Created by Partizanin on 06.06.2017 23:44:03.
 */

class Download() {

    var progressProp: SimpleDoubleProperty = SimpleDoubleProperty()
    var sData: ArrayList<String> = arrayListOf()



    constructor(sData: ArrayList<String>, progressProperty: SimpleDoubleProperty) : this() {
        this.progressProp = progressProperty
        this.sData = sData
    }

    fun downloadData2(trackList: ObservableList<TrackObject>, trackNameProp: SimpleStringProperty) {


        for (i in 0..trackList.size) {
            val track = trackList[i]
            val trackName = track.trackName
            trackNameProp.set(trackName)
            val filePathAndName = "c:\\Users\\topic\\Downloads\\2017-06-07.22-06\\".plus("$trackName.mp3")

            if (track.isDownloaded) {
                println("$trackName is exist")
            } else {

                if (isUrlActive(track.trackUrl)) {
                    val urlConnection = URL(track.trackUrl).openConnection()

                    print("Start download $trackName")
                    val contentSize = urlConnection.contentLengthLong
                    println(" $contentSize bytes")
                    val inStream = urlConnection.getInputStream()
                    val outStream: OutputStream = FileOutputStream(filePathAndName)

                    val buffer = ByteArray(1024)

                    var length: Int = inStream.read(buffer)
                    //copy the file content in bytes

                    var writed = length

                    do {
                        writed += length


                        val countPercent = countPercent(contentSize, writed.toLong())
                        progressProp.set(countPercent.toDouble() / 100)

                        outStream.write(buffer, 0, length)

                        length = inStream.read(buffer)

                    } while (length > 0)


                    inStream.close()
                    outStream.close()
                }
            }
            Platform.runLater {
                track.trackStatus = "Downloaded"
                trackList[i] = track
            }
        }
    }

    private fun countPercent(fullSize: Long, alreadyWrite: Long): Long {
        return alreadyWrite * 100 / fullSize
    }

    fun getFileSize(filePath: String): String {
        var result: String = ""
        if (filePath.contains("http")) {
            if (isUrlActive(filePath)) {
                val openConnection = URL(filePath).openConnection()

                result = openConnection.contentLength.toString()
            }

        } else if (filePath.contains(":\\")) {
            val file = File(filePath)
            result = file.length().toInt().toString()
        }
        return result
    }

    fun isUrlActive(url: String): Boolean {
        val urlConnection = URL(url).openConnection()

        return (urlConnection as HttpsURLConnectionImpl).responseCode != 404
    }
}