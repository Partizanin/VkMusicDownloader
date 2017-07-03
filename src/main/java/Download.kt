import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.concurrent.Task
import sun.net.www.protocol.https.HttpsURLConnectionImpl
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.ConnectException
import java.net.URL

/**
 * Created by Partizanin on 06.06.2017 23:44:03.
 */

class Download(private var progressProp: SimpleDoubleProperty) {

    fun downloadData(trackList: ObservableList<TrackObject>, trackNameProp: SimpleStringProperty, refreshProp: SimpleBooleanProperty) {

        for (i in 0 until trackList.size) {
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

            val task: Task<Unit> = object : Task<Unit>() {
                override fun call() {
                    track.isDownloaded = true
                    trackList[i] = track
                    updateMessage("Loading customers")
                    updateProgress(0.4, 1.0)
                }

            }

            val thread = Thread(task)
            thread.isDaemon = true
            thread.start()
            refreshProp.set(true)

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
        var result = false

        val url1 = URL(url)
        val urlConnection = url1.openConnection()
        try {

            val responseCode = (urlConnection as HttpsURLConnectionImpl).responseCode
            if (responseCode == -1 || responseCode == 404) {
                return false
            } else if (responseCode == 200) {
                return true
            }

        } catch (e: ConnectException) {
            println("Connection timed out")
        }

        return result

    }
}