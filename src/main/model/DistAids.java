package main.model;

/**
 * DistAids Class contains the Donate Info and Request Info.
 * It is use to format the Matched Aids Result.
 */
public class DistAids {
    
    private DonateInfo donateInfo;
    private RequestInfo requestInfo;

    private Integer donatedQty;
    private String status;

    /**
     * Constructs DistAids object which contains 
     * the donation information and request information.
     * @param donateInfo donation information
     * @param requesntInfo request information
     */
    public DistAids(DonateInfo donateInfo, RequestInfo requestInfo) {
        this.donateInfo = donateInfo;
        this.requestInfo = requestInfo;
    }

    /**
     * Gets the request information.
     * @return request information
     */
    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    /**
     * Sets the request information.
     * @param requestInfo request information
     */
    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    /**
     * Gets the donation information.
     * @return donation information
     */
    public DonateInfo getDonateInfo() {
        return donateInfo;
    }

    /**
     * Sets the donation information.
     * @param donateInfo donation information
     */
    public void setDonateInfo(DonateInfo donateInfo) {
        this.donateInfo = donateInfo;
    }

    /**
     * Gets the donated quantity.
     * @return donated quantity
     */
    public Integer getDonatedQty() {
        return donatedQty;
    }

    /**
     * Sets the donated quantity.
     * @param donatedQty donated quantity
     */
    public void setDonatedQty(Integer donatedQty) {
        this.donatedQty = donatedQty;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    ////////////////////////////////////////////////////////////////

    /**
     * Performs aid matching and updates the remaining item quantity
     * for both request information and donation information.
     */
    public void matchAids(){
        
        if( donateInfo.getRemainQty() > requestInfo.getRemainQty() ){
            int donateRemainQty = donateInfo.getRemainQty();
            int requestRemainQty = requestInfo.getRemainQty();

            donateInfo.setRemainQty(donateRemainQty - requestRemainQty);
            this.donatedQty = requestInfo.getRemainQty();

            requestInfo.setRemainQty(0);
        }
        else if (donateInfo.getRemainQty() < requestInfo.getRemainQty()){
            int donateRemainQty = donateInfo.getRemainQty();
            int requestRemainQty = requestInfo.getRemainQty();

            this.donatedQty = donateInfo.getRemainQty();
            donateInfo.setRemainQty(0);

            requestInfo.setRemainQty(requestRemainQty - donateRemainQty);
        }
        else if (donateInfo.getRemainQty() == requestInfo.getRemainQty()){
            int donateRemainQty = donateInfo.getRemainQty();
            int requestRemainQty = requestInfo.getRemainQty();

            this.donatedQty = donateInfo.getRemainQty();

            donateInfo.setRemainQty(0);
            requestInfo.setRemainQty(0);
        }

            
    }

}
