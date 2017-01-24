package org.springframework.cloud.stream.app.pgcopy.sink;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.test.junit.AbstractExternalResourceTestSupport;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by trisberg on 1/24/17.
 */
public class PostgresTestSupport extends AbstractExternalResourceTestSupport<DataSource> {

	private ConfigurableApplicationContext context;

	protected PostgresTestSupport() {
		super("POSTGRES");
	}

	@Override
	protected void cleanupResource() throws Exception {
		context.close();
	}

	@Override
	protected void obtainResource() throws Exception {
		context = new SpringApplicationBuilder(Config.class).web(false).run();
		DataSource dataSource = context.getBean(DataSource.class);
		Connection con = DataSourceUtils.getConnection(dataSource);
		DataSourceUtils.releaseConnection(con, dataSource);
	}

	@Configuration
	@EnableAutoConfiguration
	public static class Config {

	}
}
