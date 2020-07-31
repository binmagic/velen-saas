package com.github.binmagic.saas.velen.common.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetUtil
{

	public static List<Map<String, Object>> toListMap(ResultSet rs)
	{
		try
		{
			List<Map<String, Object>> result = new ArrayList<>();

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next())
			{
				Map<String, Object> map = new HashMap();
				for(int i = 1; i <= columnCount; i++)
				{
					map.put(rsmd.getColumnName(i), rs.getObject(i));
				}
				result.add(map);
			}
			return result;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

}
