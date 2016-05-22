package ikbal_jimmy.shareservices;

/**
 * Created by jimmymunoz on 22/05/16.
 */
public class Order {
    public String id_order = "";
    public String id_user = "";
    public String id_service = "";
    public String payment_code = "";
    public String code = "";
    public String code_valided = "";
    public String code_notified = "";
    public String created_at = "";
    public String client_pseudo = "";
    public String client_first_name = "";
    public String client_last_name = "";
    public String client_phone = "";
    public String client_email = "";
    public String titre = "";
    public String description = "";
    public String price = "";
    public String image = "";
    public String address = "";
    public String city = "";
    public String latitude = "";
    public String longituge = "";
    public String publication_date = "";
    public String id_category_service = "";
    public String active = "";
    public String id_provider = "";
    public String provider_pseudo = "";
    public String provider_first_name = "";
    public String provider_last_name = "";
    public String provider_phone = "";
    public String provider_email = "";

    public Order(String id_order, String id_user, String id_service, String payment_code, String code, String code_valided, String code_notified, String created_at, String client_pseudo, String client_first_name, String client_last_name, String client_phone, String client_email, String titre, String description, String price, String image, String address, String city, String latitude, String longituge, String publication_date, String id_category_service, String active, String id_provider, String provider_pseudo, String provider_first_name, String provider_last_name, String provider_phone, String provider_email) {
        this.id_order = id_order;
        this.id_user = id_user;
        this.id_service = id_service;
        this.payment_code = payment_code;
        this.code = code;
        this.code_valided = code_valided;
        this.code_notified = code_notified;
        this.created_at = created_at;
        this.client_pseudo = client_pseudo;
        this.client_first_name = client_first_name;
        this.client_last_name = client_last_name;
        this.client_phone = client_phone;
        this.client_email = client_email;
        this.titre = titre;
        this.description = description;
        this.price = price;
        this.image = image;
        this.address = address;
        this.city = city;
        this.latitude = latitude;
        this.longituge = longituge;
        this.publication_date = publication_date;
        this.id_category_service = id_category_service;
        this.active = active;
        this.id_provider = id_provider;
        this.provider_pseudo = provider_pseudo;
        this.provider_first_name = provider_first_name;
        this.provider_last_name = provider_last_name;
        this.provider_phone = provider_phone;
        this.provider_email = provider_email;
    }
}
