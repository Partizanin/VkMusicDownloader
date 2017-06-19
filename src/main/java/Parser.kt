/**
 * Created by Partizanin on 07.06.2017 15:06:16.
 */
class Parser {

    fun parse(fileLines: List<String>): ArrayList<TrackObject> {
        val list = ArrayList<TrackObject>(20)
        var i = 0

        while (i < fileLines.size - 1) {
            val currentLine = fileLines[i]

            if (!currentLine.contains("#EXTM3U")) {
                val trackObcject = TrackObject(getName(currentLine), fileLines[i + 1])
                list.add(trackObcject)
                i += 2
            } else {
                i++
            }


        }
        return list
    }

    private fun getName(line: String): String {

        var result: String = line.substring(line.indexOf(",") + 1)

        result = result.replace("/", " ")
        return result
    }
}