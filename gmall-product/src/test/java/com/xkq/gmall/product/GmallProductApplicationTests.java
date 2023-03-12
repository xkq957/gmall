package com.xkq.gmall.product;

import com.xkq.gmall.product.entity.CategoryEntity;
import com.xkq.gmall.product.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallProductApplicationTests {

	@Resource
	private CategoryService categoryService;

	@Test
	public void contextLoads() {
		List<CategoryEntity> list = categoryService.list();
		System.out.println(list);
	}

}
