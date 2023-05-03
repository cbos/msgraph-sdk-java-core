package com.microsoft.graph.requests.upload;

import com.microsoft.graph.models.IUploadSession;
import com.microsoft.kiota.*;
import com.microsoft.kiota.serialization.Parsable;
import com.microsoft.kiota.serialization.ParsableFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import okhttp3.Response;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * UploadSessionRequestBuilder class to get and delete an UploadSession.
 * @param <T> The type of object being uploaded.
 */
public class UploadSessionRequestBuilder<T extends Parsable> {

    private final UploadResponseHandler responseHandler;
    private final RequestAdapter requestAdapter;
    private final String urlTemplate;
    private final ParsableFactory<T> factory;
    /**
     * Create a new UploadSessionRequest.
     * @param sessionUrl The uploadSession url to use in the request.
     * @param requestAdapter The RequestAdapted to execute the request.
     * @param factory The ParsableFactory defining the instantiation of the object being uploaded.
     */
    @SuppressFBWarnings
    public UploadSessionRequestBuilder(@Nonnull String sessionUrl,
                                       @Nonnull final RequestAdapter requestAdapter,
                                       @Nonnull final ParsableFactory<T> factory) {
        this.responseHandler = new UploadResponseHandler();
        this.requestAdapter = Objects.requireNonNull(requestAdapter);
        this.urlTemplate = Objects.requireNonNull(sessionUrl);
        this.factory = Objects.requireNonNull(factory);
    }
    /**
     * Gets the specified UploadSession.
     * @return the IUploadSession
     */
    @Nonnull
    public CompletableFuture<IUploadSession> getAsync() {
        RequestInformation requestInformation = toGetRequestInformation();
        NativeResponseHandler nativeResponseHandler = new NativeResponseHandler();
        requestInformation.setResponseHandler(nativeResponseHandler);
        return this.requestAdapter.sendPrimitiveAsync(requestInformation, InputStream.class, null)
            .thenCompose( i -> responseHandler.handleResponse((Response) nativeResponseHandler.getValue(), factory))
            .thenCompose(result -> CompletableFuture.completedFuture(result.uploadSession));
    }
    private RequestInformation toGetRequestInformation() {
        RequestInformation requestInformation = new RequestInformation();
        requestInformation.httpMethod = HttpMethod.GET;
        requestInformation.urlTemplate = this.urlTemplate;
        return requestInformation;
    }
    /**
     * Deletes the specified UploadSession.
     * @return Once returned the UploadSession has been deleted.
     */
    @Nonnull
    public CompletableFuture<Void> deleteAsync() {
        RequestInformation requestInfo = this.toDeleteRequestInformation();
        return this.requestAdapter.sendPrimitiveAsync(requestInfo, Void.class, null);
    }
    private RequestInformation toDeleteRequestInformation() {
        RequestInformation requestInformation = new RequestInformation();
        requestInformation.httpMethod = HttpMethod.DELETE;
        requestInformation.urlTemplate = this.urlTemplate;
        return requestInformation;
    }
}