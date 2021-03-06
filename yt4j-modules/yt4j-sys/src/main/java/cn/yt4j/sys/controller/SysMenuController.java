package cn.yt4j.sys.controller;

import cn.yt4j.core.domain.PageResult;
import cn.yt4j.core.domain.R;
import cn.yt4j.core.util.PageUtil;
import cn.yt4j.security.util.SecurityUtil;
import cn.yt4j.sys.entity.SysMenu;
import cn.yt4j.sys.entity.vo.MenuTreeVO;
import cn.yt4j.sys.entity.vo.Route;
import cn.yt4j.sys.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 资源权限(SysMenu)表控制层
 *
 * @author gyv12345@163.com
 * @since 2020-08-10 08:43:33
 */
@Api(tags = " 资源权限")
@AllArgsConstructor
@RestController
@RequestMapping("sys/menu")
public class SysMenuController {

	/**
	 * 服务对象
	 */
	private final SysMenuService sysMenuService;

	@ApiOperation("动态路由，动态菜单")
	@GetMapping("nav")
	public R<List<Route>> nav() {
		return R.ok(this.sysMenuService.nav(SecurityUtil.getUser().getId()));
	}

	@ApiOperation("菜单树")
	@GetMapping("menuTree")
	public R<List<MenuTreeVO>> menuTree() {
		return R.ok(this.sysMenuService.menuTree());
	}

	/**
	 * 分页查询所有数据
	 * @param sysMenu 查询实体
	 * @return 所有数据
	 */
	@ApiOperation("分页查询")
	@GetMapping("list")
	public R<PageResult<SysMenu>> selectAll(SysMenu sysMenu) {
		return R.ok(this.sysMenuService.page(PageUtil.page(), new QueryWrapper<>(sysMenu)));
	}

	/**
	 * 通过主键查询单条数据
	 * @param id 主键
	 * @return 单条数据
	 */
	@ApiOperation("获取单个")
	@GetMapping("{id}")
	public R<SysMenu> selectOne(@PathVariable Serializable id) {
		return R.ok(this.sysMenuService.getById(id));
	}

	/**
	 * 新增数据
	 * @param sysMenu 实体对象
	 * @return 新增结果
	 */
	@ApiOperation("添加")
	@PostMapping("insert")
	public R insert(@RequestBody SysMenu sysMenu) {
		return R.ok(this.sysMenuService.save(sysMenu));
	}

	/**
	 * 修改数据
	 * @param sysMenu 实体对象
	 * @return 修改结果
	 */
	@ApiOperation("修改")
	@PutMapping("update")
	public R update(@RequestBody SysMenu sysMenu) {
		return R.ok(this.sysMenuService.updateById(sysMenu));
	}

	/**
	 * 删除数据
	 * @param id 主键结合
	 * @return 删除结果
	 */
	@ApiOperation("删除")
	@DeleteMapping("delete/{id}")
	public R delete(@PathVariable Long id) {
		return R.ok(this.sysMenuService.removeById(id));
	}

}