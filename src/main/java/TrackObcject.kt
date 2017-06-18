/**
 * Created by Partizanin on 19.06.2017 00:28:21.
 */
data class TrackObcject(val trackName: String, val trackUrl: String) {
    var trackSizeBytes: Int = 0
    var isDownloaded: Boolean = false
}