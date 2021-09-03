package com.kiheyunkim.elasticsearch.search.service

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.xcontent.XContentType
import org.springframework.stereotype.Service

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-03
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
@Service
class SearchService(private val client: RestHighLevelClient) {

	//reference: https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-delete-index.html
	fun deleteElasticSearchIndex(index: String): Boolean {
		val deleteIndexRequest = DeleteIndexRequest(index)

		//TimeOut 지정
		//deleteIndexRequest.timeout("2m")
		//deleteIndexRequest.timeout(TimeValue.timeValueMinutes(2))

		val acknowledgedResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT)
		return acknowledgedResponse.isFragment
	}

	fun postElasticSearchData(index: String) {
		val createIndexRequest = CreateIndexRequest(index)
		createIndexRequest.settings(
			Settings.builder()
				.put("index.number_of_shards", 1)
				.put("index.number_of_replicas", 1)
		)
		createIndexRequest.mapping(
			"{\n" +
					"  \"properties\": {\n" +
					"    \"message\": {\n" +
					"      \"type\": \"text\"\n" +
					"    }\n" +
					"  }\n" +
					"}",
			XContentType.JSON
		)

		client.indices().create(createIndexRequest,RequestOptions.DEFAULT)
	}

}