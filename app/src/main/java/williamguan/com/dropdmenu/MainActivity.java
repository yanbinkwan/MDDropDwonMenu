package williamguan.com.dropdmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import williamguan.com.dropdmenu.adapter.AgeDropDownAdapter;
import williamguan.com.dropdmenu.adapter.DividerItemDecoration;
import williamguan.com.dropdmenu.adapter.ListDropDownAdapter;
import williamguan.com.dropdmenu.adapter.MyRecyclerAdapter;
import williamguan.com.dropdmenu.entity.AgePackage;
import williamguan.com.dropdmenu.entity.UserEntity;
import williamguan.com.library.MaterialDropMenu;
import williamguan.com.library.entity.MenuItemEntity;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private List<String> genderCondition;
    private List<AgePackage> ageCondition;
    private List<UserEntity> userEntities;
    private MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        genderCondition = new ArrayList<>();
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        assert recyclerView != null;
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        final MaterialDropMenu materialDropMenu = (MaterialDropMenu) findViewById(R.id.materialDropMenu);
        assert materialDropMenu != null;
        materialDropMenu.setMenuListItemClickListener(new MaterialDropMenu.OnMenuListItemClickListener() {

            @Override
            public void onMenuListItemClick(View view, int position, BaseAdapter adapter) {
                loadData();
                int currentPos = materialDropMenu.getCurrentTabPosition();
                if (currentPos == 1) {
                    String gender = (String) adapter.getItem(position);
                    MenuItemEntity menuItemEntity = materialDropMenu.getMenuItemEntities().get(2);
                    AgePackage agePackage = (AgePackage) menuItemEntity.getTag();
                    if (agePackage != null) {
                        filterData(gender, agePackage.getIndex());
                    }
                    filterData(gender, -1);
                }
                if (currentPos == 2) {
                    AgePackage agePackage = (AgePackage) adapter.getItem(position);
                }
            }
        });

        materialDropMenu.addMenu("全部", false);
        materialDropMenu.addListMenu(genderCondition.get(0), new ListDropDownAdapter(this, genderCondition));
        materialDropMenu.addListMenu(ageCondition.get(0).getAgeText(), new AgeDropDownAdapter(this, ageCondition));
    }

    private void initData() {
        genderCondition.add("性别");
        genderCondition.add("男");
        genderCondition.add("女");
        ageCondition = new ArrayList<>();
        ageCondition.add(new AgePackage("年龄", 0));
        ageCondition.add(new AgePackage("10-20", 1));
        ageCondition.add(new AgePackage("20-30", 2));
        ageCondition.add(new AgePackage("30-40", 3));
        ageCondition.add(new AgePackage("> 40", 4));
        userEntities = new ArrayList<>();
        userEntities = loadData();
        adapter = new MyRecyclerAdapter(userEntities);
    }

    private void filterData(String gender, int ageIndex) {
        List<UserEntity> matched = new ArrayList<>();
        for (int i = 0; i < userEntities.size(); i++) {
            UserEntity entity = userEntities.get(i);
            if (!gender.isEmpty()) {
                if (gender.equals(entity.getGender())) {
                    matched.add(entity);
                }
            }
        }
        if (matched.size() > 0) {
            userEntities.retainAll(matched);
        } else {
            loadData();
        }
        adapter.notifyDataSetChanged();
    }

    public List<UserEntity> loadData() {
        userEntities.clear();
        UserEntity userEntity1 = new UserEntity(1, "William", 23, "男");
        UserEntity userEntity2 = new UserEntity(2, "Bike", 21, "男");
        UserEntity userEntity3 = new UserEntity(3, "Nik", 23, "男");
        UserEntity userEntity4 = new UserEntity(4, "Marry", 25, "女");
        UserEntity userEntity5 = new UserEntity(5, "Will", 33, "男");
        UserEntity userEntity6 = new UserEntity(6, "DaMin", 43, "男");
        UserEntity userEntity7 = new UserEntity(7, "May", 52, "女");
        UserEntity userEntity8 = new UserEntity(8, "Jack", 27, "女");
        UserEntity userEntity9 = new UserEntity(9, "Mike", 28, "女");
        UserEntity userEntity10 = new UserEntity(10, "Adela", 29, "男");
        UserEntity userEntity11 = new UserEntity(11, "Celeste", 29, "男");
        UserEntity userEntity12 = new UserEntity(12, "Cherry", 44, "女");
        UserEntity userEntity13 = new UserEntity(13, "Dinah", 34, "女");
        UserEntity userEntity14 = new UserEntity(14, "Ethel", 23, "女");
        UserEntity userEntity15 = new UserEntity(15, "Jocelyn", 67, "女");
        UserEntity userEntity16 = new UserEntity(16, "Jane", 12, "男");
        userEntities.add(userEntity1);
        userEntities.add(userEntity2);
        userEntities.add(userEntity3);
        userEntities.add(userEntity4);
        userEntities.add(userEntity5);
        userEntities.add(userEntity6);
        userEntities.add(userEntity7);
        userEntities.add(userEntity8);
        userEntities.add(userEntity9);
        userEntities.add(userEntity10);
        userEntities.add(userEntity11);
        userEntities.add(userEntity12);
        userEntities.add(userEntity13);
        userEntities.add(userEntity14);
        userEntities.add(userEntity15);
        userEntities.add(userEntity16);
        return userEntities;
    }
}
