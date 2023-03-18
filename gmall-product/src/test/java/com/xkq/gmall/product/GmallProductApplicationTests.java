package com.xkq.gmall.product;

import com.xkq.gmall.product.entity.CategoryEntity;
import com.xkq.gmall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
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

	@Test
	public void getParentPath() {
		Long[] categoryPath = categoryService.findCategoryPath(225L);
		log.info("完整路径:[{}]", Arrays.asList(categoryPath));
	}

}
