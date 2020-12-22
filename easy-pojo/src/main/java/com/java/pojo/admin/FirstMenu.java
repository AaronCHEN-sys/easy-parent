package com.java.pojo.admin;

import lombok.Data;

import java.util.List;

/**
 * Description:	   <br/>
 * Date:     2020/12/21 20:02 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Data
public class FirstMenu {
    private Long firstId;
    private String firstText;
    private List<SecondMenu> secondMenuList;
}
