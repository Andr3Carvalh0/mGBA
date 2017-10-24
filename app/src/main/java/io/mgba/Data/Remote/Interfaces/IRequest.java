package io.mgba.Data.Remote.Interfaces;

import io.mgba.Data.DTOs.GameJSON;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRequest {

    public final static String BASE_URL = "https://andr3carvalh0.github.io/mGBA_Database/";

    @GET("Games/{md5}.json")
    Call<GameJSON> getGameInformation(@Path("md5") String md5);
}
