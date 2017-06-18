import javafx.beans.property.SimpleDoubleProperty
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL

/**
 * Created by Partizanin on 06.06.2017 23:44:03.
 */

class Download() {

    var progressProp: SimpleDoubleProperty = SimpleDoubleProperty()
    var sData: ArrayList<String> = arrayListOf()
    var trackName: String = ""


    constructor(sData: ArrayList<String>, progressProperty: SimpleDoubleProperty, trackName: String) : this() {
        this.progressProp = progressProperty
        this.sData = sData
        this.trackName = trackName
    }

    /*fun downloadData(url: String, fileName: String) {
        print("Start download ${fileName.substringAfter("test\\")} ")
        val fileNameFinal = fileName.plus(".mp3")
        val website: URL = URL(url)
        val openStream = website.openStream()
        val rbc: ReadableByteChannel = Channels.newChannel(openStream)
        print(openStream.available())
        val fos: FileOutputStream = FileOutputStream(fileNameFinal)
        fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
        print(" Finish download \n")
    }*/

    fun downloadData2(url: String, fileName: String) {
        print("Start download ${fileName.substringAfter("test\\")} ")
        trackName = fileName
        val fileNameFinal = fileName.plus(".mp3")
        val urlConnection = URL(url).openConnection()
        val contentSize = urlConnection.contentLengthLong
        println(contentSize)
        val inStream = urlConnection.getInputStream()
        val outStream: OutputStream = FileOutputStream(fileNameFinal)

        val buffer = ByteArray(1024)

        var length: Int = inStream.read(buffer)
        //copy the file content in bytes

        var writed = length

        do {
            writed += length


            val countPercent = countPercent(contentSize, writed.toLong())
            progressProp.set(countPercent.toDouble() / 100)
//            println("$countPercent% $writed")

            outStream.write(buffer, 0, length)

            length = inStream.read(buffer)

        } while (length > 0)

        inStream.close()
        outStream.close()

    }

    private fun countPercent(fullSize: Long, alreadyWrite: Long): Long {
        return alreadyWrite * 100 / fullSize
    }

/*
    fun readFile(filePart: String): List<String> {
        return File("C:\\Users\\topic\\Downloads\\fore phone 2017-06-06 15-39.m3u").readLines()
    }
*/
}