package io.mgba.Data.Remote.Interfaces;

import io.mgba.Data.Remote.DTOs.GameJSON;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRequest {

    String BASE_URL = "https://andr3carvalh0.github.io/Databases/mGBA/";

    @GET("Games_{lang}/{md5}.json")
    Call<GameJSON> getGameInformation(@Path("md5") String md5, @Path("lang") String lang);
}
