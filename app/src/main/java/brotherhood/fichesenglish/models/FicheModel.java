package brotherhood.fichesenglish.models;

/**
 * Created by Przemek on 16.02.2016.
 */
public class FicheModel {
    private int id;
    private Status status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        LEARNED,
        NOT_LEARNED,
        UNKNOWN;

        public static Status defineStatus(int statusFromDatabase) {
            switch (statusFromDatabase) {
                case 0:
                    return NOT_LEARNED;
                case 1:
                    return LEARNED;
                default:
                    return UNKNOWN;
            }
        }

        public static int getStatusCode(Status status){
            switch (status)
            {
                case LEARNED:
                    return 1;
                case NOT_LEARNED:
                    return 0;
                default:
                    return 2;
            }
        }
    }
}
