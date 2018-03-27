package io.mgba.Presenter;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.annimon.stream.Stream;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.nononsenseapps.filepicker.Controllers.FilePickerUtils;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import io.mgba.Model.Interfaces.ILibrary;
import io.mgba.Presenter.Interfaces.IIntroPresenter;
import io.mgba.Model.Interfaces.IPermissionManager;
import io.mgba.Model.System.PermissionManager;
import io.mgba.Model.System.PreferencesManager;
import io.mgba.R;
import io.mgba.UI.Activities.Interfaces.IIntroView;
import io.mgba.UI.Activities.MainActivity;
import io.mgba.Utils.IDependencyInjector;
import io.mgba.Utils.IResourcesManager;
import io.mgba.mgba;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.PermissionRequest;


public class IntroPresenter implements IIntroPresenter {

    private final static String TAG = "mgba:IntroCtr";
    @Inject ILibrary library;
    private final IIntroView view;
    private final IPermissionManager permissionService;
    @Inject IResourcesManager resourceManager;
    private CompositeDisposable disposable = new CompositeDisposable();
    private boolean isVisible = true;
    private boolean isDone = false;

    public IntroPresenter(@NonNull IPermissionManager permissionManager, @NonNull IDependencyInjector dependencyInjector,
                          @NonNull IIntroView view) {
        this.permissionService = permissionManager;
        this.view = view;
        dependencyInjector.inject(this);

        view.addSlides(getIntroFragments().toList());
    }

    private Stream<AppIntroFragment> getIntroFragments(){
        List<AppIntroFragment> slides = new LinkedList<>();

        //Welcome-screen
        slides.add(AppIntro2Fragment.newInstance(
                resourceManager.getString(R.string.Welcome_Title),
                resourceManager.getString(R.string.Welcome_Description),
                R.mipmap.ic_launcher,
                resourceManager.getColor(R.color.colorPrimary)));

        //Feature-Library
        slides.add(AppIntro2Fragment.newInstance(
                resourceManager.getString(R.string.Library_Title),
                resourceManager.getString(R.string.Library_Description),
                R.mipmap.ic_launcher,
                resourceManager.getColor(R.color.colorPrimary)));

        return Stream.of(slides);
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
        view.savePreference(PreferencesManager.GAMES_DIRECTORY, dir);
        view.showProgressDialog();

        disposable.add(library.reloadGames(dir)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(games -> {
                                isDone = true;
                                onEnd();
                            }));
    }

    private void onEnd(){
        view.savePreference(PreferencesManager.SETUP_DONE, true);
        view.showProgressDialog();

        //House cleaning
        if(disposable != null && !disposable.isDisposed())
            disposable.dispose();

        if(isVisible)
            view.startActivity(MainActivity.class);
    }
}
