package io.mgba.model.interfaces

import io.mgba.data.database.Game
import io.reactivex.Single

interface ILibrary {
    fun prepareGames(platform: Int): Single<List<Game>>
    fun query(query: String): Single<List<Game>>

    fun reloadGames(vararg platform: Int): Single<List<Game>>
    fun reloadGames(path: String, vararg platform: Int): Single<List<Game>>
}
