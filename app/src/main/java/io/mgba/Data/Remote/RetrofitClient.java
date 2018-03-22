package io.mgba.Data.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit = null;

    public Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            synchronized (this){
                if(retrofit == null)
                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
            }
        }

        return retrofit;
    }
}
