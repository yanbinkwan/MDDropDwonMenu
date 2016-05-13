package williamguan.com.dropdmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import williamguan.com.library.MaterialDropMenu;

public class MainActivity extends AppCompatActivity {
    private String constellations[] = {"不限", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};
    private String data[] = {"朝外大街", "望京", "亚运村", "十里堡", "东华门", "景山街", "和平里", "永定门", "清河", "羊坊店"};
    private String data1[] = {"万寿路", "新街口", "月坛", "金融街", "广安门内", "广安门外", "中仓", "玉桥"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialDropMenu materialDropMenu = (MaterialDropMenu) findViewById(R.id.materialDropMenu);
        if (materialDropMenu != null) {
            materialDropMenu.setMenuItemClickListener(new MaterialDropMenu.OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(TextView menuText, int position) {
                    Toast.makeText(MainActivity.this, menuText.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            materialDropMenu.setMenuListItemClickListener(new MaterialDropMenu.OnMenuListItemClickListener() {
                @Override
                public void onMenuListItemClick(AdapterView<?> parent, View view, int position, String data) {
                    Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                }
            });
            materialDropMenu.addListMenu("全部", new ListDropDownAdapter(this, Arrays.asList(constellations)));
            materialDropMenu.addListMenu("项目", new ListDropDownAdapter(this, Arrays.asList(data)), 300);
            materialDropMenu.addListMenu("时间", new ListDropDownAdapter(this, Arrays.asList(data1)), 213);
            materialDropMenu.addListMenu("难度", new ListDropDownAdapter(this, Arrays.asList(constellations)));
            materialDropMenu.addListMenu("等级", new String[]{"高", "中", "低   "}, true);

        }

    }
}
