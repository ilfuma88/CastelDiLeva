package it.ilfuma.rc.casteldileva.riccardoUtils;

public class FirestoreHelper {
    String  NOfCoupons;
    String  coupon;
    private static FirestoreHelper instance;

    public String getNOfCoupons() {
        return NOfCoupons;
    }

    public void setNOfCoupons(String NOfCoupons) {
        this.NOfCoupons = NOfCoupons;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    private FirestoreHelper() {
    }

    public static FirestoreHelper getInstance(){
        if (instance == null){
            instance = new FirestoreHelper();
            return instance;
        }else{
            return instance;
        }
    }
}
