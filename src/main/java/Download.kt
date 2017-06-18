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


    constructor(sData: ArrayList<String>, progressProperty: SimpleDoubleProperty) : this() {
        this.progressProp = progressProperty
        this.sData = sData
    }

    fun downloadData2(url: String, fileName: String) {

        print("Start download ${fileName.substringAfter("test\\")} ")
        val urlConnection = URL(url).openConnection()
        val contentSize = urlConnection.contentLengthLong
        println("$contentSize bytes")
        val inStream = urlConnection.getInputStream()
        val outStream: OutputStream = FileOutputStream(fileName)

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

    private fun countPercent(fullSize: Long, alreadyWrite: Long): Long {
        return alreadyWrite * 100 / fullSize
    }

    fun getFileSize(fileUrl: String): Int {
        return URL(fileUrl).openConnection().contentLength
    }
}