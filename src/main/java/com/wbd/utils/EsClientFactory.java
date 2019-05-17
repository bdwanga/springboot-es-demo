package com.wbd.utils;

import org.apache.http.*;
import org.elasticsearch.client.*;
import java.util.*;
import java.io.*;

public class EsClientFactory
{
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String HTTP_SCHEME = "http";
    private static EsClientFactory esClientFactory;
    private static String node;
    private static String cluseter;
    private RestClientBuilder builder;
    private RestClient restClient;
    private HttpHost[] httpHosts;
    private RestHighLevelClient restHighLevelClient;

    private EsClientFactory() {
    }

    public static EsClientFactory getEsClientFactory(final String nodes) {
        EsClientFactory.node = nodes;
        return EsClientFactory.esClientFactory;
    }

    public void init() {
        this.analyseHosts();
        this.builder = RestClient.builder(this.httpHosts);
        this.restClient = this.builder.build();
        this.restHighLevelClient = new RestHighLevelClient(this.restClient);
    }

    private void analyseHosts() {
        final String[] esNodes = EsClientFactory.node.split(",");
        final List<HttpHost> hostList = new ArrayList<HttpHost>();
        for (int i = 0; i < esNodes.length; ++i) {
            final String[] nodeSegment = esNodes[i].split(":");
            final String host = nodeSegment[0];
            final int port = Integer.parseInt(nodeSegment[1]);
            hostList.add(new HttpHost(host, port, "http"));
        }
        final HttpHost[] hostsArray = new HttpHost[hostList.size()];
        this.httpHosts = hostList.toArray(hostsArray);
    }

    public RestClient getClient() {
        return this.restClient;
    }

    public RestHighLevelClient getRestHighLevelClient() {
        return this.restHighLevelClient;
    }

    public void close() {
        if (this.restClient != null) {
            try {
                this.restClient.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static {
        EsClientFactory.esClientFactory = new EsClientFactory();
    }
}
