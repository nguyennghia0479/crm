package cybersoft.javabackend.java18.crm.model;

import lombok.Data;

@Data
public class ResponseData {
    private int statusCode;
    private String message;
    private Object data;
    private boolean isSuccess;

    public void getResponseData(int result, String message) {
        if (result == 1) {
            this.statusCode = 200;
            this.message = message;
            this.isSuccess = true;
        } else {
            this.statusCode = 200;
            this.message = message;
            this.isSuccess = false;
        }
    }
}
