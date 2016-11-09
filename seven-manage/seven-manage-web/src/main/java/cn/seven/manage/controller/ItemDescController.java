package cn.seven.manage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.seven.manage.pojo.ItemDesc;
import cn.seven.manage.service.ItemDescService;
@RequestMapping("item/desc")
@Controller
public class ItemDescController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemDescController.class);
	
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping(value = "{itemId}" , method = RequestMethod.GET)
	public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable("itemId") Long itemId) {
		LOGGER.info("begin to query itemDesc, the itemId is : [{}]" , itemId);
		try {
			ItemDesc itemDesc = this.itemDescService.queryById(itemId);
			if (null == itemDesc) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
