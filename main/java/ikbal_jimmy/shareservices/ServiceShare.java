package ikbal_jimmy.shareservices;

/**
 * Created by jimmymunoz on 25/04/16.
 */
public class ServiceShare {

    private String id_service;
    private String titre;
    private String active;
    private  String  adress;

    public String getCategory() {
        return category;
    }

    public String getPrix() {
        return prix;
    }

    private String description;

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;
    private String prix;

    public ServiceShare(String id_service, String titre, String active, String adress, String description, String category, String prix) {
        this.id_service = id_service;
        this.titre = titre;
        this.active = active;
        this.adress = adress;
        this.description = description;
        this.category = category;
        this.prix = prix;
    }

    public String getId_service() {
        return id_service;
    }

    public void setId_service(String id_service) {
        this.id_service = id_service;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
