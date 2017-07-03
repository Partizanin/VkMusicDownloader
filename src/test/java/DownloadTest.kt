import javafx.beans.property.SimpleDoubleProperty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Created by Partizanin on 03.07.2017 10:42:11.
 */
internal class DownloadTest {

    private var downloader = Download(SimpleDoubleProperty())
    @Test
    fun isUrlActive() {
        var url = "https://cs9-5v4.userapi.com/p20/ca2a54ce88757f.mp3?extra=pQbSWeeHCQR2Gpgp0feXkTURpiK9OYi-l5am9TW-leCFE16MGKiIOhsHltqnQvrVGmR-hFdz_nDbeguKsPhRKUtlQSLTHA_fiOWJvJnTPT-LpBgeGW_FnEl_qGF77y-SRUbIl8fJa8wu8Yo"

        assertEquals(downloader.isUrlActive(url), false)
        url = "https://cs9-5v4.userapi.com/p20/19c99792a50818.mp3?extra=UoxCq86DdkcPAnbTerm7sell7HOi78XIRBVHf6mcNuzUAjwIIgnhYGqzGeki_Df_BCEq4hR5c3azocbfuoTvPGqsv8jbq_Ba75f9HXb9FwFdpBoMWlhAzGYK6FEfHgDPXWmArrsKQy67gBQ\n"
        assertEquals(downloader.isUrlActive(url), true)
    }

}