package cn.seven.manage.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.seven.manage.pojo.ItemParam;
import cn.seven.manage.service.ItemParamService;

@RequestMapping("item/param")
@Controller
public class ItemParamController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemParamController.class);
	
	@Autowired
	private ItemParamService itemParamService;
	
	/**
	 * 根据商品类目ID查询模板
	 * @param itemCatId
	 * @return
	 */
	@RequestMapping(value = "{itemCatId}" , method = RequestMethod.GET)
	public ResponseEntity<ItemParam> queryItemParamByItemId(@PathVariable("itemCatId") Long itemCatId){
		try {
			LOGGER.info("begin to query itmeParam by itemCatId ,the param is : [{}]" , itemCatId);
			ItemParam itemParam = new ItemParam();
			itemParam.setItemCatId(itemCatId);
			ItemParam itemParam2 = this.itemParamService.queryOne(itemParam);
			return ResponseEntity.ok(itemParam2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	/**
	 * 新增商品规格参数
	 * @param itemCatId
	 * @param itemParam
	 * @return
	 */
	@RequestMapping(value = "{itemCatId}" , method = RequestMethod.POST)
	public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId") Long itemCatId , ItemParam itemParam){
		try {
			itemParam.setCreated(new Date());
			itemParam.setUpdated(itemParam.getCreated());
			itemParam.setItemCatId(itemCatId);
			this.itemParamService.save(itemParam);
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
