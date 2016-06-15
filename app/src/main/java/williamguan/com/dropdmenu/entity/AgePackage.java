package williamguan.com.dropdmenu.entity;

/**
 * Created by William on 5/29/2016.
 */
public class AgePackage {

    public AgePackage(String ageText, int index) {
        this.ageText = ageText;
        this.index = index;
    }

    private String ageText;
    private int index;

    public String getAgeText() {
        return ageText;
    }

    public void setAgeText(String ageText) {
        this.ageText = ageText;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
