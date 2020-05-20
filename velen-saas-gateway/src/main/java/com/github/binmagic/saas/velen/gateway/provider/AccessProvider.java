//package com.github.binmagic.saas.velen.gateway.provider;
//
//import com.github.binmagic.saas.velen.authority.api.VerifyApi;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//
//@Component
//public class AccessProvider
//{
//
//	private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
//
//	@Autowired
//	private ReactiveStringRedisTemplate redisTemplate;
//
//	@Autowired
//	VerifyApi verifyApi;
//
//	public Mono<Void> writeVerify(String userId)
//	{
//		return verifyApi.writeVerify(userId).then();
//	}
//
//	public Mono<Boolean> getVerify(String userId, String path, String method)
//	{
//		return Flux.concat(redisTemplate.opsForSet().members(userId + "_" + method)
//				, redisTemplate.opsForSet().members(userId + "_" + "ALL"))
//				.filter(url -> path.startsWith(url) || ANT_PATH_MATCHER.match(url, path))
//				.collectList().flatMap(data -> {
//					if(data.isEmpty())
//					{
//						return Mono.just(false);
//					}
//					return Mono.just(true);
//				});
//	}
//
//
//}
