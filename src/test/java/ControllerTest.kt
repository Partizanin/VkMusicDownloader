import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Created by Partizanin on 05.07.2017 12:48:55.
 */
internal class ControllerTest {

    @Test
    fun comp() {
        val o1 = TrackObject("", "")
        val o2 = TrackObject("", "")


        o1.trackStatus = "readyForDownloading"

        o2.trackStatus = "badUrl"
        assertEquals(comp(o1, o2), -1)
        assertEquals(comp(o2, o1), 1)

        o2.trackStatus = "Downloaded"
        assertEquals(comp(o1, o2), -1)
        assertEquals(comp(o2, o1), 1)

        o2.trackStatus = "noStatus"
        assertEquals(comp(o1, o2), -1)
        assertEquals(comp(o2, o1), 1)

        o2.trackStatus = "readyForDownloading"
        assertEquals(comp(o1, o2), 0)
        assertEquals(comp(o2, o1), 0)

        /*o1 test end readyForDownloading*/
        /*o1 test  badUrl*/

        o1.trackStatus = "badUrl"

        o2.trackStatus = "badUrl"
        assertEquals(comp(o1, o2), 0)

        o2.trackStatus = "Downloaded"
        assertEquals(comp(o1, o2), -1)
        assertEquals(comp(o2, o1), 1)

        o2.trackStatus = "noStatus"
        assertEquals(comp(o1, o2), 1)
        assertEquals(comp(o2, o1), 1)

        /*o1test end for badUrl*/
        /*o1 test  Downloaded*/

        o1.trackStatus = "Downloaded"


        o2.trackStatus = "Downloaded"
        assertEquals(comp(o1, o2), 0)

        o2.trackStatus = "noStatus"
        assertEquals(comp(o1, o2), -1)
        assertEquals(comp(o2, o1), 1)

        /*o1test end for Downloaded*/
        /*o1 test  noStatus*/

        o1.trackStatus = "noStatus"

        o2.trackStatus = "noStatus"
        assertEquals(comp(o1, o2), 0)

        /*o1test end for noStatus*/

    }
    // noStatus,badUrl,Downloaded,readyForDownloading

    private fun comp(o1: TrackObject, o2: TrackObject): Int {
        var result = 3
        when (o1.trackStatus) {
            "readyForDownloading" -> {
                when (o2.trackStatus) {
                    "readyForDownloading" -> result = 0
                    else -> result = -1
                }
            }
            "badUrl" -> {
                when (o2.trackStatus) {
                    "readyForDownloading" -> result = 1
                    "badUrl" -> result = 0
                    "noStatus" -> result = 1
                    else -> result = -1
                }
            }
            "Downloaded" -> {
                when (o2.trackStatus) {
                    "readyForDownloading" -> result = 1
                    "badUrl" -> result = 1
                    "Downloaded" -> result = 0
                    "noStatus" -> result = -1
                }
            }
            "noStatus" -> {
                when (o2.trackStatus) {
                    "noStatus" -> result = 0
                    "Downloaded" -> result = 1
                    else -> result = 1
                }
            }
        }

        return result
    }

}