public class ReportObject {
    private String status;
    private String errorMessage;
    private String methodName;

    public ReportObject(int status, String errorMessage, String methodName) {
        this.status = (status == 500 ? "KO" : "OK");
        this.errorMessage = errorMessage;
        this.methodName = methodName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
