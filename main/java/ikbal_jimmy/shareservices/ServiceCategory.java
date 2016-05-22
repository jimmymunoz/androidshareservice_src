package ikbal_jimmy.shareservices;

/**
 * Created by H.ikbal on 11/05/2016.
 */
public class ServiceCategory {


    private String  id_service;
    private String titre;
    private String active;
    private String description;
    private String id_category_service;


    public ServiceCategory(String id_service, String titre, String active, String description, String id_category_service) {
        this.id_service = id_service;
        this.titre = titre;
        this.active = active;
        this.description = description;
        this.id_category_service = id_category_service;
    }
}
