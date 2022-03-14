package hu.gdf.quickaid.component;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class JedisConnector {

	private static final Logger LOGGER = LoggerFactory.getLogger(JedisConnector.class);

	public static JedisPool getPool() {
		try {

			String rUrl = System.getenv("REDIS_URL");
			rUrl = rUrl == null ? System.getenv("REDIS_LOCAL_URL") : rUrl;
			if (rUrl == null) {
				LOGGER.error("Redis url missing.");
				throw new IllegalStateException("Redis connection is not present in connector.");
			}
			TrustManager bogusTrustManager = new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			};

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { bogusTrustManager }, new java.security.SecureRandom());

			HostnameVerifier bogusHostnameVerifier = (hostname, session) -> true;

			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxTotal(10);
			poolConfig.setMaxIdle(5);
			poolConfig.setMinIdle(1);
			poolConfig.setTestOnBorrow(true);
			poolConfig.setTestOnReturn(true);
			poolConfig.setTestWhileIdle(true);
			LOGGER.info("Presenting jedis pool.");
			return new JedisPool(poolConfig, URI.create(rUrl), sslContext.getSocketFactory(),
					sslContext.getDefaultSSLParameters(), bogusHostnameVerifier);

		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			LOGGER.error("Redis not available.");
			throw new RuntimeException("Cannot obtain Redis connection!", e);
		}
	}

}
