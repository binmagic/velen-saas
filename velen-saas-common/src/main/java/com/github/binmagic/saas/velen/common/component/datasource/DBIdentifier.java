package com.github.binmagic.saas.velen.common.component.datasource;

import com.github.binmagic.saas.velen.common.constant.Constant;
import reactor.core.publisher.Mono;

public class DBIdentifier
{
	public static Mono<String> getAppId()
	{
		return Mono.subscriberContext().map(context -> context.get(Constant.APP_ID));
	}

	public static Mono<Void> setAppId(String appId){
		return Mono.subscriberContext()
				.doOnNext(context -> context.put(Constant.APP_ID, appId)).then();
	}

}
