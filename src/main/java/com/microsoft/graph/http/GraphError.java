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

import com.google.gson.annotations.SerializedName;

import com.microsoft.graph.core.GraphErrorCodes;
import com.google.common.base.CaseFormat;
import com.google.gson.annotations.Expose;

import javax.annotation.Nullable;

import java.util.Objects;

import javax.annotation.Nonnull;

/**
 * Represents an error returned by the service
 */
public class GraphError {

    /** The error message */
    @SerializedName("message")
    @Expose()
    @Nullable
    public String message;

    /** The error code */
    @SerializedName("code")
    @Expose()
    @Nullable
    public String code;

    /** The inner error */
    @SerializedName("innererror")
    @Nullable
    public GraphInnerError innererror;

    /**
     * Determine if the given error code is the one that is expected
     *
     * @param expectedCode the expected error code
     * @return <b>true</b> if the error code matches, and <b>false</b> if there was no match
     */
    public boolean isError(@Nonnull final GraphErrorCodes expectedCode) {
        Objects.requireNonNull(expectedCode, "parameter expectedCode cannot be null");
        if (transformErrorCodeCase(code).equalsIgnoreCase(expectedCode.toString())) {
            return true;
        }
        GraphInnerError innerError = innererror;
        while (null != innerError) {
            if (transformErrorCodeCase(innerError.code).equalsIgnoreCase(expectedCode.toString())) {
                return true;
            }
            innerError = innerError.innererror;
        }
        return false;
    }
    /**
     * Transforms the text error code into the format expected by the value of the enum
     * @param original original lowser Camel cased error code
     * @return the resulting upper undescore cased error code
     */
    @Nonnull
    protected String transformErrorCodeCase(@Nonnull final String original) {
        Objects.requireNonNull(original, "parameter original cannot be null");
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, original);
    }

    /**
     * Makes a deep copy of this GraphError
     * @return The copy of this GraphError
     */
    @Nonnull
    public final GraphError copy() {
        GraphError errorCopy = new GraphError();
        errorCopy.message = this.message;
        errorCopy.code = this.code;
        if(this.innererror != null) {
            errorCopy.innererror = this.innererror.copy();
        }
        return errorCopy;
    }
}
