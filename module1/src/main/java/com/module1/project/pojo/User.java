package com.module1.project.pojo;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private
            String
                    id,
                    username,
                    password,
                    role,
                    permission;
    private
            List<String>
                    permissionList,
                    roleList;
}
