package main.model;

public class DistAids {
    
    private DonateInfo donateInfo;
    private RequestInfo requestInfo;

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

    ////////////////////////////////////////////////////////////////

    public void matchAids(){
        // 6 // 3
        if( donateInfo.getRemainQty() >= requestInfo.getRemainQty() ){
            int requestRemainQty = requestInfo.getRemainQty();

            donateInfo.setRemainQty(donateInfo.getRemainQty() - requestRemainQty);
            requestInfo.setRemainQty(0);
        }
        // 3 // 6
        else if (donateInfo.getRemainQty() < requestInfo.getRemainQty()){
            int donateRemainQty = donateInfo.getRemainQty();

            donateInfo.setRemainQty(0);
            requestInfo.setRemainQty(requestInfo.getRemainQty() - donateRemainQty);
        }
            
    }

}
