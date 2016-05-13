package williamguan.com.library;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * williamguan.com.library
 * Email: william.guanluck@gmail.com
 * Created by Willi on 5/11/2016.
 */
public class MaterialDropMenu extends LinearLayout {

    private int maskColor;

    private int textSelectedColor;

    private int textUnSelectedColor;

    private int underlineColor;

    private int menuTitleSize;

    private int menuBackgroundColor;

    private int menuIndicatorColor;

    private Drawable menuExpandingIcon;

    private Drawable menuCloseUpIcon;

    // -1 = un select
    private int currentTabPosition = -1;

    private LinearLayout menuContainer;

    private View underline;

    private View maskView;

    // 底部菜单容器
    private FrameLayout bottomContainerView;

    private FrameLayout popupMenuContainer;

    private OnMenuItemClickListener menuItemClickListener;

    private OnMenuListItemClickListener menuListItemClickListener;

    public MaterialDropMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialDropMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);

        TintTypedArray ta = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.MaterialDropMenu, defStyleAttr, 0);
        maskColor = ta.getColor(R.styleable.MaterialDropMenu_maskColor, 0x40888888);
        textSelectedColor = ta.getColor(R.styleable.MaterialDropMenu_textSelectedColor, value.data);
        textUnSelectedColor = ta.getColor(R.styleable.MaterialDropMenu_textUnSelectedColor, 0xff6D6D6D);
        underlineColor = ta.getColor(R.styleable.MaterialDropMenu_underlineColor, 0xffcccccc);
        menuTitleSize = (int) ta.getDimension(R.styleable.MaterialDropMenu_menuTitleSize, dpToPx(14));
        menuBackgroundColor = ta.getColor(R.styleable.MaterialDropMenu_menuBackgroundColor, 0xffffffff);
        menuIndicatorColor = ta.getColor(R.styleable.MaterialDropMenu_menuIndicatorColor, value.data);
        menuExpandingIcon = ta.getDrawable(R.styleable.MaterialDropMenu_menuExpandingIcon);
        menuCloseUpIcon = ta.getDrawable(R.styleable.MaterialDropMenu_menuCloseUpIcon);
        ta.recycle();

        menuContainer = new LinearLayout(context);
        menuContainer.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        menuContainer.setBackgroundColor(menuBackgroundColor);
        menuContainer.setOrientation(HORIZONTAL);
        addView(menuContainer, 0);

        underline = new View(context);
        underline.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(1)));
        underline.setBackgroundColor(underlineColor);
        addView(underline, 1);

        maskView = new View(context);
        maskView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        maskView.setBackgroundColor(maskColor);
        maskView.setVisibility(GONE);

        popupMenuContainer = new FrameLayout(context);
        popupMenuContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popupMenuContainer.setVisibility(GONE);

        bottomContainerView = new FrameLayout(context);
        bottomContainerView.setLayoutParams(
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bottomContainerView.setBackgroundResource(android.R.color.transparent);
        bottomContainerView.addView(maskView);
        bottomContainerView.addView(popupMenuContainer);
        addView(bottomContainerView);
    }

    public ListView addListMenu(String menuTitle, final BaseAdapter adapter, boolean widthIcon, int height) {
        ListView lv = new ListView(getContext());
        lv.setBackgroundColor(menuBackgroundColor);
        if (height != 0) {
            lv.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(height)));
        } else {
            lv.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        lv.setAdapter(adapter);
        if (menuListItemClickListener != null) {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    menuListItemClickListener.onMenuListItemClick(parent, view, position, (String) adapter.getItem(position));
                    setMenuText((String) adapter.getItem(position), currentTabPosition);
                    closeMenu();
                }
            });
        }
        popupMenuContainer.addView(lv);
        addMenu(menuTitle, widthIcon);
        return lv;
    }

    public ListView addListMenu(String menuTitle, final BaseAdapter adapter, int height) {
        return addListMenu(menuTitle, adapter, true, height);
    }

    public ListView addListMenu(String menuTitle, String[] data, boolean widthIcon) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
        return addListMenu(menuTitle, adapter, widthIcon, 0);
    }

    public ListView addListMenu(String menuTitle, BaseAdapter adapter) {
        return addListMenu(menuTitle, adapter, true, 0);
    }

    private TextView addMenu(String menuTitle, boolean widthIcon) {
        final LinearLayout menuTitleContainer = new LinearLayout(getContext());
        menuTitleContainer.setLayoutParams(
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        menuTitleContainer.setGravity(Gravity.CENTER);
        menuTitleContainer.setOrientation(VERTICAL);
        menuTitleContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMenu(v);
            }
        });

        TextView menuTitleView = new TextView(getContext());
        menuTitleView.setLayoutParams(
                new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        menuTitleView.setSingleLine();
        menuTitleView.setGravity(Gravity.CENTER);
        menuTitleView.setEllipsize(TextUtils.TruncateAt.END);
        menuTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTitleSize);
        menuTitleView.setPadding(dpToPx(5), dpToPx(10), dpToPx(5), dpToPx(10));
        menuTitleView.setText(menuTitle);
        if (menuItemClickListener != null) {
            menuTitleView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuItemClickListener.onMenuItemClick((TextView) v, currentTabPosition);
                    menuTitleContainer.callOnClick();
                }
            });
        }
        if (widthIcon) {
            menuTitleView.setTag("widthIcon");
            menuTitleView.setCompoundDrawablesWithIntrinsicBounds(null, null, menuExpandingIcon, null);
            menuTitleView.setCompoundDrawablePadding(dpToPx(5));
        }

        View menuIndicator = new View(getContext());
        menuIndicator.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(2)));
        menuIndicator.setBackgroundColor(menuIndicatorColor);
        menuIndicator.setVisibility(INVISIBLE);

        LinearLayout container = new LinearLayout(getContext());
        container.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setGravity(Gravity.CENTER);
        container.setOrientation(VERTICAL);
        container.addView(menuTitleView);
        container.addView(menuIndicator);

        menuTitleContainer.addView(container);
        menuContainer.addView(menuTitleContainer);
        return menuTitleView;
    }

    private void selectMenu(View menu) {
        for (int i = 0; i < menuContainer.getChildCount(); i++) {
            TextView title = (TextView) ((LinearLayout) ((LinearLayout) menuContainer.getChildAt(i)).getChildAt(0)).getChildAt(0);
            View indicator = ((LinearLayout) ((LinearLayout) menuContainer.getChildAt(i)).getChildAt(0)).getChildAt(1);
            if (menu == menuContainer.getChildAt(i)) {
                if (currentTabPosition == i) {
                    title.setTextColor(textUnSelectedColor);
                    indicator.setVisibility(INVISIBLE);
                    if (title.getTag() != null) {
                        title.setCompoundDrawablesWithIntrinsicBounds(null, null, menuExpandingIcon, null);
                    }
                    closeMenu();
                } else {
                    if (currentTabPosition == -1) {
                        popupMenuContainer.setVisibility(VISIBLE);
                        popupMenuContainer.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
                        popupMenuContainer.getChildAt(i).setVisibility(VISIBLE);
                        maskView.setVisibility(VISIBLE);
                        maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
                    } else {
                        popupMenuContainer.getChildAt(i).setVisibility(VISIBLE);
                        maskView.setVisibility(VISIBLE);
                    }
                    title.setTextColor(textSelectedColor);
                    indicator.setVisibility(VISIBLE);
                    currentTabPosition = i;
                    if (title.getTag() != null) {
                        title.setCompoundDrawablesWithIntrinsicBounds(null, null, menuCloseUpIcon, null);
                    }
                }
            } else {
                popupMenuContainer.getChildAt(i).setVisibility(View.GONE);
                title.setTextColor(textUnSelectedColor);
                indicator.setVisibility(INVISIBLE);
                if (title.getTag() != null) {
                    title.setCompoundDrawablesWithIntrinsicBounds(null, null, menuExpandingIcon, null);
                }
            }
        }
    }

    private void closeMenu() {
        popupMenuContainer.setVisibility(GONE);
        popupMenuContainer.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
        maskView.setVisibility(GONE);
        currentTabPosition = -1;
    }

    public void setMenuText(String text, int position) {
        ((TextView) ((LinearLayout) ((LinearLayout)
                menuContainer.getChildAt(position)).getChildAt(0)).getChildAt(0)).setText(text);
    }

    public void setCurrentMenuText(String text) {
        if (currentTabPosition != -1) {
            setMenuText(text, currentTabPosition);
        }
    }

    public void setMenuListItemClickListener(OnMenuListItemClickListener menuListItemClickListener) {
        this.menuListItemClickListener = menuListItemClickListener;
    }

    public void setMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
    }

    public int dpToPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(TextView menuText, int position);
    }

    public interface OnMenuListItemClickListener {
        void onMenuListItemClick(AdapterView<?> parent, View view, int position, String data);
    }
}
