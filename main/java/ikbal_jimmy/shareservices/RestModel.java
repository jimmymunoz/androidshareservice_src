package ikbal_jimmy.shareservices;

/**
 * Created by jimmymunoz on 17/04/16.
 */
public abstract class RestModel {

    protected String urlServer;
    public String getUrlServer()
    {
        return urlServer;
    }

    public RestModel()
    {
        //urlServer = "http://jm.viajemosdev.info/restandroid/test.json";//http://46.101.40.23/shareserviceserver/v1/register
        urlServer = "http://46.101.40.23/shareserviceserver/v1/";
    }
    /*
    Sr.No	Method & description
    1	get(String name)
    This method just Returns the value but in the form of Object type

    2	getBoolean(String name)
    This method returns the boolean value specified by the key

    3	getDouble(String name)
    This method returns the double value specified by the key

    4	getInt(String name)
    This method returns the integer value specified by the key

    5	getLong(String name)
    This method returns the long value specified by the key

    6	length()
    This method returns the number of name/value mappings in this object..

    7	names()
    This method returns an array containing the string names in this object.
    */
}
