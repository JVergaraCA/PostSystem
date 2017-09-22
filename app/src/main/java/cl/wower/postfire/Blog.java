package cl.wower.postfire;

/**
 * Created by JuanCarlos on 21-09-17.
 */

public class Blog {

    String title,desc,image;
    public Blog() {
    }

    public Blog(String title, String desc, String image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String descript) {
        this.desc = descript;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
