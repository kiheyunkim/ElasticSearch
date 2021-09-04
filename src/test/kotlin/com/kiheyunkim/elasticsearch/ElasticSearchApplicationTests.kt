package com.kiheyunkim.elasticsearch

import com.kiheyunkim.elasticsearch.search.service.SearchService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ElasticSearchApplicationTests {

	@Autowired
	private lateinit var searchService: SearchService;

	@Test
	fun contextLoads() {
		searchService.postElasticSearchData("test")

		searchService.deleteElasticSearchIndex("test")
	}

}
