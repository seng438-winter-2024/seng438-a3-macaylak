package org.jfree.data;

import static org.junit.Assert.*;

import org.junit.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jmock.Expectations;
import org.jmock.Mockery;


public class DataUtilitiesTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}

	@Before
	public void setUp() throws Exception {}

	// ------------- createNumberArray2D(double[][] data) Tests -----------

    /**
	 * This test simulates creating a null double array and passing it to the createNumberArray2D function,
      asserting that this operation will result in an exception.
	 */
	@Test
	public void createNumber2DNullArray() {
		try {
			double[][] arrayToPass = null;
			DataUtilities.createNumberArray2D(arrayToPass);
			fail("This method should throw an exception!");
		} catch (Exception e) {
			assertEquals("Exception: IllegalArgumentException", IllegalArgumentException.class,
					e.getClass());
		}
	}

	/**
	 * This test simulates creating a 2D array of doubles, including one row with two positive elements and 
     one empty row, and passing it to a createNumberArray2D function. It verifies that a 2-row array with one empty row is correctly created.
	 */
	@Test
	public void createNumber2DEmptyArrayOne() {
		Number[][] expectedArray = { { 32.0, 33.0 }, {} };
		double[][] arrayToPass = { { 32.0, 33.0 }, {} };
		Number[][] actualArray = DataUtilities.createNumberArray2D(arrayToPass);
		assertArrayEquals("Error (createNumberArray): the correct array values are.", expectedArray, actualArray);
	}

	/**
	 * This test simulates creating a 2D array of doubles with two empty rows and passing it to the createNumberArray2D
      function, verifying that an expected empty 2D array of doubles is created.
	 */
	@Test
	public void createNumber2DEmptyArrayEmpty() {
		Number[][] expectedArray = { {}, {} };
		double[][] arrayToPass = { {}, {} };
		Number[][] actualArray = DataUtilities.createNumberArray2D(arrayToPass);
		assertArrayEquals("Error (createNumberArray): not an empty 2D array { {}, {} }; .", expectedArray,
				actualArray);
	}

    /**
	 * This test simulates creating a 2D array of doubles and verifies that a 2x1 array of 
     negative doubles is accurately produced when passed to the createNumberArray2D function.
	 */
	@Test
	public void createNumberRegular2DArrayOfNegativeValues() {
		Number[][] expectedArray = { { -22.0 }, { -23.0 } };
		double[][] arrayToPass = { { -22.0 }, { -23.0 } };
		Number[][] actualArray = DataUtilities.createNumberArray2D(arrayToPass);
		assertArrayEquals("Error (createNumberArray): the correct array values are.", expectedArray, actualArray);
	}

	/**
	 * This test simulates creating a 2D array of doubles and checks that a 2x1 array of 
     positive doubles is correctly generated when passed to the createNumberArray2D function.
	 */
	@Test
	public void createNumberRegular2DArrayOfOne() {
		Number[][] expectedArray = { { 21.0 }, { 21.0 } };
		double[][] arrayToPass = { { 21.0 }, { 21.0 } };
		Number[][] actualArray = DataUtilities.createNumberArray2D(arrayToPass);
		assertArrayEquals("Error (createNumberArray): the correct array values are.", expectedArray, actualArray);
	}

    /**
	 * This test simulates the creation of a 2D array of doubles and verifies that a 5x2 array 
     of positive doubles is correctly created when passed to a createNumberArray2D function.
	 */
	@Test
	public void createNumberRegular2DArrayOfFive() {
		Number[][] expectedArray = { { 24.0, 12.0, 7.0, 4.0, 5.0 }, { 24.0, 12.0, 7.0, 4.0, 5.0 } };
		double[][] arrayToPass = { { 24.0, 12.0, 7.0, 4.0, 5.0 }, { 24.0, 12.0, 7.0, 4.0, 5.0 } };
		Number[][] actualArray = DataUtilities.createNumberArray2D(arrayToPass);
		assertArrayEquals("Error (createNumberArray): the correct array values are.", expectedArray, actualArray);
	}

	/**
	 * This test simulates creating a 2D array of doubles and checks that a 2x1 array of large positive 
     doubles is correctly generated when passed to the createNumberArray2D function.
	 */
	@Test
	public void createNumberRegular2DArrayOfHugeValues() {
		Number[][] expectedArray = { { 555e8 }, { 556e8 } };
		double[][] arrayToPass = { { 555e8 }, { 556e8 } };
		Number[][] actualArray = DataUtilities.createNumberArray2D(arrayToPass);
		assertArrayEquals("Error (createNumberArray): the correct array values are.", expectedArray, actualArray);
	}


	// ------------- **** calculateRowTotal(Values2D data, int row) Tests*****

	/**
	 * This test simulates the creation of an empty Values2D table, which is then passed to the 
     calculateRowTotal() function with a row number of 0, expecting the total values in the row to equal 0.
	 */
	@Test
	public void calculateRowTotalEmptyChart() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(0));
				one(values).getRowCount();
				will(returnValue(0));
			}
		});

		int rowNumber = 0; 
		double result = DataUtilities.calculateRowTotal(values, rowNumber);
		assertEquals("The row total = 0", 0, result, .000000001d);
	}

	/**
	 * This test simulates passing a null object to the calculateRowTotal() function with a 
     row number of 0, expecting that an IllegalArgumentException will be thrown.
	 */
	@Test
	public void calculateRowTotalNull() {
		try {
			DataUtilities.calculateRowTotal(null, 0);
			fail("should throw an exception!");
		} catch (Exception e) {
			assertEquals("IllegalArgumentException", IllegalArgumentException.class,
					e.getClass());
		}
	}

	/**
	 * This test simulates creating a Values2D table filled with positive values, which is then 
     passed to calculateRowTotal() with a row number of 1, expecting the sum of the values in that row to be accurate.
	 */
	@Test
	public void calculateRowTotalPositive() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(4));
				one(values).getValue(1, 0);
				will(returnValue(1.0));
				one(values).getValue(1, 1);
				will(returnValue(2.0));
				one(values).getValue(1, 2);
				will(returnValue(3.0));
				one(values).getValue(1, 3);
				will(returnValue(4.0));
			}
		});
		int rowNumber = 1;
		double result = DataUtilities.calculateRowTotal(values, rowNumber);
		assertEquals("The row total = 10", 10, result, .000000001d);
	}

	/**
	 * This test simulates creating a Values2D table with negative values, which is then passed to calculateRowTotal() with a row 
     number of 1, expecting the total of the values in the row to be accurately calculated.
	 */
	@Test
	public void calculateRowTotalNegative() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(5));
				one(values).getValue(1, 0);
				will(returnValue(-1.0));
				one(values).getValue(1, 1);
				will(returnValue(-2.0));
				one(values).getValue(1, 2);
				will(returnValue(-3.0));
				one(values).getValue(1, 3);
				will(returnValue(-4.0));
				one(values).getValue(1, 4);
				will(returnValue(-5.0));
			}
		});
		int rowNumber = 1;
		double result = DataUtilities.calculateRowTotal(values, rowNumber);
		assertEquals("The row total = -15", -15, result, .000000001d);
	}
	
	

	/**
	 * This test simulates creating a Values2D table with a negative index, with the e
     xpectation that this action will result in an exception being thrown.
	*/
	@Test
	public void calculateRowTotalNegativeIndex() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(2));
				one(values).getValue(-1, 0);
				will(returnValue(-1.0));
				one(values).getValue(-1, 1);
				will(returnValue(-2.0));
			}
		});

		int rowNumber = -1;
	     try {
	         // Attempting to calculate the total of an out-of-bounds column index
	         double result = DataUtilities.calculateRowTotal(values, rowNumber);
	         // If the column index is out of bounds, the method should return 0
	         assertEquals("Sum of a column with an out of bounds index is", 0, result, .000000001d);
	     } catch (Error error) {
	         // Handle the case where an error occurs
	         fail("An error occurred: " + error.getMessage());
	     } catch (Exception e) {
	         // Handle the case where an exception occurs
	         fail("Exception occurred: " + e.getMessage());
	     }
	}
	
	
	
    @Test
    public void testCalculateRowTotal_nullColumn_2arguments() {
        Mockery mockingContext = new Mockery();
        final Values2D values = mockingContext.mock(Values2D.class);

        mockingContext.checking(new Expectations() {
            {
                one(values).getColumnCount();
                will(returnValue(-1));
                one(values).getRowCount();
                will(returnValue(1));
                

                one(values).getValue(0, 0);
                will(returnValue(40)); // Assuming value for column 0 is 40

                one(values).getValue(0, 1);
                will(returnValue(30)); // Assuming value for column 1 is 30

                one(values).getValue(0, 2);
                will(returnValue(35)); // Assuming value for column 2 is 35
            }
        });

        // Verify that the correct value is calculated
        
        try {
	         // Attempting to calculate the total of an out-of-bounds column index
	         double result = DataUtilities.calculateRowTotal(values, 0);
	         // If the column index is out of bounds, the method should return 0
	         assertEquals(0, result, 0.0001); 
	     } catch (Error error) {
	         // Handle the case where an error occurs
	         fail("An error occurred: " + error.getMessage());
	     } catch (Exception e) {
	         // Handle the case where an exception occurs
	         fail("Exception occurred: " + e.getMessage());
	     }
        
    }

	// ------------- createNumberArray(double[] data) Tests ----------------------

	/**
	 * This test simulates creating a 1D array of doubles with three positive elements and passing it to a 
     createNumberArray function, verifying that a positive 3-element double array is correctly generated.
	 */
	@Test
	public void createNumberArrayPositiveDouble() {
		Number[] expectedArray = { 1.7, 2.2, 3.4 };
		double[] arrayToPass = { 1.7, 2.2, 3.4 };
		Number[] actualArray = DataUtilities.createNumberArray(arrayToPass);
		assertArrayEquals("expected array = {1.7, 2.2, 3.4}", expectedArray,
				actualArray);
	}

	/**
	 * This test simulates creating a 1D array of doubles with three negative elements and passing it to a 
     createNumberArray function, verifying that a negative 3-element double array is correctly created.
	 */
	@Test
	public void createNumberArrayNegativeDouble() {
		Number[] expectedArray = { -1.7, -2.2, -3.4 };
		double[] arrayToPass = { -1.7, -2.2, -3.4 };
		Number[] actualArray = DataUtilities.createNumberArray(arrayToPass);
		assertArrayEquals("expected array = {-1.7, -2.2, -3.4}", expectedArray,
				actualArray);
	}

	/**
	 * This test simulates creating a null double array and passing it to the createNumberArray function, 
     asserting that this operation will result in an exception being thrown.
	 */
	@Test
	public void createNumberArrayNull() {
		try {
			double[] arrayToPass = null;
			DataUtilities.createNumberArray(arrayToPass);
			fail("should throw an exception!");
		} catch (Exception e) {
			assertEquals("IllegalArgumentException", IllegalArgumentException.class,
					e.getClass());
		}
	}

	/**
	 * This test simulates creating a 1D array of doubles with a single element valued at 0.0 and 
     passing it to a createNumberArray function, verifying that an expected single-element array of 
     0.0 is correctly generated.
	 */
	@Test
	public void createNumberArrayZero() {
		Number[] expectedArray = { 0.0 };
		double[] arrayToPass = { 0.0 };
		Number[] actualArray = DataUtilities.createNumberArray(arrayToPass);
		assertArrayEquals("expected array = {0.0}", expectedArray, actualArray);
	}

	/**
	 * This test simulates creating an empty 1D array of doubles and passing it to the createNumberArray 
     function, verifying that the expected empty double array is correctly created.
	 */
	@Test
	public void createNumberArrayEmpty() {
		Number[] expectedArray = {};
		double[] arrayToPass = {};
		Number[] actualArray = DataUtilities.createNumberArray(arrayToPass);
		assertArrayEquals("expected array = {}", expectedArray, actualArray);
	}

	/**
	 * This test simulates creating a 1D array of doubles with both a positive and a negative element, and passing 
     it to the createNumberArray function. It verifies that the expected array, matching the input array, is 
     correctly created as a Number type.
	 */
	@Test
	public void createNumberArrayNegativePositive() {
		Number[] expectedArray = { 1.2, -1.2 };
		double[] arrayToPass = { 1.2, -1.2 };
		Number[] actualArray = DataUtilities.createNumberArray(arrayToPass);
		assertArrayEquals("expected array = {1.2, -1.2};", expectedArray,
				actualArray);
	}

	/**
	 * This test simulates creating a 1D array of doubles with four elements, each set to the maximum double 
     value, and passing it to the createNumberArray function. It verifies that the expected array of maximum 
     positive doubles is correctly created.
	 */
	@Test
	public void createNumberArrayMaxDoubleFour() {
		Number[] expectedArray = { Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE };
		double[] arrayToPass = { Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE };
		Number[] actualArray = DataUtilities.createNumberArray(arrayToPass);
		assertArrayEquals("expected array = 4 elements of the max double value;", expectedArray,
				actualArray);
	}

	/**
	 * This test simulates creating a 1D array of doubles with four elements, each set to the minimum double 
     value, and passing it to the createNumberArray function. It verifies that the expected array of minimum 
     negative doubles is correctly created.
	 */
	@Test
	public void createNumberArrayMinDoubleFour() {
		Number[] expectedArray = { Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE };
		double[] arrayToPass = { Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE };
		Number[] actualArray = DataUtilities.createNumberArray(arrayToPass);
		assertArrayEquals("The expected array should contain 4 elements of the min double value;", expectedArray,
				actualArray);
	}
	

 

	// ------------- calculateColumnTotal(Values2D data, int column) Tests ---------------------

	/**
	 * This test simulates creating an empty Values2D table, which is then passed 
     to calculateColumnTotal() with a column number of 0, expecting the total 
     values in the column to equal 0.
	 */
	@Test
	public void calculateColumnTotalEmptyChart() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(0));
			}
		});
		int columnumber = 0;
		double result = DataUtilities.calculateColumnTotal(values, columnumber);
		assertEquals("The column total is adding up to 0", 0, result, .000000001d);
	}

	/**
	 * This test will simulate passing a null object to calculateColumnTotal() with
	 * a column number of 0 and expects that an IllegalArgumentException is thrown.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void calculateColumnTotalNull() {
		double result = DataUtilities.calculateColumnTotal(null, 0);
		assertEquals("IllegalArgumentException", 0.0, result, .000000001d);
	}

	/**
	 * This test will simulate passing a negative number for the column. The
	 * negative number is passed to calculateColumnTotalNegativeColumnNumber() with
	 * a Values2D table and expects that an IllegalArgumentException is thrown.
	 */
	@Test
	public void calculateColumnTotalNegativeColumnNumber() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(2));
				one(values).getValue(0, -1);
				will(returnValue(5.0));
				one(values).getValue(1, -1);
				will(returnValue(10.0));
			}
		});

		int columnNumber = -1;
		try {
			DataUtilities.calculateColumnTotal(values, columnNumber);
			fail("should throw an exception!");
		} catch (Exception e) {
			assertEquals("IllegalArgumentException", IllegalArgumentException.class,
					e.getClass());
		}
	}

	/**
	 * This test will simulate creating a Values2D table with positive values. The
	 * table is passed to calculateColumnTotal() with a column number of 1 and
	 * expects that the total of the values in the column is correct.
	 */
	@Test
	public void calculateColumnTotalPositive() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(3));
				one(values).getValue(0, 1);
				will(returnValue(0.1));
				one(values).getValue(1, 1);
				will(returnValue(0.23));
				one(values).getValue(2, 1);
				will(returnValue(0.009));
			}
		});
		int columnNumber = 1;
		double result = DataUtilities.calculateColumnTotal(values, columnNumber);
		assertEquals("column total = 0.339", 0.339, result, .000000001d);
	}

	/**
	 * This test will simulate creating a Values2D table with negative values. The
	 * table is passed to calculateColumnTotal() with a column number of 1 and
	 * expects that the total of the values in the column is correct.
	 */
	@Test
	public void calculateColumnTotalNegative() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(2));
				one(values).getValue(0, 1);
				will(returnValue(-45.8));
				one(values).getValue(1, 1);
				will(returnValue(-5569.9));
			}
		});
		int columnNumber = 1;
		double result = DataUtilities.calculateColumnTotal(values, columnNumber);
		assertEquals("row total = -5615.7", -5615.7, result, .000000001d);
	}


	// ------------- getCumulativePercentages(KeyedValues data) Tests -----------

	/**
	 *This test involves creating a mock object of the KeyedValues type, complete with its keys and values. 
     It then passes this mock object to the getCumulativePercentage function, expecting that the percentages of 
     the values within the keys will be calculated accurately and precisely.
	 */
	@Test
	public void cumulativePercentageForIndexZeroSixteen() {
		Mockery mockingContext = new Mockery();
		final KeyedValues data = mockingContext.mock(KeyedValues.class); 

		mockingContext.checking(new Expectations() {  
			{
				atLeast(1).of(data).getItemCount(); 
				will(returnValue(2)); 
				atLeast(1).of(data).getValue(0); 
				will(returnValue(1.0)); 
				atLeast(1).of(data).getValue(1); 
				will(returnValue(5.0)); 
				atLeast(1).of(data).getKey(0); 
				will(returnValue(25.0)); 
				atLeast(1).of(data).getKey(1); 
				will(returnValue(26.0)); 
			}
		});
		
		KeyedValues result = DataUtilities.getCumulativePercentages(data); 
		assertEquals("value at index[0] = 0.166666667", 0.166666667, result.getValue(0).doubleValue(),
				.000000001d);
	}

	/**
	 *
    This test creates a mock object of the KeyedValues type, including its keys and associated values. 
    This object is then passed to the getCumulativePercentage function, expecting accurate and precise percentage 
    calculations of the values within the keys. Additionally, the test checks if the cumulative percentage sums up to 100 at an 
    index other than 0, ensuring overall percentage accuracy.
	 */
	@Test
	public void cumulativePercentageForIndexOneHundred() {
		Mockery mockingContext = new Mockery();
		final KeyedValues data = mockingContext.mock(KeyedValues.class);

		mockingContext.checking(new Expectations() {
			{
				atLeast(1).of(data).getItemCount(); 
				will(returnValue(2));
				atLeast(1).of(data).getValue(0); 
				will(returnValue(1.0)); 
				atLeast(1).of(data).getValue(1); 
				will(returnValue(5.0)); 
				atLeast(1).of(data).getKey(0);
				will(returnValue(19.0));
				atLeast(1).of(data).getKey(1); 
				will(returnValue(26.0)); 
			}
		});
		KeyedValues result = DataUtilities.getCumulativePercentages(data);
		assertEquals("value at index[0] = 1.0", 1.0, result.getValue(1).doubleValue(), .000000001d);
	}

	/**
	 *This test will create a local object of the type KeyedValues which will be assigned null and then passing it in the method
	 * getCumulativePercentages to test whether an exception is being thrown or not as part of the test creation. 
	 */
	@Test
	public void cumulativePercentageDataNullCheck() {
		try {
			final KeyedValues dataToPass = null;
			DataUtilities.getCumulativePercentages(dataToPass);
			fail("should throw an exception!");
		} catch (Exception e) {
			assertEquals("IllegalArgumentException", IllegalArgumentException.class,
					e.getClass());
		}
	}

	/**
	 *This test will create a mock object of the type KeyedValues with it's key and the values of the it. 
	 *Later then this will be passed into getCumulativePercentage for these values which are inside the keys, 
	 *and it expects the percentage will be calculated properly and with accuracy as well this test also covers 
	 *a test that at one of the index of values it is equal to null and if the percentage method performs the same functionality. 
	 */

	
	@Test
	public void cumulativePercentageForIndexZeroSixteenPercentNullIncluded() {
		Mockery mockingContext = new Mockery(); 
		final KeyedValues data = mockingContext.mock(KeyedValues.class);

		mockingContext.checking(new Expectations() {
			{
				atLeast(1).of(data).getItemCount();
				will(returnValue(3));
				atLeast(1).of(data).getValue(0);
				will(returnValue(1.0));
				atLeast(1).of(data).getValue(1);
				will(returnValue(5.0)); 
				atLeast(1).of(data).getValue(2); 
				will(returnValue(null));
				atLeast(1).of(data).getKey(0); 
				will(returnValue(25.0)); 
				atLeast(1).of(data).getKey(1);
				will(returnValue(26.0)); 
				atLeast(1).of(data).getKey(2); 
				will(returnValue(27.0));

			}
		});
		KeyedValues result = DataUtilities.getCumulativePercentages(data); 
		assertEquals("value at index[0] = 0.166666667", 0.166666667, result.getValue(0).doubleValue(),
				.000000001d);  
	}

	// ------------- calculateRowTotal(Values2D data, int row, int[] validCols) Tests -------------

	/**
	 * This test will simulate passing a null object to calculateRowTotal() with a
	 * row number of 0 and expects that an IllegalArgumentException is thrown.
	 */
	@Test
	public void calculateRowTotalNullChecking() { 
		try {
			final Values2D valueToPass = null; 
			final int rowNumberToPass = 0; 
			final int[] validColumnsToPass = { 0 }; 
			DataUtilities.calculateRowTotal(valueToPass, rowNumberToPass, validColumnsToPass);
			fail("should throw an exception!");

		} catch (Exception e) {
			assertEquals("IllegalArgumentException", IllegalArgumentException.class,
					e.getClass());
		}
	}

	/**
	 * This test will simulate passing a null object to calculateRowTotal() with a
	 * row number of 1 and is expected to calculate the total properly with the valid columns as the new parameter
	 * in this test which is also 1. Result adds up to 10 (1 + 2 + 3 + 4 = 10) which is correct expected output
	 * This test also includes null as one of the values in the row. 
	 */
	@Test
	public void calculateRowTotalWithNull() { 
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(4));
				one(values).getValue(1, 0);
				will(returnValue(1.0));
				one(values).getValue(1, 1);
				will(returnValue(2.0));
				one(values).getValue(1, 2);
				will(returnValue(3.0));
				one(values).getValue(1, 3);
				will(returnValue(4.0));
			}
		});
		int rowNumber = 1; 
		final int[] validColumnsToPass = { 1 }; 
		double result = DataUtilities.calculateRowTotal(values, rowNumber, validColumnsToPass);
		assertEquals("row total = 10", 10, result, .000000001d);

	}
	
    @Test
    public void testCalculateRowTotal_nullColumn() {
        Mockery mockingContext = new Mockery();
        final Values2D values = mockingContext.mock(Values2D.class);

        mockingContext.checking(new Expectations() {
            {
                one(values).getColumnCount();
                will(returnValue(-1));

                one(values).getValue(0, 0);
                will(returnValue(40)); // Assuming value for column 0 is 40

                one(values).getValue(0, 1);
                will(returnValue(30)); // Assuming value for column 1 is 30

                one(values).getValue(0, 2);
                will(returnValue(35)); // Assuming value for column 2 is 35
            }
        });

        int[] validCols = {0, 1, 2}; // Assuming all columns are valid

        double total = DataUtilities.calculateRowTotal(values, 0, validCols);

        // Verify that the correct value is calculated
        assertEquals(0, total, 0.0001); // Expected row total is 105 (40 + 30 + 35)
    }
    
    
    


	// ------------- calculateColumnTotal(Values2D data, int column, int[] validRows) -------------
	/**

	 * This test will simulate passing a null object to calculateColumnTotal() with a
	 * row number of 0 and expects that an IllegalArgumentException is thrown.
	 */
	@Test
	public void calculateColumnTotalNullChecking() {
		try {
			final Values2D valueToPass = null; 
			final int columnNumberToPass = 0; 
			final int[] validRowsToPass = { 0 }; 
			DataUtilities.calculateColumnTotal(valueToPass, columnNumberToPass, validRowsToPass);
			fail("should throw an exception!");
		} catch (Exception e) {
			assertEquals("IllegalArgumentException", IllegalArgumentException.class,
					e.getClass());
		}
	}

	/**
	 * This test will simulate passing a null object to calculateColumnTotal() with a
	 * column number of 1 and is expected to calculate the total properly with the valid rows as the new parameter
	 * in this test which is also 1. Result adds up to 10 (1 + 2 + 3 + 4 = 10) which is correct expected output.
	 * This test also includes null as one of the values in the column. 
	 */
	@Test
	public void calculateColumnTotalWithNull() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(4));
				one(values).getValue(0, 1);
				will(returnValue(1.0));
				one(values).getValue(1, 1);
				will(returnValue(2.0));
				one(values).getValue(2, 1);
				will(returnValue(3.0));
				one(values).getValue(3, 1);
				will(returnValue(4.0));
			}
		});
		int columnNumber = 1; 
		final int[] validRowsToPass = { 1 }; 
		double result = DataUtilities.calculateColumnTotal(values, columnNumber, validRowsToPass);
		assertEquals("column total = 10", 10, result, .000000001d);
	}

	// --------- calculateColumnTotal(Values2D data, int column) ---------
	/**
	 * This test will be testing the calculateColumnTotal function when a value in
	 * the data table is null.
	 */
	@Test
	public void calculateColumnTotalNullData() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(2));
				one(values).getValue(0, 1);
				will(returnValue(-25.98));
				one(values).getValue(1, 1);
				will(returnValue(null));
			}
		});
		int columnNumber = 1; 
		double result = DataUtilities.calculateColumnTotal(values, columnNumber);
		assertEquals("column total = -25.98", -25.98, result, .000000001d);
	}

	// ------------- calculateRowTotal(Values2D data, int row) Test -----------
	
    /**
	 * This test will be testing the calculateRowTotal function when a value in the
	 * data table is null.
	 */
	@Test
	public void calculateRowTotalNullData() {
		Mockery mockingContext = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);

		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(2));
				one(values).getValue(1, 0);
				will(returnValue(null));
				one(values).getValue(1, 1);
				will(returnValue(3.5));
			}
		});
		int rowNumber = 1; 
		double result = DataUtilities.calculateRowTotal(values, rowNumber);
		assertEquals("The row total is adding up to 3.5", 3.5, result, .000000001d);
	}

	// ------------- clone(double[][] source) Tests -----------
	
    /**
	 * This test will be testing the clone function when the 2D array is null.
	 */
	@Test
	public void cloneNullParameter() {
		try {
			double[][] array = null; 
			DataUtilities.clone(array); 
			fail("should throw an exception!"); 
		} catch (Exception e) {
			assertEquals("IllegalArgumentException", IllegalArgumentException.class,
					e.getClass());
		}
	}

	/**
	 * This test will be testing the clone function when the 2D array is empty.
	 */
	@Test
	public void cloneEmptyArray() {
		double[][] array = { {} }; 
		double[][] resultArray = DataUtilities.clone(array); 
		assertArrayEquals("Error (createNumberArray): not an empty 2D array { {} }; .", array, resultArray);
	}

	/**
	 * This test will be testing the clone function when the 2D array contains a
	 * null value.
	 */
	@Test
	public void cloneNullDataInArray() {
		double[][] array = { { 2, 4, 5 }, null, { 19, 345 } };
		double[][] resultArray = DataUtilities.clone(array); 
		assertArrayEquals("Error (createNumberArray): correct array values are {{ {2, 4, 5}, {19, 345} }; .", array,
				resultArray); 
	}

	/**
	 * This test will be testing the clone function when the 2D array contains big
	 * positive values.
	 */
	@Test
	public void cloneArrayOfBigAndPositiveValues() {
		double[][] array = { { 2425.0, 1285.0, 7697.0, 4845.0, 59415.0 }, { 24665.0, 1255.0, 798.0, 4232.0, 5000.0 } };
		double[][] actualArray = DataUtilities.clone(array); 
		assertArrayEquals(
				"Error (createNumberArray): correct array values are { { 2425.0, 1285.0, 7697.0, 4845.0, 59415.0 }, { 24665.0, 1255.0, 798.0, 4232.0, 5000.0 } }; .",
				array, actualArray); 
	}

	/**
	 * This test will be testing the clone function when the 2D array contains small
	 * negative values.
	 */
	@Test
	public void cloneArrayOfSmallAndNegativeValues() {
		double[][] array = { { -0.025 }, { -0.000560 } }; 
		double[][] actualArray = DataUtilities.clone(array); 
		assertArrayEquals("Error (createNumberArray): correct array values are { { -0.025 }, { -000056.0 } }; .",
				array, actualArray);
	}
	
	
	  @Test
	   public void testEqualArrays() {
	        double[][] a = {{1.0, 2.0}, {3.0, 4.0}};
	        double[][] b = {{1.0, 2.0}, {3.0, 4.0}};

	        assertTrue(DataUtilities.equal(a, b));
	    }

	    @Test
	   public void testUnequalArrays() {
	        double[][] a = {{1.0, 2.0}, {3.0, 4.0}};
	        double[][] b = {{1.0, 2.0}, {3.0, 5.0}};

	        assertFalse(DataUtilities.equal(a, b));
	    }

	    @Test
	   public void testNullArrays() {
	        double[][] a = null;
	        double[][] b = null;

	        assertTrue(DataUtilities.equal(a, b));
	    }

	    @Test
	   public void testOneNullArray() {
	        double[][] a = {{1.0, 2.0}, {3.0, 4.0}};
	        double[][] b = null;

	        assertFalse(DataUtilities.equal(a, b));
	    }

	    @Test
	   public void testDifferentLengthArrays() {
	        double[][] a = {{1.0, 2.0}, {3.0, 4.0}};
	        double[][] b = {{1.0, 2.0}};

	        assertFalse(DataUtilities.equal(a, b));
	    }
	    
	    @Test
	   public void testNullArraysFalse() {
	        double[][] a = null;
	        double[][] b = {{1.0, 2.0}};
	        assertFalse(DataUtilities.equal(a, b));
	    }


	@After
	public void tearDown() throws Exception {
		
	}
	
	
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}
	
	
	
}