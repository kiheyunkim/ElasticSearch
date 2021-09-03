package com.kiheyunkim.elasticsearch.common.response

/**
 * IDE : IntelliJ IDEA
 * Created by kiheyunkim@gmail.com on 2021-09-03
 * GitHub : http://github.com/kiheyunkim
 * Comment :
 */
data class ElasticSearchResponse<T>(var result: T, var errorCode: String?, var errorMessage: String?) {
	constructor(result: T) : this(result, null, null)
}
