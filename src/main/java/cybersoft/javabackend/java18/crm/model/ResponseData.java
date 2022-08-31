package cybersoft.javabackend.java18.crm.model;

import lombok.Data;

@Data
public class ResponseData {
    private int statusCode;
    private String message;
    private Object data;
    private boolean isSuccess;

    public ResponseData getResponseData(int result, String message) {
        ResponseData responseData = new ResponseData();
        if (result == 1) {
            responseData.setStatusCode(200);
            responseData.setMessage(message);
            responseData.setSuccess(true);
        } else {
            responseData.setStatusCode(201);
            responseData.setMessage(message);
            responseData.setSuccess(false);
        }
        return responseData;
    }
}
