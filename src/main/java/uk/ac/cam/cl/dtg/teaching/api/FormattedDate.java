package uk.ac.cam.cl.dtg.teaching.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.collect.ImmutableMap;

public class FormattedDate implements Comparable<FormattedDate> {

	private long timeMillis;
	private String formattedTime;
	private String formattedDate;
	private String formattedDateTime;

	public FormattedDate(Date date) {
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE dd MMM yyyy");
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
				"EEE dd MMM yyyy 'at' HH:mm");
		this.timeMillis = date.getTime();
		this.formattedDate = dateFormatter.format(date);
		this.formattedTime = timeFormatter.format(date);
		this.formattedDateTime = dateTimeFormatter.format(date);
	}

	public long getTimeMillis() {
		return timeMillis;
	}

	public String getFormattedTime() {
		return formattedTime;
	}

	public String getFormattedDate() {
		return formattedDate;
	}

	public String getFormattedDateTime() {
		return formattedDateTime;
	}

	public ImmutableMap<String, ?> toMap() {
		return ImmutableMap.of("timeMillis", timeMillis, "formattedTime",
				formattedTime, "formattedDate", formattedDate,
				"formattedDateTime", formattedDateTime);
	}

	@Override
	public int compareTo(FormattedDate o) {
		return new Long(timeMillis).compareTo(o.timeMillis);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (timeMillis ^ (timeMillis >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormattedDate other = (FormattedDate) obj;
		if (timeMillis != other.timeMillis)
			return false;
		return true;
	}
}