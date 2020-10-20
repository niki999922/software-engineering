interface VKService {
    fun getDates(tag: String, hours: Int, currentTime: Long = System.currentTimeMillis()): List<Long>
}