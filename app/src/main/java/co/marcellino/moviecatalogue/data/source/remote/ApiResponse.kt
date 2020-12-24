package co.marcellino.moviecatalogue.data.source.remote

class ApiResponse<T>(val status: StatusResponse, val body: T, val message: String?) {

    companion object {
        fun <T> success(body: T): ApiResponse<T> = ApiResponse(StatusResponse.SUCCESS, body, null)

        fun <T> empty(body: T): ApiResponse<T> = ApiResponse(StatusResponse.EMPTY, body, null)

        fun <T> error(body: T): ApiResponse<T> = ApiResponse(StatusResponse.ERROR, body, null)
    }
}