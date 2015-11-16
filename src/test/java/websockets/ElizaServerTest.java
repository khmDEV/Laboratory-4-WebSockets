package websockets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import websockets.web.ElizaServerEndpoint;

public class ElizaServerTest {

	private Server server;
	
	@Before
	public void setup() throws DeploymentException {
        server = new Server("localhost", 8025, "/websockets", new HashMap<String,Object>(), ElizaServerEndpoint.class);
		server.start();
	}
	
	@Test(timeout=1000)
	public void onOpen() throws DeploymentException, IOException, URISyntaxException, InterruptedException {
		CountDownLatch latch = new CountDownLatch(3);
		List<String> list = new ArrayList<>();
		ClientEndpointConfig configuration = ClientEndpointConfig.Builder.create().build();
		ClientManager client = ClientManager.createClient();	
		client.connectToServer(new Endpoint() {

			@Override
			public void onOpen(Session session, EndpointConfig config) {
				session.addMessageHandler(new MessageHandler.Whole<String>() {

					@Override
					public void onMessage(String message) {
						list.add(message);
                        latch.countDown();
					}
				});
			}
			
		}, configuration, new URI("ws://localhost:8025/websockets/eliza"));
		latch.await();
		assertEquals(3, list.size());
		assertEquals("The doctor is in.", list.get(0));
	}
	
	@Test(timeout=1000)
	@Ignore
	public void onChat() throws DeploymentException, IOException, URISyntaxException, InterruptedException {
		// COMPLETE
		List<String> list = new ArrayList<>();
		ClientEndpointConfig configuration = ClientEndpointConfig.Builder.create().build();
		ClientManager client = ClientManager.createClient();	
		client.connectToServer(new Endpoint() {

			@Override
			public void onOpen(Session session, EndpointConfig config) {
				
				// COMPLETE
				
				session.addMessageHandler(new MessageHandler.Whole<String>() {

					@Override
					public void onMessage(String message) {
						list.add(message);
                        // COMPLETE
					}
				});
			}
			
		}, configuration, new URI("ws://localhost:8025/websockets/eliza"));
		// COMPLETE
		// COMPLETE
		// COMPLETE
	}

	
	@After
	public void close() {
		server.stop();
	}
}