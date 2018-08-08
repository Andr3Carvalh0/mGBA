package io.mgba.data.remote.dtos

data class GameJSON(var name: String? = null, var description: String? = null,
                    var released: String? = null, var developer: String? = null,
                    var genre: String? = null, var cover: String? = null
)
