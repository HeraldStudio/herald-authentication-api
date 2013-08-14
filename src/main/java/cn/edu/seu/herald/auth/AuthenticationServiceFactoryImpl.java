/*
 * The MIT License
 *
 * Copyright 2013 Herald Studio, Southeast University.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cn.edu.seu.herald.auth;

import org.apache.commons.httpclient.HttpClient;

/**
 * 认证服务工厂的实现类
 * @author Ray <predator.ray@gmail.com>
 */
public class AuthenticationServiceFactoryImpl
        implements AuthenticationServiceFactory {

    private static final HttpClient DEFAULT_CLIENT = new HttpClient();
    protected static String DEFAULT_URI =
            "http://herald.seu.edu.cn/authentication/";
    private HttpClient httpClient = DEFAULT_CLIENT;
    private String baseUri = DEFAULT_URI;

    /**
     * 自定义HTTP客户端。不设置时，将使用默认的HTTP客户端
     * @param httpClient 自定义的HTTP客户端
     * @see org.apache.commons.httpclient.HttpClient
     */
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 自定义服务的URI。不设置时，将使用http://herald.seu.edu.cn/authentication/
     * @param baseUri 服务的URI
     */
    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return new AuthenticationServiceImpl(baseUri, httpClient);
    }
}
