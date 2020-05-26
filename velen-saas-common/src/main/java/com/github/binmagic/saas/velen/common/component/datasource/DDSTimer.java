package com.github.binmagic.saas.velen.common.component.datasource;

import org.davidmoten.rx.jdbc.Database;

public class DDSTimer
{
	/**
	 * 空闲时间周期。超过这个时长没有访问的数据库连接将被释放。默认为10分钟。
	 */
	private static long idlePeriodTime = 10 * 60 * 1000;

	/**
	 * 动态数据源
	 */
	private Database dbs;

	/**
	 * 上一次访问的时间
	 */
	private long lastUseTime;

	public DDSTimer(Database dbs)
	{
		this.dbs = dbs;
		this.lastUseTime = System.currentTimeMillis();
	}

	/**
	 * 更新最近访问时间
	 */
	public void refreshTime()
	{
		lastUseTime = System.currentTimeMillis();
	}

	/**
	 * 检测数据连接是否超时关闭。
	 *
	 * @return true-已超时关闭; false-未超时
	 */
	public boolean checkAndClose()
	{

		if(System.currentTimeMillis() - lastUseTime > idlePeriodTime)
		{
			dbs.close();
			return true;
		}

		return false;
	}

	public Database getDBS()
	{
		return dbs;
	}
}
