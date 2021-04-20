package com.java.pojo.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Description:	   <br/>
 * Date:     2021/04/19 20:15 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart implements Serializable {

    private List<GoodsItem> goodsItemList;
}
