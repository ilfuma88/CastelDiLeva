package it.ilfuma.rc.casteldileva.Database;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Shop implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    public int categoryId;
    @ColumnInfo(name = "shopName")
    public String shopName;
    @ColumnInfo(name = "shopDescription")
    public String shopDescription;
    @ColumnInfo(name = "shopMail")
    public String shopMail;
    @ColumnInfo(name = "shopWebsite")
    public String shopWebsite;
    @ColumnInfo(name = "shopNumber1")
    public String shopNumber1;
    @ColumnInfo(name = "shopNumber2")
    public String shopNumber2;
    @ColumnInfo (name = "shopPosition")
    public String shopPosition;
    @ColumnInfo(name = "shopLogo")
    public String shopLogo;
    @ColumnInfo(name = "discount1")
    public String discount1;
    @ColumnInfo(name = "condition1" )
    public String condition1;
    @ColumnInfo(name = "discount2")
    public String discount2;
    @ColumnInfo(name = "condition2")
    public String condition2;
    @ColumnInfo(name = "discount3")
    public String discount3;
    @ColumnInfo(name = "condition3")
    public String condition3;

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(categoryId);
        parcel.writeString(shopName);
        parcel.writeString(shopDescription);
        parcel.writeString(shopMail);
        parcel.writeString(shopWebsite);
        parcel.writeString(shopNumber1);
        parcel.writeString(shopNumber2);
        parcel.writeString(shopPosition);
        parcel.writeString(shopLogo);
        parcel.writeString(discount1);
        parcel.writeString(condition1);
        parcel.writeString(discount2);
        parcel.writeString(condition2);
        parcel.writeString(discount3);
        parcel.writeString(condition3);
    }

    public Shop(){

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private Shop(Parcel in){
        categoryId = in.readInt();
        shopName = in.readString();
        shopMail = in.readString();
        shopDescription = in.readString();
        shopWebsite = in.readString();
        shopNumber1 = in.readString();
        shopNumber2 = in.readString();
        shopPosition = in.readString();
        shopLogo = in.readString();
        discount1 = in.readString();
        condition1 = in.readString();
        discount2 = in.readString();
        condition2 = in.readString();
        discount3 = in.readString();
        condition3 = in.readString();
    }
}