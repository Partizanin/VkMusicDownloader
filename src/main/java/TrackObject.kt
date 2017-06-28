/**
 * Created by Partizanin on 19.06.2017 00:28:21.
 */

data class TrackObject(val trackName: String, val trackUrl: String) {
    var trackSizeBytes: String = ""
        set(value) {
            field = setTrackSize(value)
        }

    private fun setTrackSize(value: String): String {
        var result = ""
        if (value.length > 6) {
            result = value.substring(0, value.length - 6) + " " + value.substring(value.length - 6, value.length)
        } else {
            result = value
        }
        return result
    }

    var isDownloaded: Boolean = false
        set(value) {
            if (value) {
                trackStatus = "Downloaded"
            }
        }

    var filePath = ""
    var trackStatus: String = "noStatus"// noStatus,badUrl,Downloaded,readyForDownloading
    override fun toString(): String {
        return "$trackName  | trackSize $trackSizeBytes bytes | ${if (isDownloaded) "Downloaded" else trackStatus}"
    }


}