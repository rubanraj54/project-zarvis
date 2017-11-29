package zarvis.bakery.models;

public class Status {

    private boolean status = false;

    public Status(boolean initialStatus){
        this.status = initialStatus;
    }


    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
