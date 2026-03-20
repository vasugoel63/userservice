package org.example.models;

import org.example.entities.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoDTO extends UserInfo {
    // model hides some field like passowrd and all which are in database layer and
    // this is used in api
    private String userName; // user_name
    private String lastName;
    private Long phoneNumber;
    private String email;
}