class RequestParametersBuilder {
    private val parameters = mutableMapOf<String, String>()

    fun add(key: String, value: String) = this.also { parameters[key] = value }
    override fun toString() = parameters.map { "${it.key}=${it.value}"}.joinToString("&")
}