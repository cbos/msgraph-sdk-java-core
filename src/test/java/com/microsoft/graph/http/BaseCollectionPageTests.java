package com.microsoft.graph.http;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import okhttp3.Request;

import com.google.gson.JsonObject;
import com.microsoft.graph.core.IBaseClient;
import com.microsoft.graph.serializer.ISerializer;

/**
 * Test cases for {@see BaseCollectionPage}
 */
public class BaseCollectionPageTests {

    private BaseRequestBuilder<String> mRequestBuilder;
    private ArrayList<String> list;
    private BaseCollectionPage<String, BaseRequestBuilder<String>> baseCollectionPage;
    private String requestUrl = "https://a.b.c/";

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        list = new ArrayList<String>();
        list.add("Object1");
        list.add("Object2");
        list.add("Object3");
        final IBaseClient<Request> mBaseClient = mock(IBaseClient.class);
        mRequestBuilder = new BaseRequestBuilder<>(requestUrl, mBaseClient, null) {};
        baseCollectionPage = new BaseCollectionPage<>(list, mRequestBuilder) {};
    }

    @Test
    public void testNotNull() {
        assertNotNull(baseCollectionPage);
    }

    @Test
    public void testCurrentPage() {
        assertEquals(3,baseCollectionPage.getCurrentPage().size());
        assertEquals("Object2", baseCollectionPage.getCurrentPage().get(1));

        //Test to ensure the returned list from getCurrentPage is a deep copy of baseCollectionPage contents.
        ArrayList<String> baseCollectionContentsCopy = (ArrayList<String>) baseCollectionPage.getCurrentPage();
        baseCollectionContentsCopy.remove(1);
        assertNotEquals(baseCollectionPage.getCurrentPage().size(), baseCollectionContentsCopy.size());
    }

    @Test
    public void testNextPage() {
        assertEquals(mRequestBuilder, baseCollectionPage.getNextPage());
    }

    @Test
    public void testRawObject() {
        ISerializer serializer = mock(ISerializer.class);
        JsonObject jsonObject = new JsonObject();
        baseCollectionPage.setRawObject(serializer,jsonObject);
    }

}
