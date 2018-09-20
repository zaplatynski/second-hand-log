package de.marza.firstspirit.modules.logging.console.utilities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.is;

/**
 * The type Level test.
 */
public class LevelTest {

	private Level tesling;

	/**
	 * Sets up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		tesling = Level.DEFAULT;
	}

	/**
	 * Refine by next fragement error.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void refineByNextFragementError() throws Exception {
		final Level result = tesling.valueOfFragment("DEBUG INFO Error WARN This is a message...");

		assertThat(result, is(Level.ERROR));
	}

	/**
	 * Refine by next fragement warning.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void refineByNextFragementWarning() throws Exception {
		final Level result = tesling.valueOfFragment("DEBUG info WARN This is a message...");

		assertThat(result, is(Level.WARN));
	}

	/**
	 * Refine by next fragement info.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void refineByNextFragementInfo() throws Exception {
		final Level result = tesling.valueOfFragment("iNFO DEBUG This is a message...");

		assertThat(result, is(Level.INFO));
	}

	/**
	 * Refine by next fragement debug.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void refineByNextFragementDebug() throws Exception {
		final Level result = tesling.valueOfFragment("DEBUG This is a message...");

		assertThat(result, is(Level.DEBUG));
	}

	/**
	 * Refine by next fragement.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void refineByNextFragement() throws Exception {
		final Level result = tesling.valueOfFragment("This is a message...");

		assertThat(result, is(Level.DEFAULT));
	}

	/**
	 * Refine by next fragement null.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void refineByNextFragementNull() throws Exception {
		final Level result = Level.WARN.valueOfFragment(null);

		assertThat(result, is(Level.WARN));
	}

}