/**
 * Created by Partizanin on 07.06.2017 15:12:39.
 */
fun main(args: Array<String>) {

    val download = Download()

    val parser = Parser(download.readFile())
    val parse = parser.parse()

    for (i in 0..0) {
        val filePath = "D:\\Illia\\Java\\Projects\\VkMusicDownloader\\src\\test\\".plus(parse[i][0])
        download.downloadData2(parse[i][1], filePath)
    }

}
