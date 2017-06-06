import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

/**
 * Created by Partizanin on 06.06.2017 23:44:03.
 */

fun main(args: Array<String>) {
    val url = "https://cs9-5v4.userapi.com/p20/f03d436a0e06f4.mp3?extra=RrwSeJacyXEA7fsXk6Nv2PPSUwJPwdN0VELwTNd_DZLUC9na2CtvDflxyMcKX7Km4bTQr4QUx5O-WlxgZ4ZFlcSizap9WM04BoOtzeOrXR_jJ9StdAtdJsh4WjMSOiEfKhFrmgu2Ub56Ng"
    val d = Download(url)
    d.downloadData()
}

class Download(val sData: String) {
    fun downloadData() {
        val website: URL = URL(sData)
        val rbc: ReadableByteChannel = Channels.newChannel(website.openStream())
        val fos: FileOutputStream = FileOutputStream("test.mp3")
        fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
        /*todo: заміряти час скачувяння саме цього файлу і спробувати знайти найшвидший метод скачування*/
    }

}