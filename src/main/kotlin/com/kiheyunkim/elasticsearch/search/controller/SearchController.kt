package com.kiheyunkim.elasticsearch.search.controller

import com.kiheyunkim.elasticsearch.common.response.ElasticSearchResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-03
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Controller
class SearchController {

	@GetMapping("/getIndexes")
	fun getElasticIndexing(): ElasticSearchResponse<*>{
		return ElasticSearchResponse(true)
	}

	@GetMapping("/reIndexes")
	fun getReIndexing(): ElasticSearchResponse<*>{
		return ElasticSearchResponse(true)
	}
}