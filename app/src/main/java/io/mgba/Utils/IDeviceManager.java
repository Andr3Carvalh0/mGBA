package io.mgba.Utils;

import io.mgba.Data.Remote.Interfaces.IRequest;

public interface IDeviceManager {
    boolean isConnectedToWeb();
    String getDeviceLanguage();
    IRequest getWebService();
}
