package cn.seven.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seven.manage.mapper.ItemMapper;
import cn.seven.manage.pojo.Item;
import cn.seven.manage.pojo.ItemDesc;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class ItemService extends BaseService<Item>{
	
	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDescService itemDescService;
	
	public void saveItem(Item item, String desc) {
		//保存商品数据到数据库
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		item.setId(null);
		item.setStatus(1);
		super.save(item);
		
		//保存商品描述到数据库中
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		this.itemDescService.save(itemDesc);
	}

	public PageInfo<Item> queryItemList(Integer page, Integer rows) {
		PageHelper.startPage(page,rows);
		Example example = new Example(Item.class);
		example.setOrderByClause("updated DESC");
		List<Item> items = this.itemMapper.selectByExample(example);
		return new PageInfo<Item>(items);
	}
	
	@RequestMapping(value = "{itemId}" , method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable("itemId") Long itemId) {
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

	public void updateItem(Item item, String desc) {
		//设置itme的初始值
		item.setUpdated(new Date());
		item.setCreated(null);
		super.updateSelectiveById(item);
		
		//更新商品描述
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		this.itemDescService.updateSelectiveById(itemDesc);
		
	}

}
