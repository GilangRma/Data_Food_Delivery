package com.dimata.demo.user.demo_data_user.core.api;


/**
 * Convert form to record.
 *
 * @param <R> Record
 */
public interface RecordAdapter<R> {
	/**
	 * Convert to record which this record will be used
	 * to create new record.
	 * 
	 * @return Record object to be used as new record.
	 */
	R convertNewRecord();
	/**
	 * Convert to record
	 * 
	 * @return Record.
	 */
	R convertToRecord();
}
