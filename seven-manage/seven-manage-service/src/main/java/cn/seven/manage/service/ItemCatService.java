package cn.seven.manage.service;

import org.springframework.stereotype.Service;

import cn.seven.manage.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat>{
	
	
	//注：使用spring4的新特性后，对泛型的注入，就不需要在子类中再次实现父类的方法和注入mapper了
	/*@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public Mapper<ItemCat> getMapper() {
		return this.itemCatMapper;
	}*/
}
