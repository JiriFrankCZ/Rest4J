package org.frank.rest4j.simple.util;

import com.google.common.collect.ImmutableMap;
import org.frank.rest4j.util.CommonsUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;


/**
 * Created by FrankJ on 9.7.2015.
 */
public class CommonsUtilTest {

	@Test
	public void testGetClassNameFromFQNNullValue() {
		String result = CommonsUtil.getClassNameFromFQN(null);
		org.springframework.util.Assert.notNull(result);
		Assert.assertEquals("", result);
	}

	@Test
	public void testGetClassNameFromFQNStrangeValue() {
		String result = CommonsUtil.getClassNameFromFQN("4as6d5f4.-1545645sdf.ddf");
		org.springframework.util.Assert.notNull(result);
		Assert.assertEquals("ddf", result);
	}

	@Test
	public void testGetClassNameFromFQNValidValue() {
		String result = CommonsUtil.getClassNameFromFQN("package.package.Test");
		org.springframework.util.Assert.notNull(result);
		Assert.assertEquals("Test", result);
	}

	@Test
	public void testReplaceParametresBasic() {
		final String testText = "Test with {first} and {SECOND} and even {Thir%5d} parametres.";
		final String testReplacedText = "Test with 1 and TestParam and even 145TestMap parametres.";

		Map<String, Object> testParameters =
				new ImmutableMap.Builder<String, Object>()
						.put("first", 1)
						.put("SECOND", "TestParam")
						.put("Thir%5d", "145TestMap")
						.build();

		String result = CommonsUtil.replaceParametres(testText, testParameters);

		Assert.assertEquals("Values not correctly replaced", testReplacedText, result);
	}
}