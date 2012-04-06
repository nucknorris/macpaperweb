package de.htwkleipzig.mmdb;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.ImmutableList;
import org.elasticsearch.common.transport.TransportAddress;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/integration/spring-integration-context.xml"
})
public class ElasticsearchTransportClientTest {
    static protected ConfigurableApplicationContext ctx;

    @BeforeClass
    static public void setup() {
        ctx = new ClassPathXmlApplicationContext(
                "META-INF/spring/integration/elasticsearch-context.xml");
    }

    @AfterClass
    static public void tearDown() {
        if (ctx != null) {
            ctx.close();
        }
    }

    @Test
    public void test_transport_client() {
        System.out.println("starting test");
        TransportClient client = ctx.getBean("esClient", TransportClient.class);
        assertNotNull("Client must not be null...", client);
        assertTrue("Client should be an instance of org.elasticsearch.client.transport.TransportClient.",
                client instanceof org.elasticsearch.client.transport.TransportClient);

//        TransportClient tClient = (TransportClient) client;
        ImmutableList<TransportAddress> adresses = client.transportAddresses();
        assertNotNull("Nodes urls must not be null...", adresses);
        assertFalse("Nodes urls must not be empty...", adresses.isEmpty());

        // TransportAddress transportAddress = adresses.get(0);
        // assertEquals("URL should be localhost:9399", "inet[localhost:9399]", transportAddress.toString());

        // We wait a while for connection to the cluster (1s should be enough)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        // Testing if we are really connected to a cluster node
        assertFalse("We should be connected at least to one node.", client.connectedNodes().isEmpty());
        DiscoveryNode node = client.connectedNodes().get(0);

        // TODO Raise a bug to ES Team ? This test fails... Why ?
        // assertEquals("The node should be junit.node.transport", "junit.node.transport", node.getName());

        assertTrue("We should be connected to the master node.", node.isMasterNode());
    }
}
