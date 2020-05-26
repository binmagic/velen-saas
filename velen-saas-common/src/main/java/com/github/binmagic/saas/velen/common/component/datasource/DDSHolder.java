package com.github.binmagic.saas.velen.common.component.datasource;

import org.davidmoten.rx.jdbc.Database;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class DDSHolder
{
	/**
	 * 管理动态数据源列表。<工程编码，数据源>
	 */
	private Map<String, DDSTimer> ddsMap = new ConcurrentHashMap<>();

	/**
	 * 通过定时任务周期性清除不使用的数据源
	 */
	private static Timer clearIdleTask = new Timer();

	static
	{
		clearIdleTask.schedule(new ClearIdleTimerTask(), 5000, 60 * 1000);
	}

	;

	private DDSHolder()
	{
	}

	/*
	 * 获取单例对象
	 */
	public static DDSHolder getInstance()
	{
		return DDSHolderBuilder.instance;
	}

	public Database getDBS(String projectCode)
	{
		if(ddsMap.containsKey(projectCode))
		{
			DDSTimer ddsTimer = ddsMap.get(projectCode);
			ddsTimer.refreshTime();
			return ddsTimer.getDBS();
		}
		return null;
	}


	public Database addDBS(String projectCode, Database database)
	{
		DDSTimer ddsTimer = new DDSTimer(database);
		ddsMap.put(projectCode, ddsTimer);
		return ddsTimer.getDBS();
	}

	/**
	 * 清除超时无人使用的数据源。
	 */
	public synchronized void clearIdleDBS()
	{

		Iterator<Map.Entry<String, DDSTimer>> iter = ddsMap.entrySet().iterator();
		for(; iter.hasNext(); )
		{
			Map.Entry<String, DDSTimer> entry = iter.next();
			if(entry.getValue().checkAndClose())
			{
				iter.remove();
			}
		}
	}

	/**
	 * 单例构件类
	 */
	private static class DDSHolderBuilder
	{
		private static DDSHolder instance = new DDSHolder();
	}


}
