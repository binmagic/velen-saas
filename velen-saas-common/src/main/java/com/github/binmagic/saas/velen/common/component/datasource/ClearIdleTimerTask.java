package com.github.binmagic.saas.velen.common.component.datasource;

import java.util.TimerTask;

public class ClearIdleTimerTask extends TimerTask
{

	@Override
	public void run()
	{
		DDSHolder.getInstance().clearIdleDBS();
	}
}
