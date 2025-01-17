// ------------------------------------------------------------------------------
// Copyright (c) 2017 Microsoft Corporation
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sub-license, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
// ------------------------------------------------------------------------------

package com.microsoft.graph.http;

import com.microsoft.graph.core.IBaseClient;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.serializer.IJsonBackedObject;

import javax.annotation.Nullable;

import java.util.Objects;

import javax.annotation.Nonnull;

/**
 * An HTTP request.
 *
 * @param <T> the response class
 */
public abstract class BaseWithReferenceRequest<T> extends BaseRequest<T> {

    /**
     * The request for the entity
     *
     * @param requestUrl     the request URL
     * @param client         the service client
     * @param requestOptions the options for this request
     * @param entityClass     the class for the entity
     */
    public BaseWithReferenceRequest(@Nonnull final String requestUrl, @Nonnull final IBaseClient<?> client, @Nullable final java.util.List<? extends com.microsoft.graph.options.Option> requestOptions,
                                    @Nonnull final Class<T> entityClass) {
        super(requestUrl, client, requestOptions, entityClass);
    }

    /**
     * Creates a new entity and invokes the callback with the result
     *
     * @param payload paylod to send to the service
     * @param newEntity entity to return
     * @return a future with the result
     */
    @Nonnull
    public java.util.concurrent.CompletableFuture<T> postAsync(@Nullable final T newEntity, @Nonnull final IJsonBackedObject payload) {
        Objects.requireNonNull(payload, "parameter payload cannot be null");
        return sendAsync(HttpMethod.POST, payload);
    }

    /**
     * Creates a new entity and invokes the callback with the result
     *
     * @param payload paylod to send to the service
     * @param newEntity entity to return
     * @return the entity once request is executed
     */
    @Nullable
    public T post(@Nullable final T newEntity, @Nonnull final IJsonBackedObject payload) throws ClientException {
        Objects.requireNonNull(payload, "parameter payload cannot be null");
        final T response = send(HttpMethod.POST, payload);
        if (response != null){
            return newEntity;
        }
        return null;
    }

    /**
     * Gets the entity an invokes the callback with it
     * @return a future with the result
     */
    @Nonnull
    public java.util.concurrent.CompletableFuture<T> getAsync() {
        return sendAsync(HttpMethod.GET, null);
    }

    /**
     * Gets the entity
     * @return the obtained entity
     */
    @Nullable
    public T get() throws ClientException {
       return send(HttpMethod.GET, null);
    }

    /**
     * Deletes the entity and invokes the callback
     * @return a future with the result
     */
    @Nonnull
    public java.util.concurrent.CompletableFuture<T> deleteAsync() {
        return sendAsync(HttpMethod.DELETE, null);
    }

    /**
     * Deletes the entity
     */
    public void delete() throws ClientException {
        send(HttpMethod.DELETE, null);
    }

    /**
     * Updates the entity and invokes the callback
     *
     * @param sourceObject object to update
     * @return a future with the result
     */
    @Nonnull
    public java.util.concurrent.CompletableFuture<T> patchAsync(@Nonnull final T sourceObject) {
        Objects.requireNonNull(sourceObject, "parameter sourceObject cannot be null");
        return sendAsync(HttpMethod.PATCH, sourceObject);
    }

    /**
     * Updates the entity
     *
     * @param sourceObject object to update
     * @return the updated entity
     */
    @Nullable
    public T patch(@Nonnull final T sourceObject) throws ClientException {
        Objects.requireNonNull(sourceObject, "parameter sourceObject cannot be null");
        return send(HttpMethod.PATCH, sourceObject);
    }
}
