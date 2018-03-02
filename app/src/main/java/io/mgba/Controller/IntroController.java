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
import io.mgba.Model.System.PermissionManager;
import io.mgba.Model.System.PreferencesManager;
import io.mgba.R;
import io.mgba.UI.Activities.MainActivity;
import io.mgba.mgba;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.PermissionRequest;

//S
public class IntroController implements IIntroController {

    private final static String TAG = "mgba:IntroCtr";
    private boolean isVisible = true;
    private boolean isDone = false;

    private final IPermissionManager permissionService;
    private AppCompatActivity context;
    private CompositeDisposable disposable = new CompositeDisposable();

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
            mgba.printLog(TAG, "received result from activity");
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

    @Override
    public void onResume() {
        isVisible = true;

        if (isDone){
            mgba.printLog(TAG, "Finishing setup coming from the background");
            onEnd();
        }
    }

    @Override
    public void onPause() {
        isVisible = false;
    }

    // Since this Single object runs outside the UI Thread, we will leave it be.
    // In case of a onPause event, we will not terminate it. It will do want it need and when finished,
    // set the flag isDone.
    // In the eventual case that the app isnt killed, when the user comes back, the onResume will be triggered,
    // And we will continue to the MainActivity
    // If thats not the case, and the app ends up dead, there will be no problem(*) and the app will start the main activity
    // * There could also happen that the app gets kill in between the processing...
    //  In that case the flag setup_done will stay false and on the next launch the setup will show up again.
    private void onEnd(String dir){
        ((mgba)context.getApplication()).savePreference(PreferencesManager.GAMES_DIRECTORY, dir);
        ((mgba)context.getApplication()).showProgressDialog(context);

        disposable.add(((mgba)context.getApplication()).getLibrary()
                                    .reloadGames()
                                    .subscribeOn(Schedulers.computation())
                                    .subscribe(games -> {
                                        isDone = true;
                                        onEnd();
                                    }));
    }

    private void onEnd(){
        ((mgba) context.getApplication()).savePreference(PreferencesManager.SETUP_DONE, true);
        ((mgba) context.getApplication()).stopProgressDialog();

        //House cleaning
        if(disposable != null && !disposable.isDisposed())
            disposable.dispose();

        if(isVisible) {
            Intent it = new Intent(context.getBaseContext(), MainActivity.class);
            context.startActivity(it);
            context.finish();
        }
    }
}
