package org.jfree.data;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.jfree.data.Range; import org.junit.*;

public class RangeTest {
    private Range exampleRange;
    private Range exampleRange1;
    @BeforeClass public static void setUpBeforeClass() throws Exception {
    }


    @Before
    public void setUp() throws Exception { 
    	exampleRange = new Range(-1, 1);
    	exampleRange1 = new Range(1, 2);
    }
    
    
    	@Test
    	public void testCombineIgnoringNaNWithRange1NotNaN() {
    		// Create a non-NaN range
    		Range range1 = new Range(0.0, 1.0);
    		Range range2 = null;

    		// Test the combineIgnoringNaN method with a non-NaN range as range1 and null as range2
    		assertEquals(range1, Range.combineIgnoringNaN(range1, range2));
    	}
    
    
    	@Test
    	public void testMinWithNaN() {
    		double result1 = Range.combineIgnoringNaN(new Range(0, 5), new Range(Double.NaN, 10)).getLowerBound();
    		double result2 = Range.combineIgnoringNaN(new Range(Double.NaN, 5), new Range(0, 10)).getLowerBound();
    		assertEquals(0, result1, 0); //should return 0 when NaN in the second argument
    		assertEquals(0, result2, 0); // should return 0 when NaN is in the first argument
    	}

    	@Test
    	public void testMaxWithNaN() {
    		double result1 = Range.combineIgnoringNaN(new Range(0, 5), new Range(6, Double.NaN)).getUpperBound();
    		double result2 = Range.combineIgnoringNaN(new Range(6, Double.NaN), new Range(0, 5)).getUpperBound();
    		assertEquals(5, result1, 0); // should return 5 when NaN in the second argument
    		assertEquals(5, result2, 0); // should return 5 when NaN in the first argument
    	}
    
    
    	@Test(expected = IllegalArgumentException.class)
    	public void testConstructorWithInvalidRange() {
    		double lower = 5.0;
    		double upper = 2.0;
    		new Range(lower, upper); 
    	}

    	@Test
    	public void testIntersects() {
    		Range range = new Range(0.0, 5.0);
    		assertTrue(range.intersects(2.0, 3.0)); // valid intersection
    		assertFalse(range.intersects(6.0, 7.0)); // no intersection
    	}
    	
    	@Test
        public void testConstructorWithValidRange() {
            Range range = new Range(2.0, 5.0);
            assertEquals(2.0, range.getLowerBound(), 0.0);
            assertEquals(5.0, range.getUpperBound(), 0.0);
        }

        @Test
        public void testCombine() {
            Range range1 = new Range(2.0, 5.0);
            Range range2 = new Range(4.0, 7.0);
            Range combinedRange = Range.combine(range1, range2);
            assertEquals(2.0, combinedRange.getLowerBound(), 0.0);
            assertEquals(7.0, combinedRange.getUpperBound(), 0.0);
        }

        @Test
        public void testCombineIgnoringNaN() {
            Range range1 = new Range(2.0, 5.0);
            Range range2 = new Range(Double.NaN, 7.0);
            Range combinedRange = Range.combineIgnoringNaN(range1, range2);
            assertEquals(2.0, combinedRange.getLowerBound(), 0.0);
            assertEquals(7.0, combinedRange.getUpperBound(), 0.0);
        }
        
        @Test
        public void testCombineWithNullRanges() {
            Range range1 = new Range(2.0, 5.0);
            Range range2 = null;
            Range combinedRange = Range.combine(range1, range2);
            assertEquals(range1, combinedRange);
        }
        
        @Test(expected = IllegalArgumentException.class)
        public void testScaleWithNegativeFactor() {
            Range base = new Range(0, 10);
            Range.scale(base, -1.0);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testGetUpperBoundWithLowerGreaterThanUpper() {
            Range range = new Range(10, 0); // lower > upper
            range.getUpperBound();
        }

        
        @Test(expected = IllegalArgumentException.class)
        public void testGetLengthWithLowerGreaterThanUpper() {
            Range range = new Range(10, 0); // lower > upper
            range.getLength();
        }

        
        @Test
        public void testCombineIgnoringNaN11() {
            // test when both ranges are null
            assertNull(Range.combineIgnoringNaN(null, null));

            // test when range1 is null and range2 is NaN
            assertNull(Range.combineIgnoringNaN(null, new Range(Double.NaN, Double.NaN)));

            // test when range1 is null and range2 is not NaN
            Range range2 = new Range(5.0, 10.0);
            assertEquals(range2, Range.combineIgnoringNaN(null, range2));

            // test when range1 is NaN and range2 is null
            assertNull(Range.combineIgnoringNaN(new Range(Double.NaN, Double.NaN), null));

            // test when range1 is NaN and range2 is also NaN
            assertNull(Range.combineIgnoringNaN(new Range(Double.NaN, Double.NaN), new Range(Double.NaN, Double.NaN)));

            // test when range1 is NaN and range2 is not NaN
            Range range1NaN = new Range(Double.NaN, Double.NaN);
            assertNull(Range.combineIgnoringNaN(range1NaN, range2));

            // test when range1 is not NaN and range2 is null
            Range range1 = new Range(1.0, 3.0);
            assertEquals(range1, Range.combineIgnoringNaN(range1, null));

            // test when range1 is not NaN and range2 is NaN
            assertNull(Range.combineIgnoringNaN(range1, new Range(Double.NaN, Double.NaN)));

            // test when range1 and range2 are both not NaN
            Range range3 = new Range(2.0, 5.0);
            assertEquals(new Range(1.0, 10.0), Range.combineIgnoringNaN(range1, range3));
        }
        
        
        @Test
        public void testEquals() {
            Range range1 = new Range(0.0, 5.0);
            Range range2 = new Range(0.0, 5.0);
            Range range3 = new Range(1.0, 5.0);
            assertTrue(range1.equals(range2)); // equal ranges
            assertFalse(range1.equals(range3)); // different lower bound
        }

        @Test
        public void testIsNaNRange() {
            Range range1 = new Range(Double.NaN, Double.NaN);
            Range range2 = new Range(0.0, Double.NaN);
            Range range3 = new Range(Double.NaN, 5.0);
            Range range4 = new Range(0.0, 5.0);
            assertTrue(range1.isNaNRange()); // both bounds NaN
            assertTrue(range2.isNaNRange()); // upper bound NaN
            assertTrue(range3.isNaNRange()); // lower bound NaN
            assertFalse(range4.isNaNRange()); // no NaN
        }

        @Test
        public void testCombine1() {
            Range range1 = new Range(0.0, 5.0);
            Range range2 = new Range(2.0, 7.0);
            assertEquals(new Range(0.0, 7.0), Range.combine(range1, range2)); // combined range
            assertEquals(range1, Range.combine(range1, null)); // null range 2
            assertEquals(range2, Range.combine(null, range2)); // null range 1
            assertNull(Range.combine(null, null)); // both null ranges
        }

        @Test
        public void testCombineIgnoringNaN1() {
            Range range1 = new Range(Double.NaN, Double.NaN);
            Range range2 = new Range(0.0, 5.0);
            Range range3 = new Range(Double.NaN, 7.0);
            assertNull(Range.combineIgnoringNaN(range1, range2)); // range 1 NaN
            assertNull(Range.combineIgnoringNaN(range2, range1)); // range 1 NaN (reversed)
            assertEquals(new Range(0.0, 7.0), Range.combineIgnoringNaN(range2, range3)); //range 2 NaN
            assertEquals(new Range(0.0, 5.0), Range.combineIgnoringNaN(range2, null)); // null range 2
            assertEquals(range2, Range.combineIgnoringNaN(null, range2)); //null range 1
            assertNull(Range.combineIgnoringNaN(null, null)); // both null ranges
        }

        

        @Test(expected = IllegalArgumentException.class)
        public void testGetLengthWhenLowerGreaterThanUpper() {
            // create range where lower is greater than upper
            Range range = new Range(5.0, 3.0);
            range.getLength();
        }
    	

    	@Test
    	public void testCombineWithNullRange1() {
    		Range range2 = new Range(3, 8);
    		Range combinedRange = Range.combine(null, range2);
    		assertEquals(range2, combinedRange);
    	}

    	@Test
    	public void testCombineWithNullRange2() {
    		Range range1 = new Range(0, 5);
    		Range combinedRange = Range.combine(range1, null);
    		assertEquals(range1, combinedRange);
    	}

    	@Test
    	public void testCombineWithValidRanges() {
    		Range range1 = new Range(0, 5);
    		Range range2 = new Range(3, 8);
    		Range expectedRange = new Range(0, 8);
    		Range combinedRange = Range.combine(range1, range2);
    		assertEquals(expectedRange, combinedRange);
    	}
    	
    	@Test
        public void testCombineRanges() {
            Range range1 = new Range(0.0, 5.0);
            Range range2 = new Range(10.0, 15.0);
            Range combinedRange = Range.combine(range1, range2);
            assertEquals(new Range(0.0, 15.0), combinedRange);
        }

    	@Test
    	public void testConstrainWithinRange() {
    	    Range range = new Range(0, 10);
    	    double constrainedValue = range.constrain(5.5);
    	    assertEquals(5.5, constrainedValue, 0.001);
    	}

    	@Test
    	public void testConstrainBelowRange() {
    	    Range range = new Range(0, 10);
    	    double constrainedValue = range.constrain(-2);
    	    assertEquals(0.0, constrainedValue, 0.001);
    	}

    	@Test
    	public void testConstrainAboveRange() {
    	    Range range = new Range(0, 10);
    	    double constrainedValue = range.constrain(15);
    	    assertEquals(10.0, constrainedValue, 0.001);
    	}

    	@Test
    	public void testConstrainOnLowerBound() {
    	    Range range = new Range(0, 10);
    	    double constrainedValue = range.constrain(0);
    	    assertEquals(0.0, constrainedValue, 0.001);
    	}

    	@Test
    	public void testConstrainOnUpperBound() {
    	    Range range = new Range(0, 10);
    	    double constrainedValue = range.constrain(10);
    	    assertEquals(10.0, constrainedValue, 0.001);
    	}

    	
    	@Test
    	public void testContainsWithinRange() {
    	    Range range = new Range(0, 10);
    	    assertTrue(range.contains(5));
    	}

    	@Test
    	public void testContainsOnLowerBound() {
    	    Range range = new Range(0, 10);
    	    assertTrue(range.contains(0));
    	}

    	@Test
    	public void testContainsOnUpperBound() {
    	    Range range = new Range(0, 10);
    	    assertTrue(range.contains(10));
    	}

    	@Test
    	public void testContainsOutsideRange() {
    	    Range range = new Range(0, 10);
    	    assertFalse(range.contains(-2));
    	    assertFalse(range.contains(15));
    	}
    	
    	@Test
        public void testContainsNaN() {
            Range range = new Range(5.0, 15.0);
            assertFalse(range.contains(Double.NaN));
        }

    	
    	@Test
    	public void testIntersectsOverlap() {
    	    Range range = new Range(0, 10);
    	    assertTrue(range.intersects(5, 15));
    	    assertTrue(range.intersects(-2, 3));
    	}

    	@Test
    	public void testIntersectsNoOverlap() {
    	    Range range = new Range(0, 10);
    	    assertFalse(range.intersects(15, 20));
    	    assertFalse(range.intersects(-5, -2));
    	}
    	
    	@Test
        public void testIntersection() {
            Range range1 = new Range(0.0, 10.0);
            assertTrue(range1.intersects(5.0, 15.0));

            Range range2 = new Range(20.0, 30.0);
            assertFalse(range1.intersects(range2));
        }

    	
    	@Test
    	public void testShiftPositiveDelta() {
    	    Range baseRange = new Range(0, 5);
    	    Range shiftedRange = Range.shift(baseRange, 3);
    	    assertEquals(3.0, shiftedRange.getLowerBound(), 0.001);
    	    assertEquals(8.0, shiftedRange.getUpperBound(), 0.001);
    	}

    	@Test
    	public void testShiftNegativeDelta() {
    	    Range baseRange = new Range(0, 5);
    	    Range shiftedRange = Range.shift(baseRange, -2);
    	    assertEquals(-2.0, shiftedRange.getLowerBound(), 0.001);
    	    assertEquals(3.0, shiftedRange.getUpperBound(), 0.001);
    	}

    	@Test
    	public void testShiftZeroDelta() {
    	    Range baseRange = new Range(0, 5);
    	    Range shiftedRange = Range.shift(baseRange, 0);
    	    assertEquals(baseRange, shiftedRange);
    	}

    	
    	@Test
        public void testExpandToInclude_NullRangeWithinBounds() {
            Range result = Range.expandToInclude(null, 5.0);
            assertNotNull(result);
            assertEquals(new Range(5.0, 5.0), result);
        }

        @Test
        public void testExpandToInclude_NullRangeOutOfBounds() {
            Range result = Range.expandToInclude(null, -3.0);
            assertNotNull(result);
            assertEquals(new Range(-3.0, -3.0), result);
        }

        @Test
        public void testExpandToInclude_RangeValueLessThanLowerBound() {
            Range range = new Range(0.0, 10.0);
            Range result = Range.expandToInclude(range, -5.0);
            assertEquals(new Range(-5.0, 10.0), result);
        }

        @Test
        public void testExpandToInclude_RangeValueGreaterThanUpperBound() {
            Range range = new Range(0.0, 10.0);
            Range result = Range.expandToInclude(range, 15.0);
            assertEquals(new Range(0.0, 15.0), result);
        }

        @Test
        public void testExpandToInclude_RangeValueWithinBounds() {
            Range range = new Range(-5.0, 5.0);
            Range result = Range.expandToInclude(range, 0.0);
            assertEquals(new Range(-5.0, 5.0), result);
        }
        
        @Test
        public void testGetCentralValue_PositiveBounds() {
            Range range = new Range(5.0, 15.0);
            assertEquals(10.0, range.getCentralValue(), 0.01);
        }

        @Test
        public void testGetCentralValue_NegativeBounds() {
            Range range = new Range(-15.0, -5.0);
            assertEquals(-10.0, range.getCentralValue(), 0.01);
        }

        @Test
        public void testGetCentralValue_PositiveAndNegativeBounds() {
            Range range = new Range(-10.0, 10.0);
            assertEquals(0.0, range.getCentralValue(), 0.01);
        }
        
        @Test
        public void testGetCentralValueBoundary() {
            Range range = new Range(5.0, 15.0);
            assertEquals(10.0, range.getCentralValue(), 0.01);
        }
        
        @Test
        public void testHashCode_PositiveBounds() {
            Range range = new Range(5.0, 15.0);
            assertEquals(179222630, range.hashCode());
        }

        @Test
        public void testHashCode_NegativeBounds() {
            Range range = new Range(-15.0, -5.0);
            assertEquals(-179222625, range.hashCode());
        }

        @Test
        public void testHashCode_PositiveAndNegativeBounds() {
            Range range = new Range(-10.0, 10.0);
            assertEquals(0, range.hashCode());
        }
        
        @Test
        public void testShift_PositiveBoundsAllowZeroCrossingTrue() {
            Range base = new Range(5.0, 15.0);
            Range result = Range.shift(base, 2.0, true);
            assertEquals(new Range(7.0, 17.0), result);
        }
        
        @Test
        public void testShift_NegativeBoundsAllowZeroCrossingTrue() {
            Range base = new Range(-15.0, -5.0);
            Range result = Range.shift(base, -3.0, true);
            assertEquals(new Range(-18.0, 2.0), result);
        }

        @Test
        public void testShift_PositiveBoundsAllowZeroCrossingFalse() {
            Range base = new Range(5.0, 15.0);
            Range result = Range.shift(base, 10.0, false);
            assertEquals(new Range(15.0, 25.0), result);
        }

        @Test
        public void testShift_NegativeBoundsAllowZeroCrossingFalse() {
            Range base = new Range(-15.0, -5.0);
            Range result = Range.shift(base, -10.0, false);
            assertEquals(new Range(-5.0, 5.0), result);
        }
        
        @Test
        public void testShiftWithZeroCrossing() {
            Range baseRange = new Range(-5.0, 5.0);
            Range shiftedRange = Range.shift(baseRange, 2.0, true);
            assertEquals(new Range(-3.0, 7.0), shiftedRange);
        }
        
        @Test
        public void testShiftWithoutZeroCrossing() {
            Range baseRange = new Range(-5.0, 5.0);
            Range shiftedRange = Range.shift(baseRange, 2.0, false);
            assertEquals(new Range(-3.0, 7.0), shiftedRange);
        }
        
        
        @Test
        public void testToString_PositiveBounds() {
            Range range = new Range(5.0, 15.0);
            assertEquals("Range[5.0,15.0]", range.toString());
        }
        
        @Test
        public void testToString_NegativeBounds() {
            Range range = new Range(-10.0, 10.0);
            assertEquals("Range[-10.0,10.0]", range.toString());
        }

        @Test
        public void testToString_ZeroBounds() {
            Range range = new Range(0.0, 0.0);
            assertEquals("Range[0.0,0.0]", range.toString());
        }

        @Test
        public void testToString_OneBound() {
            Range range = new Range(5.0, 5.0);
            assertEquals("Range[5.0,5.0]", range.toString());
        }

        @Test
        public void testToString_Decimals() {
            Range range = new Range(3.14159, 6.28318);
            assertEquals("Range[3.14159,6.28318]", range.toString());
        }
    	
    	
    	
        @Test
        public void testUpperBoundPositiveNumber() {
            Range range = new Range(-10.0, 20.0);
            // test case: upper bound is a positive number
            assertEquals(20.0, range.getUpperBound(), 0.001);
        }


        @Test
        public void testUpperBoundNegativeNumber() {
            Range range = new Range(-30.0, -5.0);
            // test case: upper bound is a negative number
            assertEquals(-5.0, range.getUpperBound(), 0.001);
        }


        @Test
        public void testUpperBoundZero() {
            Range range = new Range(-5.0, 0.0);
            // test case: upper bound is zero
            assertEquals(0.0, range.getUpperBound(), 0.001);
        }


        @Test
        public void testUpperBoundPositiveInfinity() {
            Range range = new Range(-10.0, Double.POSITIVE_INFINITY);
            // test case: upper bound is positive infinity
            assertEquals(Double.POSITIVE_INFINITY, range.getUpperBound(), 0.001);
        }


        @Test
        public void testUpperBoundNegativeInfinity() {
            Range range = new Range(Double.NEGATIVE_INFINITY, -5.0);
            // test case: upper bound is negative infinity
            assertEquals(-5.0, range.getUpperBound(), 0.001);
        }
        
        @Test
        public void testGetUpperBoundBoundary() {
            Range range = new Range(5.0, 15.0);
            assertEquals(15.0, range.getUpperBound(), 0.001);
        }
        
        
        @Test
        public void testLowerBoundPositiveNumber() {
            Range range = new Range(-10.0, 20.0);
            // test case: lower bound is a positive number
            assertEquals(-10.0, range.getLowerBound(), 0.001);
        }


        @Test
        public void testLowerBoundNegativeNumber() {
            Range range = new Range(-30.0, -5.0);
            // test case: lower bound is a negative number
            assertEquals(-30.0, range.getLowerBound(), 0.001);
        }


        @Test
        public void testLowerBoundZero() {
        	Range range = new Range(0.0, 5.0);
            // test case: lower bound is zero
            assertEquals(0.0, range.getLowerBound(), 0.001);
        }


        @Test
        public void testLowerBoundPositiveInfinity() {
            Range range = new Range(10.0, Double.POSITIVE_INFINITY);
            // test case: lower bound is positive infinity, expected lower bound should be 10.0
            assertEquals(10.0, range.getLowerBound(), 0.001);
        }


        @Test
        public void testLowerBoundNegativeInfinity() {
            Range range = new Range(Double.NEGATIVE_INFINITY, -10.0);
            // test case: lower bound is negative infinity, expected lower bound should be -10.0
            assertEquals(-10.0, range.getLowerBound(), 0.001);
        }
        
        @Test
        public void testGetLowerBoundBoundary() {
            Range range = new Range(5.0, 15.0);
            assertEquals(5.0, range.getLowerBound(), 0.001);
        }        
        
        
        
        @Test
        public void testLengthPositiveNumber() {
            Range range = new Range(10.0, 20.0);
            // test case: length is a positive number
            assertEquals(10.0, range.getLength(), 0.001);
        }


        @Test
        public void testLengthZero() {
            Range range = new Range(10.0, 10.0);
            // test case: length is zero
            assertEquals(0.0, range.getLength(), 0.001);
        }


        @Test
        public void testLengthPositiveInfinity() {
            Range range = new Range(10.0, Double.POSITIVE_INFINITY);
            // test case: length is positive infinity
            assertEquals(Double.POSITIVE_INFINITY, range.getLength(), 0.001);
        }


        @Test
        public void testLengthNegativeInfinity() {
            Range range = new Range(Double.NEGATIVE_INFINITY, 10.0);
            // test case: length is positive infinity
            assertEquals(Double.POSITIVE_INFINITY, range.getLength(), 0.001);
        }
        
        @Test
        public void testGetLengthBoundary() {
            Range range = new Range(5.0, 15.0);
            assertEquals(10.0, range.getLength(), 0.001);
        }

    
	    @Test
	    public void centralValueShouldBeZero() {
	        assertEquals("The central value of -1 and 1 should be 0",
	        0, exampleRange.getCentralValue(), .000000001d);
	    }
	    

	    @Test
	    public void testNull() {
	        assertFalse("Test for null (should return false)", exampleRange1.equals(null));
	    }

	    @Test
	    public void testDifferentObject() {
	        assertFalse("Test for a different object type (should return false)", exampleRange1.equals(new Object()));
	    }

	    @Test
	    public void testDifferentRange() {
	        assertFalse("Test for a different range (should return false)", exampleRange1.equals(new Range(2, 3)));
	    }

	    @Test
	    public void testSameRange() {
	        assertTrue("Test for the same range (should return true)", exampleRange1.equals(new Range(1, 2)));
	    }
	    
	    @Test
	    public void testScaleRange() {
	        Range baseRange = new Range(5.0, 15.0);
	        Range scaledRange = Range.scale(baseRange, 2.0);
	        assertEquals(new Range(10.0, 30.0), scaledRange);
	    }

	    @Test
	    public void testExpand() {
	        Range range = new Range(2, 6);
	        Range expandedRange1 = Range.expand(range, 0.25, 0.5);
	        assertEquals(1, expandedRange1.getLowerBound(), .0000001);
	        assertEquals(8, expandedRange1.getUpperBound(), .0000001);
	    }

	    @Test
	    public void testExpandNullRange() {
	        Range range = null;
	        try {
	            Range.expand(range, 0.25, 0.5);
	            fail("Expected InvalidParameterException");
		     } catch (InvalidParameterException e) {
		         // Expected exception, do nothing
		     } catch (Exception e) {
		         fail("Unexpected exception occurred: " + e.getMessage());
		     }
		 }
	    @Test
	    public void testExpandZeroMargins() {
	        Range range = new Range(2, 6);
	        Range expandedRange1 = Range.expand(range, 0, 0);
	        assertEquals(2, expandedRange1.getLowerBound(), .0000001);
	        assertEquals(6, expandedRange1.getUpperBound(), .0000001);
	    }

	    @Test
	    public void testExpandZeroRange() {
	        Range range = new Range(2, 2);
	        Range expandedRange1 = Range.expand(range, 0.25, 0.5);
	        assertEquals(2, expandedRange1.getLowerBound(), .0000001);
	        assertEquals(2, expandedRange1.getUpperBound(), .0000001);
	    }

    @After
    public void tearDown() throws Exception {
    }


	@AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}
