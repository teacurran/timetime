package com.approachingpi.timetime.view.tags;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.TransformingComparator;

/**
 * Date: 6/3/11
 *
 * @author T. Curran
 */
public class Functions {

	public static final String REGEX_PHONE_NUMBER = "(\\d{10})?";

	public static String concat(
		final String string1,
		final String string2 ) {

		return concatVar( string1, string2 );
	}

	public static String concat(
		final String string1,
		final String string2,
		final String string3 ) {

		return concatVar( string1, string2, string3 );
	}

	public static String concatVar( final String... inStrings ) {

		StringBuilder sb = new StringBuilder();
		for ( String string : inStrings ) {
			sb.append( string );
		}
		return sb.toString();
	}

	// TODO only works in North American phone numbers.
	// Should add support for international and EPP phone number format
	// international: ^\+(?:[0-9] ?){6,14}[0-9]$
	// epp: ^\+[0-9]{1,3}\.[0-9]{4,14}(?:x.+)?$
	// see: http://blog.stevenlevithan.com/archives/validate-phone-number
	/**
	 * Takes an un-formatted phone number and returns a formatted phone number
	 * @param phoneNumber a north american Phone number
	 * @return phoneNumber formatted like (123) 456-7890
	 */
	public static String phoneFormat(
		final String phoneNumber ) {

		if ( phoneNumber == null ) {
			return "";
		}

		if ( !phoneNumber.matches( REGEX_PHONE_NUMBER ) ) {
			return phoneNumber;
		}
		// build the phone number
		// (###) ###-####
		final StringBuilder phoneReturn = new StringBuilder();
		phoneReturn.append( "(" );
		phoneReturn.append( phoneNumber.substring( 0, 3 ) );
		phoneReturn.append( ") " );
		phoneReturn.append( phoneNumber.substring( 3, 6 ) );
		phoneReturn.append( "-" );
		phoneReturn.append( phoneNumber.substring( 6, 10 ) );

		return phoneReturn.toString();

	}

	public static List< Object > sort(
		List< Object > inList,
		String inSortBy ) {

		if ( inList == null ) {
			return null;
		}

		Transformer lowercaseTransformer = new Transformer() {
			@Override
			public Object transform(
				final Object input ) {

				if ( input == null ) {
					return null;
				}
				if ( !(input instanceof String) ) {
					return input;
				}
				return ((String) input).toLowerCase();
			}
		};
		final Comparator lowercaseComparator =
			new TransformingComparator( lowercaseTransformer );
		final NullComparator nullComparator = new NullComparator( lowercaseComparator );

		@SuppressWarnings("unchecked")
		final Comparator<Object> beanComparator =
			new BeanComparator( inSortBy, nullComparator );

		Collections.sort( inList, beanComparator);

		return inList;
	}
}
