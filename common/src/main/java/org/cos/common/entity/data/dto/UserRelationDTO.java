package org.cos.common.entity.data.dto;

import lombok.Data;
import org.cos.common.entity.data.po.User;

@Data
public class UserRelationDTO {
    private User level0;
    private User level1;
    private User level2;
}