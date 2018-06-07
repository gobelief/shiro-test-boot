package com.module1.project.pojo;

import com.module1.comms.utils.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class User extends BaseEntity {
    private
            String
//                    id,
                    username,
                    password,
                    role,
                    permission;
    private
            List<String>
                    permissionList,
                    roleList;
}
