package uk.ac.cam.cl.dtg.teaching.api;

import java.util.Date;
import java.util.Map;

public class Mapper {

	private Mapper() {
	}

	public static Map<String, ?> map(Date time) {
		return new FormattedDate(time).toMap();
	}
}
