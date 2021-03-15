package mcs;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class App {

    public void elastic(int port) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", port, "http"))
        );

        ClusterHealthResponse health = client.cluster().health(new ClusterHealthRequest(), RequestOptions.DEFAULT);        
        System.out.println(health.getClusterName());
    }
}
