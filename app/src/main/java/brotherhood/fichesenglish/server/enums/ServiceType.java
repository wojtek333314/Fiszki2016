package brotherhood.fichesenglish.server.enums;


public enum ServiceType {
    GET_DATABASE,
    CHECK_VERSION,
    REGISTER_POINTS;

    private static final String SERVER_PATH = "http://www.datastore.waw.pl/Fiche2016/getDatabase.php/";

    public static String getURL(ServiceType serviceType){
        switch(serviceType){

            case GET_DATABASE:
                return SERVER_PATH + "getList.php";
            case CHECK_VERSION:
                return SERVER_PATH + "checkVersion.php";
            case REGISTER_POINTS:
                return SERVER_PATH + "updatePoints.php";
        }
        return "Service path is invalid";
    }
}
