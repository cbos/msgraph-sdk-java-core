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

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.microsoft.graph.serializer.AdditionalDataManager;
import com.microsoft.graph.serializer.IJsonBackedObject;
import com.microsoft.graph.serializer.ISerializer;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Represents the error response body returned by the service
 */
public class GraphErrorResponse implements IJsonBackedObject {

    private AdditionalDataManager additionalDataManager = new AdditionalDataManager(this);

    /** The error returned by the response */
    @SerializedName("error")
    @Expose()
    @Nullable
    public GraphError error;

    /**
     * The raw representation of this class when deserialized
     */
    @Expose(serialize = false, deserialize = false)
    @Nullable
    public JsonObject rawObject;

    /**
     * Sets the raw JSON object
     */
    @Override
    public void setRawObject(@Nonnull final ISerializer serializer, @Nonnull final JsonObject json) {
        rawObject = Objects.requireNonNull(json, "parameter json cannot be null");
    }

    @Override
    @Nullable
    @SuppressFBWarnings
    public final AdditionalDataManager additionalDataManager() {
        return additionalDataManager;
    }

    /**
     * Make and return a deep copy of this GraphErrorResponse.
     * @return The copy of this GraphErrorResponse.
     */
    @Nonnull
    public GraphErrorResponse copy() {
        GraphErrorResponse responseCopy = new GraphErrorResponse();
        responseCopy.additionalDataManager = this.additionalDataManager;
        responseCopy.rawObject = this.rawObject == null ? null : this.rawObject.deepCopy();
        responseCopy.error = this.error == null ? null : this.error.copy();
        return responseCopy;
    }
}
