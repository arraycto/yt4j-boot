package cn.yt4j.sys.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author gyv12
 */
@Data
@ApiModel(value = "设置角色菜单对象", description = "设置角色菜单")
public class RoleMenuDTO implements Serializable {

	private static final long serialVersionUID = 8163461364967823330L;

	@NotNull
	@ApiModelProperty(value = "角色ID")
	private Long roleId;

	@NotNull
	@ApiModelProperty(value = "菜单按钮")
	private List<Long> menuIds;

}
