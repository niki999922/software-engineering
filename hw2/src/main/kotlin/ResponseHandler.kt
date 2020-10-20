import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import java.io.InputStreamReader

class ResponseHandler {
    companion object {
        private const val ITEMS = "items"
        private const val DATE = "date"

        fun String.datesList(): List<Long> {
            val reader = JsonReader(InputStreamReader(this.byteInputStream()))
            val result = mutableListOf<Long>()

            reader.beginObject()
            reader.nextName()
            reader.beginObject()

            while (reader.peek() == JsonToken.NAME) {
                if (reader.nextName() == ITEMS) {
                    reader.beginArray()
                    while (reader.peek() != JsonToken.END_ARRAY) {
                        reader.beginObject()
                        while (reader.peek() == JsonToken.NAME) {
                            if (reader.nextName() == DATE) {
                                result.add(reader.nextLong())
                            } else {
                                reader.skipValue()
                            }
                        }
                        reader.endObject()
                    }
                    reader.endArray()
                } else {
                    reader.skipValue()
                }
            }

            return result
        }
    }
}