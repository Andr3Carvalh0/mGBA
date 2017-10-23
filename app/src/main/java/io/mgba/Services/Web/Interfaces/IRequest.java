package io.mgba.Services.Web.Interfaces;

import io.mgba.Data.DTOs.GameJSON;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRequest {

    @GET("{md5}.json")
    Call<GameJSON> registerDevice(@Path("md5") String md5);
}
