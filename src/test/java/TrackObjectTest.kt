import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Created by Partizanin on 28.06.2017 20:38:13.
 */
internal class TrackObjectTest {
    private var trackObject = TrackObject("", "")

    @Test
    fun setTrackSizeBytes() {
        trackObject.trackSizeBytes = "6123036444"
        assertEquals(trackObject.trackSizeBytes, "6123 036444")

        trackObject.trackSizeBytes = "612036444"
        assertEquals(trackObject.trackSizeBytes, "612 036444")

        trackObject.trackSizeBytes = "61036444"
        assertEquals(trackObject.trackSizeBytes, "61 036444")

        trackObject.trackSizeBytes = "6036444"
        assertEquals(trackObject.trackSizeBytes, "6 036444")

        trackObject.trackSizeBytes = "636444"
        assertEquals(trackObject.trackSizeBytes, "636444")
    }

}