package com.naver.hyeonjung.navertestapp.vo;


import java.util.Date;



// databinding 과 lombok 의 호환성 문제로 인한 lombok 미사용.
public class Item {
    public static final int IMAGE_TYPE = 0;
    public static final int WEB_TYPE = 1;

    private int type;
    private Image image;
    private Web web;

    public Item() { }

    public Item(int type, Image image, Web web) {
        this.type = type;
        this.image = image;
        this.web = web;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Web getWeb() {
        return web;
    }

    public void setWeb(Web web) {
        this.web = web;
    }


//    @Override
//    public String toString() {
//        final StringBuffer sb = new StringBuffer("item{");
//        sb.append("id=").append(id);
//        if (getType() == IMAGE_TYPE) {
//            sb.append(", thumbnail_url='").append(getImage().getThumbnail_url()).append('\'');
//            sb.append(", image_url='").append(getImage().getImage_url()).append('\'');
//            sb.append(", display_sitename='").append(getImage().getDisplay_sitename()).append('\'');
//        } else {
//            sb.append(", title='").append(getVideo().getTitle()).append('\'');
//            sb.append(", url='").append(getVideo().getUrl()).append('\'');
//        }
//        sb.append(", createdAt=").append(createdAt);
//        sb.append('}');
//        return sb.toString();
//    }
}
