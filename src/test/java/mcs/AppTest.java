package mcs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class AppTest {

    @Container
    private static final ElasticsearchContainer elasticsearch = new ElasticsearchContainer(
        DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch").withTag("7.11.1")
    );

    @Container 
    private static final GenericContainer<?> cockroachContainer = new GenericContainer<>(
        DockerImageName.parse("cockroachdb/cockroach")
            .withTag("latest")
    )
    .withExposedPorts(26257)
    .withCommand("start-single-node --insecure");

    @Test
    public void elasticHealth() {
        assertTrue(elasticsearch.isRunning());
        int mappedPort = elasticsearch.getMappedPort(9200);
        App app = new App();
        Assertions.assertDoesNotThrow(() -> {
            app.elastic(mappedPort);
        });
    }

    @Test
    public void cockroachHealth() {
        assertTrue(cockroachContainer.isRunning());
    }
}
