package com.github.binmagic.saas.velen.common.component.datasource;

import cn.hutool.core.lang.Tuple;
import io.reactivex.Scheduler;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import lombok.extern.slf4j.Slf4j;
import org.davidmoten.rx.jdbc.ConnectionProvider;
import org.davidmoten.rx.jdbc.Database;
import org.davidmoten.rx.jdbc.SelectBuilder;
import org.davidmoten.rx.jdbc.UpdateBuilder;
import org.davidmoten.rx.jdbc.pool.NonBlockingConnectionPool;
import org.davidmoten.rx.jdbc.pool.Pools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@ConditionalOnBean(DBConfig.class)
@Slf4j
public class DynamicDataSource
{

	@Autowired
	DBConfig dbConfig;

	private Scheduler scheduler;

	@PostConstruct
	private void postInit()
	{
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.scheduler = new ExecutorScheduler(executor, false);
	}

	public Mono<SelectBuilder> select(String sql)
	{
		return getSource().map(dbs -> dbs.select(sql));
	}

	public Mono<UpdateBuilder> update(String sql)
	{
		return getSource().map(dbs -> dbs.update(sql));
	}

	public Mono<Database> getSource()
	{

		return DBIdentifier.getAppId()
				.map(appId -> new Tuple(appId, DDSHolder.getInstance().getDBS(appId)))
				.map(tuple -> {
					try
					{
						String appId = tuple.get(0).toString();
						Database dbs = tuple.get(1);
						if(dbs == null)
						{
							dbs = initDBS(appId);
							DDSHolder.getInstance().addDBS(appId, dbs);
						}
						return dbs;
					}
					catch(SQLException ex)
					{
						log.error("catch {}", ex);
					}
					return null;
				});
	}

	private Database initDBS(String projectCode) throws SQLException
	{

		String url = String.format(dbConfig.getUrl(), projectCode);

		Connection connection =
				DriverManager.getConnection(url, dbConfig.getUsername(), dbConfig.getPassword());

		NonBlockingConnectionPool pool = Pools.nonBlocking().scheduler(scheduler)
				.connectionProvider(new ConnectionProvider()
				{
					public Connection get()
					{
						return connection;
					}

					public void close()
					{
					}
				})
				.build();

		return Database.from(pool);
	}

}
