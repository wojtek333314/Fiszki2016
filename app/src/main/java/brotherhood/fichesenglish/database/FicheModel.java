package brotherhood.fichesenglish.database;

/**
 * Created by Przemek on 16.02.2016.
 */
public class FicheModel {
    private int id;
    private String plValue;
    private String engValue;
    private String category;
    private String soundPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlValue() {
        return plValue;
    }

    public void setPlValue(String plValue) {
        this.plValue = plValue;
    }

    public String getEngValue() {
        return engValue;
    }

    public void setEngValue(String engValue) {
        this.engValue = engValue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }
}
