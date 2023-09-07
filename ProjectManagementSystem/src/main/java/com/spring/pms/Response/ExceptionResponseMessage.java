package com.spring.pms.Response;

public enum ExceptionResponseMessage {
    PROJECT_DETAILS_NOT_FOUND("Project_details_not_found"),
    USER_DETAILS_NOT_FOUND("User_details_not_found"),
    TASK_DETAILS_NOT_FOUND("Task_details_not_found"),
    SUB_TASK_DETAILS_NOT_FOUND("Sub_task_details_not_found");

    private final String message;

    ExceptionResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

