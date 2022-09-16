package order.vo;

import com.xixi.mall.common.core.webbase.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户地址VO
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class UserAddrVo extends BaseVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long addrId;

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("是否默认地址 1是")
    private Integer isDefault;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("省ID")
    private Long provinceId;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("城市ID")
    private Long cityId;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区ID")
    private Long areaId;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("邮编")
    private String postCode;

    @ApiModelProperty("地址")
    private String addr;

    @ApiModelProperty("经度")
    private Double lng;

    @ApiModelProperty("纬度")
    private Double lat;

}
