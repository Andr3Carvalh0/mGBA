package io.mgba.data

import com.annimon.stream.Stream
import java.io.File
import java.util.LinkedList
import io.mgba.data.local.database.model.Game
import io.mgba.data.remote.dtos.GameJSON
import io.mgba.data.remote.interfaces.getGamesService
import io.mgba.utilities.FileOperations
import io.mgba.data.io.FilesManager
import io.mgba.data.interfaces.ILibrary
import io.mgba.mgba
import io.mgba.data.io.Repository
import io.mgba.utilities.DeviceManager
import io.reactivex.Single

object Library : ILibrary {
    override fun prepareGames(platform: Int): Single<List<Game>> {
        val ret = Single.create<List<Game>> { subscriber ->

            if (platform == Constants.PLATFORM_GBC || platform == Constants.PLATFORM_GBA || platform == Constants.PLATFORM_FAVS) {
                subscriber.onSuccess(Repository.getGamesForPlatform(platform))
            } else {
                subscriber.onSuccess(LinkedList())
            }
        }

        return ret.doOnError { mgba.report(it) }
    }

    override fun query(query: String): Single<List<Game>> {
        val ret = Single.create<List<Game>> Single@{ subscriber ->

            if (query.isEmpty()) {
                subscriber.onSuccess(LinkedList())
                return@Single
            }

            subscriber.onSuccess(Repository.queryForGames(query))
        }

        return ret.doOnError { mgba.report(it) }
    }

    override fun reloadGames(vararg platform: Int): Single<List<Game>> {
        val ret = Single.create<List<Game>> { subscriber ->
            val games = Repository.games.toMutableList()
            removeGamesFromDatabase(games)

            val updatedList = processNewGames(games)

            games.addAll(updatedList)
            games.sortWith(Comparator { o1, o2 -> o1.getName()!!.compareTo(o2.getName()!!) })
            subscriber.onSuccess(filter(platform, games))
        }

        return ret.doOnError { mgba.report(it) }

    }

    override fun reloadGames(path: String, vararg platform: Int): Single<List<Game>> {
        FilesManager.currentDirectory = path
        return reloadGames(*platform)
    }

    private fun removeGamesFromDatabase(games: MutableList<Game>) {
        Stream.of(games)
                .filter { g -> !g.file!!.exists() }
                .forEach { g ->
                    games.remove(g)
                    Repository.delete(g)
                }
    }

    private fun processNewGames(games: MutableList<Game>): List<Game> {
        return Stream.of(FilesManager.gameList)
                .map { file -> Game(file.absolutePath, getPlatform(file)) }
                .filter { file -> games.isEmpty() || Stream.of(games).anyMatch { game -> game.file != file.file } }
                .map { game ->
                    Stream.of(games).filter { otherGame -> otherGame == game }
                                    .forEach { games.remove(it) }

                    if (calculateMD5(game)) {
                        if (DeviceManager.isConnectedToWeb()) {
                            searchWeb(game)
                        }
                        storeInDatabase(game)
                    }

                    game
                }.toList()
    }

    private fun filter(platform: IntArray, games: List<Game>): List<Game> {
        return Stream.of(games)
                .filter Stream@{ g ->
                    for (i in platform.indices) {
                        if (platform[i] == g.platform)
                            return@Stream true
                    }
                    false
                }
                .toList()
    }

    private fun getPlatform(file: File): Int {
        val fileExtension = FilesManager.getFileExtension(file)

        return if (Constants.PLATFORM_GBA_EXT.contains(fileExtension)) Constants.PLATFORM_GBA else Constants.PLATFORM_GBC

    }

    private fun storeInDatabase(game: Game) {
        Repository.insert(game)
    }

    private fun searchWeb(game: Game) {
        try {
            val json = getGamesService().getGameInformation(game.mD5!!, DeviceManager.getDeviceLanguage())
                                             .execute()
                                             .body()

            if (json != null)
                copyInformation(game, json)
        } catch (e: Exception) {
            mgba.report(e)
        }

    }

    private fun calculateMD5(game: Game): Boolean {
        val md5 = FileOperations.getFileMD5ToString(game.file!!)
        game.mD5 = md5

        return md5 != null
    }

    private fun copyInformation(game: Game, json: GameJSON) {
        game.setName(json.name)
        game.description = json.description
        game.developer = json.developer
        game.genre = json.genre
        game.released = json.released
        game.coverURL = json.cover
    }
}
