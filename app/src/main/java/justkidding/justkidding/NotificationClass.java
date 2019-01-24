package justkidding.justkidding;


public class NotificationClass {

    private String mTheme;
    private String mDate;
    private String mText;

    // Constructor that is used to create an instance of the Movie object
    public NotificationClass(String theme, String date, String text) {
        this.mTheme = theme;
        this.mText = text;
        this.mDate = date;

    }

    public String getTheme() {
        return this.mTheme;
    }
    public void setTheme(String theme) {
        this.mTheme = theme;
    }

    public String getDate() {
        return this.mDate;
    }
    public void setDate(String date) {
        this.mDate = date;
    }

    public String getText() {
        return this.mText;
    }
    public void setText(String text){
        this.mText = text;
    }
}
