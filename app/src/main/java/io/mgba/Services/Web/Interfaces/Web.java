package io.mgba.Services.Web.Interfaces;

import io.mgba.Services.Web.RetrofitClientFactory;

/**
 * Created by André Carvalho on 23/10/2017.
 */

public class Web {

    private static String main = "https://andr3carvalh0.github.io/mGBA_Database/Games/";

    public static IRequest getAPI() {
        return RetrofitClientFactory.getClient(main).create(IRequest.class);
    }
}
