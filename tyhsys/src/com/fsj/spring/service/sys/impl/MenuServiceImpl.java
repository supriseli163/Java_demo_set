package com.fsj.spring.service.sys.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fsj.spring.model.sys.SysMenu;
import com.fsj.spring.service.TServiceImpl;
import com.fsj.spring.service.sys.MenuService;
import com.fsj.spring.util.DataGridModel;
/**
 * Title:用户管理服务层实现类
 *
 * @author 唐有欢
 * @version 1.0 , 2013-1-18 创建
 */
@Service("menuService")
public class MenuServiceImpl extends TServiceImpl implements MenuService {
	/**
	 * 查询菜单列表
	 *@param dgm	grid对象
	 *@param menu	查询的菜单对象
	 *@return
	 *@throws Exception
	 */
	public Map<String, Object> getPageListBySQL(DataGridModel dgm, SysMenu menu) throws Exception {
		String totalQuery = "select count(*) from sys_menu menu";
		//多表查询的例子
		String fullQuery = "select menu.*,menu.id as uid,parent.sm_name as parentMenuName from sys_menu menu left join sys_menu parent on menu.sm_parent = parent.sm_code ";
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();

		if (menu != null) {
			if (StringUtils.isNotBlank(menu.getSmCode())) {
				sb.append(" and menu.SM_CODE like :smCode");
				params.put("smCode", "%" + menu.getSmCode() + "%");
			}
			if (StringUtils.isNotBlank(menu.getSmName())) {
				sb.append(" and menu.SM_NAME like :smName");
				params.put("smName", "%" + menu.getSmName() + "%");
			}
			if (StringUtils.isNotBlank(menu.getSmParent())) {
				sb.append(" and menu.SM_PARENT like :smParent");
				params.put("smParent", "%" + menu.getSmParent() + "%");
			}
		}

		if (sb.toString().startsWith(" and")) {
			sb.delete(0, 4);
			sb.insert(0, " where ");
		}
		totalQuery += sb.toString();
		fullQuery += sb.toString();
		return baseDao.getPageListBySQL(dgm, totalQuery, fullQuery, params);
	}

}
