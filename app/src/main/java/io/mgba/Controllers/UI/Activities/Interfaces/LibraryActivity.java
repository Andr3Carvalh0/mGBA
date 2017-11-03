package io.mgba.Controllers.UI.Activities.Interfaces;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.common.base.Function;

import io.mgba.Controllers.Services.ProcessingService;
import io.mgba.Data.Wrappers.LibraryLists;
import io.mgba.Services.Interfaces.ILibraryService;
import io.mgba.Services.Interfaces.IPermissionService;
import io.mgba.Services.System.PermissionService;
import io.mgba.mgba;

public class LibraryActivity extends AppCompatActivity {
    protected ILibraryService libraryController;
    private IPermissionService permissionService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        libraryController = ((mgba)getApplication()).getLibraryController();
        permissionService = new PermissionService(this);
    }

    protected void prepareGames(Function<LibraryLists, Void> callback){
        if(!((mgba)getApplication()).isServiceRunning(ProcessingService.class)) {
            libraryController.prepareGames(callback);
        }

    }

    protected LibraryLists getCachedList(){
        return libraryController.getCachedList();
    }

    protected boolean hasStoragePermission(){
        return permissionService.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        libraryController.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        libraryController.stop();
    }
}
