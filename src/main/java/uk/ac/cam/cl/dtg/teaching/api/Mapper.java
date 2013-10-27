package uk.ac.cam.cl.dtg.teaching.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class Mapper {

	private Mapper() {}
	
	public static Map<String,?> map(Date time) {
		
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE dd MMM yyyy");
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("EEE dd MMM yyyy 'at' HH:mm");
		
		return ImmutableMap.of(
				"timeMillis",time.getTime(),
				"formattedTime",timeFormatter.format(time),
				"formattedDate",dateFormatter.format(time),
				"formattedDateTime",dateTimeFormatter.format(time)
				);
	}
}
