package main.model;

/**
 * Router.java stores the address of all the view pages.
 */
public class Router {

    /**
	 * Return Address of Register Page.
	 * @return Address of Register Page.
	 */
    public static String RegisterPage(){
        return "/main/view/RegisterPage.fxml";
    }

    /**
	 * Return address of Login Page.
	 * @return Address of Login Page.
	 */
    public static String LoginPage(){
        return "/main/view/LoginPage.fxml";
    }

    /**
	 * Return address of Donate Page.
	 * @return Address of Donate Page.
	 */
    public static String DonatePage(){
        return "/main/view/DonatePage.fxml";
    }

    /**
	 * Return address of Request Page.
	 * @return Address of Request Page.
	 */
    public static String RequestPage(){
        return "/main/view/RequestPage.fxml";
    }

    /**
	 * Return address of Distribute Page.
	 * @return Address of Distribute Page.
	 */
    public static String DistributePage(){
        return "/main/view/DistributePage.fxml";
    }

    /**
	 * Return address of DC History Page.
	 * @return Address of DC History Page.
	 */
    public static String DCHistoryPage(){
        return "/main/view/DCHistoryPage.fxml";
    }

    /**
	 * Return address of DC Collection Page.
	 * @return Address of DC Collection Page.
	 */
    public static String DCCollectionPage(){
        return "/main/view/DCCollectionPage.fxml";
    }

    /**
	 * Return address of Collection Status Page.
	 * @return Address of Collection Status Page.
	 */
    public static String CollectionStatusPage(){
        return "/main/view/CollectionStatusPage.fxml";
    }


    ////// start of session Data and ViewPath //////

    /**
     * Get the Database Filename for current Login Session.
     * @param identity Current Login Session User Identity (Donor/Ngo)
     * @return Database Filename
     */
    public static String getDataFileName(String identity){
        String filename = "";
        switch (identity) {
            case "Donor":
                filename = "donor_acc";
                break;
            case "NGO":
                filename = "ngo_acc";
                break;
            case "DC Admin":
                filename = "dc_admin_acc";
                break;
        }
        return filename;
    }

    /**
     * Get the View Path for current Login Session.
     * @param identity Current Login Session User Identity (Donor/Ngo)
     * @return View Path
     */
    public static String getViewPath(String identity){
        String viewpath = "";
        switch (identity) {
            case "Donor":
                viewpath = "/main/view/DonatePage.fxml";
                break;
            case "NGO":
                viewpath = "/main/view/RequestPage.fxml";
                break;
            case "DC Admin":
                viewpath = "/main/view/DistributePage.fxml";
                break;
        }
        return viewpath;
    }

    ////// end of session Data and ViewPath //////
}