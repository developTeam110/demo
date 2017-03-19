import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.seven.common.bean.ItemCatData;


public class TestJacon {
	

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() throws Exception {
//		fail("Not yet implemented");
		ItemCatData item = new ItemCatData();
		ObjectMapper mapper = new ObjectMapper();
		item.setName("jfdjflkdjs");
		item.setUrl("hjfhdskjfhds");
		System.out.println(mapper.writeValueAsString(item));
	}

}
