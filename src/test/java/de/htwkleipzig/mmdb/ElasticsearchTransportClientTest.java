package de.htwkleipzig.mmdb;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.ImmutableList;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.pilato.spring.elasticsearch.ElasticsearchTransportClientFactoryBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/integration/spring-integration-context.xml" })
public class ElasticsearchTransportClientTest {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchTransportClientTest.class);

    /**
     * @uml.property  name="transportFactoryBean"
     * @uml.associationEnd  readOnly="true"
     */
    @Autowired
    ElasticsearchTransportClientFactoryBean transportFactoryBean;

    /**
     * @uml.property  name="client"
     * @uml.associationEnd  multiplicity="(0 -1)" elementType="org.elasticsearch.cluster.node.DiscoveryNode"
     */
    TransportClient client;

    @Before
    public void init() {
        logger.info("init TransportClient");
        for (String node : transportFactoryBean.getEsNodes()) {
            logger.info("node {}", node);
        }
        try {
            client = (TransportClient) transportFactoryBean.getObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            // create an index called myindex
            logger.info("create an index called myindex");
            client.admin().indices().create(new CreateIndexRequest("myindex")).actionGet();
        } catch (IndexAlreadyExistsException ex) {
            logger.warn("already exists", ex);
        }
    }

    @Test
    public void test_transport_client() {
        System.out.println("starting test");
        assertNotNull("Client must not be null...", client);
        assertTrue("Client should be an instance of org.elasticsearch.client.transport.TransportClient.",
                client instanceof org.elasticsearch.client.transport.TransportClient);

        // TransportClient tClient = (TransportClient) client;
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
        client.close();
    }
}
