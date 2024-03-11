package com.example.llz.gateway.filter;

import cn.dev33.satoken.stp.StpLogic;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Order(-1)
@Component
public class IdentityFilter implements GlobalFilter{

	private static final String EXCEPTION_MSG = "Response HTTP Code: %s, Body: %s";

	@Value("${sso.check-ticket-url:http://210.42.42.30:19004/sso-login.html}")
	private String check_ticket_url;

	public static final String TYPE = "sso_user";
	public static StpLogic stpLogic = new StpLogic(TYPE);
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if (stpLogic.isLogin()){
			return chain.filter(exchange);
		}
		exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
		return exchange.getResponse().setComplete();
	}

	/**
	 * http请求
	 *
	 * @param url 请求路径
	 * @return Mono 响应内容
	 */
	private Mono<String> doHttpRquest(String url, String header) {
		HttpClient secure = HttpClient.create().secure(t -> t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)));
		return WebClient.builder().clientConnector(new ReactorClientHttpConnector(secure)).build().post().uri(url).header("X-AToken", header).exchange().flatMap(response -> {
			if (response.statusCode().is2xxSuccessful()) {
				return response.bodyToMono(String.class);
			} else {
				return response.bodyToMono(String.class).flatMap(body -> Mono.error(new RuntimeException(String.format(EXCEPTION_MSG, response.statusCode(), body))));
			}
		});
	}
}
