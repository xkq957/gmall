package com.xkq.gmall.search;

import com.alibaba.fastjson.JSON;
import com.xkq.gmall.search.config.GmallElasticSearchConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallSearchApplicationTests {

	@Autowired
	private RestHighLevelClient restHighLevelClient;
	@Test
	public void contextLoads() {
		System.out.println(restHighLevelClient);
		System.out.println("1");
	}

	@Data
	class User {
		private String userName;
		private Integer age;
		private String gender;
	}

	/**
	 * 测试储存数据到es
	 * 更新也可以
	 * @throws IOException
	 */
	@Test
	public void indexData() throws IOException {
		IndexRequest indexRequest = new IndexRequest ("users");
		indexRequest.id("EbseF4cBpzbMho98Fu0F");
		User user = new User();
		user.setUserName("李四");
		user.setAge(21);
		user.setGender("男");
		String jsonString = JSON.toJSONString(user);
		//设置要保存的内容
		indexRequest.source(jsonString, XContentType.JSON);
		//执行创建索引和保存数据
		IndexResponse index = restHighLevelClient.index(indexRequest, GmallElasticSearchConfig.COMMON_OPTIONS);



		System.out.println(index);

	}

	@Test
	public void deleteData() throws IOException {
		DeleteRequest deleteRequest = new DeleteRequest("users", "ELsEF4cBpzbMho98fe0p");

		restHighLevelClient.delete(deleteRequest, GmallElasticSearchConfig.COMMON_OPTIONS);
	}

}
