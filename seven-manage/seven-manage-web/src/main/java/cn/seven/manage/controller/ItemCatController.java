package cn.seven.manage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seven.manage.pojo.ItemCat;
import cn.seven.manage.service.ItemCatService;
import cn.seven.manage.utils.JsonUtil;
@RequestMapping("item/cat")
@Controller
public class ItemCatController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemCatController.class);
	
	
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 查询所有商品目录
	 * @param parentId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ItemCat>> quertItemCatList(@RequestParam(value = "id" , defaultValue = "0") Long parentId) {
		try {
			LOGGER.info("*****************开始打印日志啦*************");
			ItemCat param = new ItemCat();
			param.setParentId(parentId);
			List<ItemCat> itemList = this.itemCatService.queryListByWhere(param);
			System.out.println("---------------------------");
			System.out.println(JsonUtil.Object2Json(itemList));
			System.out.println("---------------------------");
			return ResponseEntity.ok(itemList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
