import ResponseHandler.Companion.datesList
import org.junit.Assert
import org.junit.Test

internal class ResponseHandlerTest {
    private val response = """
        {
          "response": {
            "items": [
              {
                "id": 110270,
                "date": 1603131786,
                "owner_id": 107933267,
                "from_id": 107933267,
                "post_type": "post",
                "text": "asdasd",
                "is_favorite": false
              }
            ],
            "total_count": 4955
          }
        }
    """.trimIndent()

    @Test
    fun `correct handle response`() {
        Assert.assertEquals(listOf(1603131786L), response.datesList())
    }
}