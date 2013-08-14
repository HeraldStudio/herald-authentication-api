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
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * 认证服务的实现类
 * @author Ray <predator.ray@gmail.com>
 */
class AuthenticationServiceImpl implements AuthenticationService {

    private static final String PARAM_USER = "username";
    private static final String PARAM_PASS = "password";
    private String baseUri;
    private HttpClient httpClient;

    public AuthenticationServiceImpl(String baseUri, HttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    @Override
    public StudentUser authenticate(String username, String password)
            throws AuthenticationServiceException {
        PostMethod post = new PostMethod(baseUri);
        post.setQueryString(new NameValuePair[] {
                new NameValuePair(PARAM_USER, username),
                new NameValuePair(PARAM_PASS, password)
        });
        try {
            int statusCode = httpClient.executeMethod(post);

            if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                return null;
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new AuthenticationServiceException(
                        "Unexpected status: " + statusCode
                );
            }

            String csv = post.getResponseBodyAsString();
            String values[] = csv.split(",");

            if (values == null || values.length != 2) {
                throw new AuthenticationServiceException(
                        String.format("%s\n%s\n",
                                "Unexpected server response:\n", csv
                        )
                );
            }

            String name = values[0];
            String cardNumber = values[1];
            return new StudentUser(name, cardNumber);
        } catch (IOException ex) {
            throw new AuthenticationServiceException(ex);
        } finally {
            post.releaseConnection();
        }
    }
}
