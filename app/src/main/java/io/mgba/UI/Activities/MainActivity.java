package io.mgba.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.flipboard.bottomsheet.BottomSheetLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mgba.Controller.Interfaces.IMainController;
import io.mgba.Controller.MainController;
import io.mgba.Data.Database.Game;
import io.mgba.Model.Interfaces.ILibrary;
import io.mgba.R;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, io.mgba.UI.Activities.Interfaces.ILibrary{

    @BindView(R.id.floating_search_view) FloatingSearchView mToolbar;
    @BindView(R.id.pager) ViewPager mViewPager;
    @BindView(R.id.bottomsheet) BottomSheetLayout mSheetDialog;
    @BindView(R.id.tabLayout) TabLayout mTabLayout;

    private IMainController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        ButterKnife.bind(this);
        controller = new MainController(this);

        prepareToolbar();
        prepareViewPager();
    }

    private void prepareToolbar() {
        controller.prepareToolbar(mToolbar);
    }

    private void prepareViewPager() {
        controller.prepareTabLayout(mTabLayout, mViewPager, this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        controller.onTabSelected(tab, mViewPager);
    }

    @Override
    public void showBottomSheet(Game game) {
        controller.showBottomSheet(game, mSheetDialog);
    }

    @Override
    public ILibrary getLibraryService() {
        return controller.getILibrary();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        controller.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        controller.onDestroy();
    }

}
