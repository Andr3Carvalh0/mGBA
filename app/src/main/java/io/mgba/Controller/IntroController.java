package io.mgba.Controller;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.annimon.stream.Stream;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.nononsenseapps.filepicker.Controllers.FilePickerUtils;

import java.util.LinkedList;
import java.util.List;

import io.mgba.Controller.Interfaces.IIntroController;
import io.mgba.Model.Interfaces.IPermissionManager;
import io.mgba.Model.Library;
import io.mgba.Model.System.PermissionManager;
import io.mgba.Model.System.PreferencesManager;
import io.mgba.R;
import io.mgba.UI.Activities.MainActivity;
import io.mgba.mgba;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.PermissionRequest;

public class IntroController implements IIntroController {
    private final IPermissionManager permissionService;
    private AppCompatActivity context;
    private Disposable disposable;

    public IntroController(@NonNull AppCompatActivity context) {
        this.permissionService = new PermissionManager(context);
        this.context = context;
    }

    private Stream<AppIntroFragment> getIntroFragments(){
        List<AppIntroFragment> slides = new LinkedList<>();

        //Welcome-screen
        slides.add(AppIntro2Fragment.newInstance(context.getResources().getString(R.string.Welcome_Title),
                context.getResources().getString(R.string.Welcome_Description),
                R.mipmap.ic_launcher,
                context.getResources().getColor(R.color.colorPrimary)));

        //Feature-Library
        slides.add(AppIntro2Fragment.newInstance(context.getResources().getString(R.string.Library_Title),
                context.getResources().getString(R.string.Library_Description),
                R.mipmap.ic_launcher,
                context.getResources().getColor(R.color.colorPrimary)));

        return Stream.of(slides);
    }

    @Override
    public void setupView(AppIntro2 introActivity) {
        getIntroFragments().forEach(introActivity::addSlide);

        introActivity.setProgressButtonEnabled(true);
        introActivity.setFadeAnimation();
        introActivity.showSkipButton(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PermissionManager.DIR_CODE && resultCode == Activity.RESULT_OK) {
            String dir = FilePickerUtils.getSelectedDir(intent);
            onEnd(dir);
        }
    }

    @Override
    public void onDeniedForStorage() {
        onEnd();
    }

    @Override
    public void showFilePicker() {
        permissionService.showFilePicker();
    }

    @Override
    public void showRationaleForStorage(PermissionRequest request) {
        permissionService.showRationaleForStorage(request);
    }

    private void onEnd(String dir){
        ((mgba)context.getApplication()).savePreference(PreferencesManager.GAMES_DIRECTORY, dir);
        ((mgba)context.getApplication()).showProgressDialog(context);

        disposable = new Library((mgba)context.getApplication())
                .reloadGames()
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(games -> {
                    ((mgba)context.getApplication()).stopProgressDialog();
                    onEnd();
                });
    }


    private void onEnd(){
        ((mgba)context.getApplication()).savePreference(PreferencesManager.SETUP_DONE, true);

        //House cleaning
        disposable.dispose();

        Intent it = new Intent(context.getBaseContext(), MainActivity.class);
        context.startActivity(it);
        context.finish();
    }
}
