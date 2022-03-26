package main.model;

public class DistAids {
    
    private DonateInfo donateInfo;
    private RequestInfo requestInfo;

    private Integer donatedQty;

    public DistAids(DonateInfo donateInfo, RequestInfo requestInfo) {
        this.donateInfo = donateInfo;
        this.requestInfo = requestInfo;
    }


    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    public DonateInfo getDonateInfo() {
        return donateInfo;
    }

    public void setDonateInfo(DonateInfo donateInfo) {
        this.donateInfo = donateInfo;
    }

    public Integer getDonatedQty() {
        return donatedQty;
    }

    public void setDonatedQty(Integer donatedQty) {
        this.donatedQty = donatedQty;
    }

    ////////////////////////////////////////////////////////////////

    public void matchAids(){
        // 6 // 3
        if( donateInfo.getRemainQty() > requestInfo.getRemainQty() ){
            int donateRemainQty = donateInfo.getRemainQty();
            int requestRemainQty = requestInfo.getRemainQty();

            donateInfo.setRemainQty(donateRemainQty - requestRemainQty);
            this.donatedQty = requestInfo.getRemainQty();

            requestInfo.setRemainQty(0);
        }
        // 3 // 6
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
