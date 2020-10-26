import org.junit.Assert
import org.junit.Test

internal class RequestParametersBuilderTest {
    @Test
    fun `create correct string with params`() {
        Assert.assertEquals("1=1&a=b&d=c", RequestParametersBuilder().add("1", "1").add("a", "b").add("d", "c").toString()
        )
    }
}