package com.sy.common.core.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author Monster
 * @version v1.0
 */
public class HttpUtils {

    private static final RestClient REST_CLIENT = RestClient.create();

    /**
     * get请求
     *
     * @param url            请求路径
     * @param queryParams    请求参数
     * @param requestHeaders 自定义请求头
     * @param responseType   响应体类型
     * @return 响应结果
     */

    public static <T> ResponseEntity<T> get(String url, MultiValueMap<String, String> queryParams, HttpHeaders requestHeaders, Class<T> responseType) throws Exception {
        if (CollectionUtils.isEmpty(queryParams)) {
            queryParams = new LinkedMultiValueMap<>();
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(queryParams);
        String uri = uriBuilder.buildAndExpand(queryParams).toUriString();

        HttpHeaders finalRequestHeaders = buildHeaders(requestHeaders);

        return REST_CLIENT.get()
                .uri(uri)
                .headers(headers -> headers.addAll(finalRequestHeaders))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                })
                .toEntity(responseType);

    }

    /**
     * post请求
     *
     * @param url            请求路径
     * @param body           请求参数
     * @param requestHeaders 自定义请求头
     * @param responseType   响应体类型
     * @return 响应结果
     */
    public static <T> ResponseEntity<T> post(String url, String body, HttpHeaders requestHeaders, Class<T> responseType) throws Exception {
        if (!StringUtils.hasText(body)) {
            body = new HashMap<>().toString();
        }

        HttpHeaders finalRequestHeaders = buildHeaders(requestHeaders);

        return REST_CLIENT.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.addAll(finalRequestHeaders))
                .body(body)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                })
                .toEntity(responseType);
    }

    private static HttpHeaders buildHeaders(HttpHeaders requestHeaders) {
        if (Objects.isNull(requestHeaders)) {
            requestHeaders = new HttpHeaders();
        }
        return requestHeaders;
    }
}
