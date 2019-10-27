package com.example.tendertest1.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Models implements Parcelable {
    public static final Creator<Models> CREATOR = new Creator<Models>() {
        @Override
        public Models createFromParcel(Parcel in) {
            return new Models(in);
        }

        @Override
        public Models[] newArray(int size) {
            return new Models[size];
        }
    };
    private String relizeDate; //когда пост был сделан
    private String logoLink; // ссылка на картинку лого компании
    private String category; //Категория поста
    private String tittle;  // Титулка поста
    private String content;  // Сам контент поста
    private String deadLine;    //когда тендер закончится
    private String cost;          // Цена тендера
    private String number;
    private String company;
    private String adress;
    public long id;
    public List<String> images;

    public Models() {
    }

    public long getId() {
        return id;
    }


    public Models(String company, String relizeDate, String adress, String logoLink, String category, String tittle, String content, String deadLine, String cost, String numberPhone,  List<String> images) {
        this.adress = adress;
        this.relizeDate = relizeDate;
        this.logoLink = logoLink;
        this.category = category;
        this.tittle = tittle;
        this.content = content;
        this.deadLine = deadLine;
        this.cost = cost;
        this.id = System.currentTimeMillis();
        this.number = numberPhone;
        this.company = company;
        this.images = images;
    }


    public List<String> getImages() {
        return images;
    }

    public String getAdress() {
        return adress;
    }

    public String getNumberPhone() {
        return number;
    }

    public String getCompany() {
        return company;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public String getRelizeDate() {
        return relizeDate;
    }

    public String getCategory() {
        return category;
    }

    public String getTittle() {
        return tittle;
    }

    public String getContent() {
        return content;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public String getCost() {
        return cost;
    }


    @Override
    public int describeContents() {
        return 0;
    }

//парс, обраотка инфы для передачи след странице
    protected Models(Parcel in) {
        images = in.readArrayList(String.class.getClassLoader());
        adress = in.readString();
        relizeDate = in.readString();
        logoLink = in.readString();
        category = in.readString();
        tittle = in.readString();
        content = in.readString();
        deadLine = in.readString();
        cost = in.readString();
        number = in.readString();
        company = in.readString();
    }
//парс, обраотка инфы для передачи след странице

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(images);
        dest.writeString(adress);
        dest.writeString(relizeDate);
        dest.writeString(logoLink);
        dest.writeString(category);
        dest.writeString(tittle);
        dest.writeString(content);
        dest.writeString(deadLine);
        dest.writeString(cost);
        dest.writeString(number);
        dest.writeString(company);
    }

//"как добавить в firebase" + object
    @Override
    public String toString() {
        return "Models{" +
                "adress='" + adress + '\'' +
                ", relizeDate=" + relizeDate + '\'' +
                ", logoLink=" + logoLink + '\'' +
                ", category" + category + '\'' +
                ", tittle" + tittle + '\'' +
                ", content" + content + '\'' +
                ", deadLine" +  deadLine + '\'' +
                ", cost" + cost +'\'' +
                ", number" + number + '\'' +
                ", company" + company +
                '}';
    }
//"как добавить в firebase" + object

    public Map<String, Object> toMap (){
        HashMap<String, Object> result = new HashMap<>();
        result.put("logoLink", logoLink);
        result.put("adress", adress);
        result.put("relizeDate", relizeDate);
        result.put("category", category);
        result.put("tittle", tittle);
        result.put("content", content);
        result.put("deadLine", deadLine);
        result.put("cost", cost);
        result.put("number", number);
        result.put("company", company);
        result.put("images", images);
        return result;
    }
}
