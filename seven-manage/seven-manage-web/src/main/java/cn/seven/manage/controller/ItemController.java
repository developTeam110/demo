package cn.seven.manage.controller;

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

import com.github.pagehelper.PageInfo;

import cn.seven.common.bean.EasyUIResult;
import cn.seven.manage.pojo.Item;
import cn.seven.manage.service.ItemService;
@RequestMapping("item")
@Controller
public class ItemController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
	
	
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 保存商品数据
	 * @param item：商品
	 * @param desc:商品描述
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item , @RequestParam("desc") String desc){
		try {
			LOGGER.info("begin to add item ,the param is : item [{}] , desc [{}]", item , desc);
			this.itemService.saveItem(item , desc);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 查询所有商品信息
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value = "page",defaultValue = "1" ) Integer page,
			@RequestParam(value = "rows" , defaultValue = "30") Integer rows){
		try {
			PageInfo<Item> pageInfo = this.itemService.queryItemList(page,rows);
			return ResponseEntity.ok(new EasyUIResult(pageInfo.getTotal(),pageInfo.getList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item ,@RequestParam("desc") String desc){
		try {
			this.itemService.updateItem(item , desc);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
 	
 
}
