/**
 * Created by Partizanin on 19.06.2017 00:28:21.
 */
data class TrackObject(val trackName: String, val trackUrl: String) {
    var trackSizeBytes: String = ""
    var isDownloaded: Boolean = false
        set(value) {
            if (value) {
                trackStatus = "Downloaded"
            }
        }

    var trackStatus: String = "readyForDownloading"// badUrl
    override fun toString(): String {
        return "$trackName  | trackSize $trackSizeBytes bytes | ${if (isDownloaded) "Downloaded" else trackStatus}"
    }



}