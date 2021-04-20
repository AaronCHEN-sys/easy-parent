package com.java.pojo.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description:	   <br/>
 * Date:     2021/04/19 20:12 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsItem implements Serializable {

    private Long goodsId;//商品id

    private Integer count;//购买数量
}
