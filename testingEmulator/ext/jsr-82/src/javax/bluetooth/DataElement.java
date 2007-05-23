/*
 *  DataElement.java
 *
 *  $Revision$ $Date$
 *
 *  (c) Copyright 2001, 2002 Motorola, Inc.  ALL RIGHTS RESERVED.
 */



package javax.bluetooth;

import java.util.Enumeration;
import java.util.Vector;



/**
 * The <code>DataElement</code> class defines the various data
 * types that a Bluetooth service attribute value may have.
 *
 * The following table describes the data types and valid
 * values that a <code>DataElement</code> object can store.
 *
 * <TABLE BORDER>
 * <TR><TH>Data Type</TH><TH>Valid Values</TH></TR>
 * <TR><TD><code>NULL</code></TD><TD>represents a
 * <code>null</code> value
 * </TD></TR> <TR><TD><code>U_INT_1</code></TD><TD><code>
 * long </code> value range [0, 255]</TD></TR>
 * <TR><TD><code>U_INT_2</code></TD><TD><code>long</code>
 * value range [0, 2<sup>16</sup>-1]</TD></TR>
 * <TR><TD><code>U_INT_4</code></TD>
 * <TD><code>long</code> value range [0, 2<sup>32</sup>-1]</TD></TR>
 * <TR><TD><code>U_INT_8</code></TD>
 * <TD><code>byte[]</code> value range [0, 2<sup>64</sup>-1]</TD></TR>
 * <TR><TD><code>U_INT_16</code></TD>
 * <TD><code>byte[]</code> value range [0, 2<sup>128</sup>-1]</TD></TR>
 * <TR><TD><code>INT_1</code></TD><TD><code>long</code>
 * value range [-128, 127]</TD></TR>
 * <TR><TD><code>INT_2</code></TD><TD><code>long</code>
 * value range [-2<sup>15</sup>, 2<sup>15</sup>-1]</TD></TR>
 * <TR><TD><code>INT_4</code></TD><TD><code>long</code>
 * value range [-2<sup>31</sup>, 2<sup>31</sup>-1]</TD></TR>
 * <TR><TD><code>INT_8</code></TD><TD><code>long</code>
 * value range [-2<sup>63</sup>, 2<sup>63</sup>-1]</TD></TR>
 * <TR><TD><code>INT_16</code></TD><TD><code>byte[]</code>
 * value range [-2<sup>127</sup>, 2<sup>127</sup>-1]</TD></TR>
 * <TR><TD><code>URL</code></TD>
 * <TD><code>java.lang.String</code></TD></TR>
 * <TR><TD><code>UUID</code></TD>
 * <TD><code>javax.bluetooth.UUID</code></TD></TR>
 * <TR><TD><code>BOOL</code></TD><TD><code>boolean</code></TD></TR>
 * <TR><TD><code>STRING</code></TD>
 * <TD><code>java.lang.String</code></TD></TR>
 * <TR><TD><code>DATSEQ</code></TD>
 * <TD><code>java.util.Enumeration</code></TD></TR>
 * <TR><TD><code>DATALT</code></TD>
 * <TD><code>java.util.Enumeration</code></TD></TR>
 * </TABLE>
 *
 * @version 1.0 February 11, 2002
 *
 */


public class DataElement {




    /*
     * The following section defines public, static and instance
     * member variables used in the implementation of the methods.
     */



    /**
     * Defines data of type NULL.
     *
     * The value for data type <code>DataElement.NULL</code> is
     * implicit, i.e., there is no representation of it.
     * Accordingly there is no method to retrieve
     * it, and attempts to retrieve the value will throw an exception.
     * <P>
     * The value of <code>NULL</code> is 0x00 (0).
     *
     */
     public static final int NULL =  0x0000;




    /**
     * Defines an unsigned integer of size one byte.
     * <P>
     * The value of the constant <code>U_INT_1</code>
     * is 0x08 (8).
     */
     public static final int U_INT_1 = 0x0008;




    /**
     * Defines an unsigned integer of size two bytes.
     * <P>
     * The value of the constant <code>U_INT_2</code> is 0x09 (9).
     */
     public static final int U_INT_2 = 0x0009;




    /**
     * Defines an unsigned integer of size four bytes.
     * <P>
     * The value of the constant <code>U_INT_4</code> is 0x0A (10).
     */
     public static final int U_INT_4 = 0x000A;




    /**
     * Defines an unsigned integer of size eight bytes.
     * <P>
     * The value of the constant <code>U_INT_8</code> is 0x0B (11).
     */
     public static final int U_INT_8 = 0x000B;




    /**
     * Defines an unsigned integer of size sixteen bytes.
     * <P>
     * The value of the constant <code>U_INT_16</code> is 0x0C (12).
     */
     public static final int U_INT_16 = 0x000C;




    /**
     * Defines a signed integer of size one byte.
     * <P>
     * The value of the constant <code>INT_1</code> is 0x10 (16).
     */
     public static final int INT_1 = 0x0010;




    /**
     * Defines a signed integer of size two bytes.
     * <P>
     * The value of the constant <code>INT_2</code> is 0x11 (17).
     */
     public static final int INT_2 = 0x0011;




    /**
     * Defines a signed integer of size four bytes.
     * <P>
     * The value of the constant <code>INT_4</code> is 0x12 (18).
     */
     public static final int INT_4 = 0x0012;



    /**
     * Defines a signed integer of size eight bytes.
     * <P>
     * The value of the constant <code>INT_8</code> is 0x13 (19).
     */
     public static final int INT_8 = 0x0013;




    /**
     * Defines a signed integer of size sixteen bytes.
     * <P>
     * The value of the constant <code>INT_16</code> is 0x14 (20).
     */
     public static final int INT_16 = 0x0014;




    /**
     * Defines data of type URL.
     * <P>
     * The value of the constant <code>URL</code> is 0x40 (64).
     */
     public static final int URL = 0x0040;




    /**
     * Defines data of type UUID.
     * <P>
     * The value of the constant <code>UUID</code> is 0x18 (24).
     */
     public static final int UUID = 0x0018;




    /**
     * Defines data of type BOOL.
     * <P>
     * The value of the constant <code>BOOL</code> is 0x28 (40).
     */
     public static final int BOOL = 0x0028;




    /**
     * Defines data of type STRING.
     * <P>
     * The value of the constant <code>STRING</code> is 0x20 (32).
     */
     public static final int STRING = 0x0020;





    /**
     * Defines data of type DATSEQ.  The service attribute value whose
     * data has this type must consider all the elements of the list,
     * i.e. the value is the whole set and not a subset. The elements
     * of the set can be of any type defined in this class, including
     * DATSEQ.
     * <P>
     * The value of the constant <code>DATSEQ</code> is 0x30 (48).
     */
     public static final int DATSEQ = 0x0030;




    /**
     * Defines data of type DATALT.  The service attribute value whose
     * data has this type must consider only one of the elements of the
     * set, i.e., the value is the not the whole set but only one
     * element of the set. The user is free to choose any one element.
     * The elements of the set can be of any type defined in this class,
     * including DATALT.
     * <P>
     * The value of the constant <code>DATALT</code> is 0x38 (56).
     */
     public static final int DATALT = 0x0038;

     
     
     
     
     /**
     * An instance di Object that is used to store the inner data when the DataElement is not of type DATSEQ or DATALT.
     * When this field is used _innerVector must be null. 
     */
    private Object _innerObject=null;

    /**
     * DataElement of type DATSEQ or DATALT contains an Enumeration of DataElement that is stored into a vector.
     * In this implementation when this field is used _innerObject must be null.
     * */
    private Vector<DataElement> _innerVector=null;
    
    /**
     * Inner local memebers to store data type.
     * The Object start with the NULL configuration.
     */
    private int _valueType=0;

    /*
     * The following section defines public, static, and instance
     * member methods used in the class. It also defines the
     * constructors.
     */





     /**
     * Creates a <code>DataElement</code> of type <code>NULL</code>,
     * <code>DATALT</code>, or <code>DATSEQ</code>.
     *
     * @see #NULL
     * @see #DATALT
     * @see #DATSEQ
     *
     * @param  valueType the type of DataElement to create:
     * <code>NULL</code>, <code>DATALT</code>, or <code>DATSEQ</code>
     *
     * @exception IllegalArgumentException if <code>valueType</code>
     * is not <code>NULL</code>, <code>DATALT</code>, or
     * <code>DATSEQ</code>
     */
     public DataElement(int valueType) {
    	 switch(valueType)
    	 {
    	 	case DataElement.NULL:
    	 	{
    	 		this._valueType=valueType;
    	 	}
    	 	case DataElement.DATALT:
    	 	case DataElement.DATSEQ: 
    	 	{
    	 		this._valueType=valueType;
    	 		this._innerVector=new Vector<DataElement>();
    	 		break;
    	 	}
    	 	default:
    	 	{
    	 		throw new java.lang.IllegalArgumentException("ValueType must be: NULL, DATALT, or DATSEQ");
    	 	}
    	 }
    	 
     }   /*  End of the constructor  */






    /**
     * Creates a <code>DataElement</code> whose data type is
     * <code>BOOL</code> and whose value is equal to <code>bool</code>
     *
     * @see #BOOL
     *
     * @param bool the value of the <code>DataElement</code> of type
     * BOOL.
     */
     public DataElement(boolean bool) {
    	 this._valueType=DataElement.BOOL;
    	 this._innerObject=new Boolean(bool);
     }   /*  End of the constructor  */






    /**
     * Creates a <code>DataElement</code> that encapsulates an integer
     * value of size <code>U_INT_1</code>, <code>U_INT_2</code>,
     * <code>U_INT_4</code>, <code>INT_1</code>, <code>INT_2</code>,
     * <code>INT_4</code>, and <code>INT_8</code>.
     * The legal values for the <code>valueType</code> and the corresponding
     * attribute values are:
     * <TABLE>
     * <TR><TH>Value Type</TH><TH>Value Range</TH></TR>
     * <TR><TD><code>U_INT_1</code></TD>
     *     <TD>[0, 2<sup>8</sup>-1]</TD></TR>
     * <TR><TD><code>U_INT_2</code></TD>
     *     <TD>[0, 2<sup>16</sup>-1]</TD></TR>
     * <TR><TD><code>U_INT_4</code></TD>
     *     <TD>[0, 2<sup>32</sup>-1]</TD></TR>
     * <TR><TD><code>INT_1</code></TD>
     *     <TD>[-2<sup>7</sup>, 2<sup>7</sup>-1]</TD></TR>
     * <TR><TD><code>INT_2</code></TD>
     *     <TD>[-2<sup>15</sup>, 2<sup>15</sup>-1]</TD></TR>
     * <TR><TD><code>INT_4</code></TD>
     *     <TD>[-2<sup>31</sup>, 2<sup>31</sup>-1]</TD></TR>
     * <TR><TD><code>INT_8</code></TD>
     *     <TD>[-2<sup>63</sup>, 2<sup>63</sup>-1]</TD></TR>
     * </TABLE>
     * All other pairings are illegal and will cause an
     * <code>IllegalArgumentException</code> to be thrown.
     *
     * @see #U_INT_1
     * @see #U_INT_2
     * @see #U_INT_4
     * @see #INT_1
     * @see #INT_2
     * @see #INT_4
     * @see #INT_8
     *
     * @param valueType the data type of the object that is being
     * created; must be one of the following:
     * <code>U_INT_1</code>,
     * <code>U_INT_2</code>,
     * <code>U_INT_4</code>,
     * <code>INT_1</code>,
     * <code>INT_2</code>,
     * <code>INT_4</code>, or
     * <code>INT_8</code>
     *
     * @param value the value of the object being created; must be
     * in the range specified for the given <code>valueType</code>
     *
     * @exception IllegalArgumentException if the <code>valueType</code>
     * is not valid or the <code>value</code> for the given legal
     * <code>valueType</code> is outside the valid range
     *
     */
     public DataElement(int valueType, long value) {
    	 switch(valueType)
    	 {
    	 	case DataElement.U_INT_1:
    	 	{
    	 		int min=0;
    	 		int max=2^8-1;
    	 		if(value<min||value>max)
    	 		{
    	 			throw new java.lang.IllegalArgumentException("ValueType "+valueType+" needs to be between "+min+" and "+max+".");
    	 		}
    	 		break;
    	 	}
    	 	case DataElement.U_INT_2:
    	 	{
    	 		int min=0;
    	 		int max=2^16-1;
    	 		if(value<min||value>max)
    	 		{
    	 			throw new java.lang.IllegalArgumentException("ValueType "+valueType+" needs to be between "+min+" and "+max+".");
    	 		}
    	 		break;
    	 	}
    	 	case DataElement.U_INT_4:
    	 	{
    	 		int min=0;
    	 		int max=2^32-1;
    	 		if(value<min||value>max)
    	 		{
    	 			throw new java.lang.IllegalArgumentException("ValueType "+valueType+" needs to be between "+min+" and "+max+".");
    	 		}
    	 		break;
    	 	}	
    	 	case DataElement.INT_1:
    	 	{
    	 		int min=-2^7;
    	 		int max=2^7-1;
    	 		if(value<min||value>max)
    	 		{
    	 			throw new java.lang.IllegalArgumentException("ValueType "+valueType+" needs to be between "+min+" and "+max+".");
    	 		}
    	 		break;
    	 	}
    	 	case DataElement.INT_2:
    	 	{
    	 		int min=-2^15;
    	 		int max=2^15-1;
    	 		if(value<min||value>max)
    	 		{
    	 			throw new java.lang.IllegalArgumentException("ValueType "+valueType+" needs to be between "+min+" and "+max+".");
    	 		}
    	 		break;
    	 	}
    	 	case DataElement.INT_4:
    	 	{
    	 		int min=-2^31;
    	 		int max=2^31-1;
    	 		if(value<min||value>max)
    	 		{
    	 			throw new java.lang.IllegalArgumentException("ValueType "+valueType+" needs to be between "+min+" and "+max+".");
    	 		}
    	 		break;
    	 	}
    	 	case DataElement.INT_8:
    	 	{
    	 		int min=-2^63;
    	 		int max=2^63-1;
    	 		if(value<min||value>max)
    	 		{
    	 			throw new java.lang.IllegalArgumentException("ValueType "+valueType+" needs to be between "+min+" and "+max+".");
    	 		}
    	 		break;
    	 	}
    	 	default:
    	 	{
    	 		throw new java.lang.IllegalArgumentException("ValueType must be: U_INT_1, U_INT_2, U_INT_4, INT_1, INT_2, INT_4, and INT_8");
    	 	}
    	 }
    	 this._valueType=valueType;
    	 this._innerObject=new Long(value);
     }   /*  End of the constructor  */





    /**
     * Creates a <code>DataElement</code> whose data type is given by
     * <code>valueType</code> and whose value is specified by the argument
     * <code>value</code>. The legal values for the <code>valueType</code>
     * and the corresponding attribute values are:
     * <TABLE>
     * <TR><TH>Value Type</TH><TH>Java Type / Value Range</TH></TR>
     * <TR><TD><code>URL</code></TD><TD><code>java.lang.String</code>
     * </TD></TR>
     * <TR><TD><code>UUID</code></TD>
     *     <TD><code>javax.bluetooth.UUID</code></TD></TR>
     * <TR><TD><code>STRING</code></TD>
     *     <TD><code>java.lang.String</code></TD></TR>
     * <TR><TD><code>INT_16</code></TD>
     *     <TD>[-2<sup>127</sup>, 2<sup>127</sup>-1] as a byte array
     * whose length must be 16</TD></TR>
     * <TR><TD><code>U_INT_8</code></TD>
     *     <TD>[0, 2<sup>64</sup>-1] as a byte array whose length must
     * be 8</TD></TR>
     * <TR><TD><code>U_INT_16</code></TD>
     *     <TD>[0, 2<sup>128</sup>-1] as a byte array whose length must
     * be 16</TD></TR>
     * </TABLE>
     * All other pairings are illegal and would cause an
     * <code>IllegalArgumentException</code> exception.
     *
     * @see #URL
     * @see #UUID
     * @see #STRING
     * @see #U_INT_8
     * @see #INT_16
     * @see #U_INT_16
     *
     * @param valueType the data type of the object that is being
     * created; must be one of the following: <code>URL</code>,
     * <code>UUID</code>,
     * <code>STRING</code>,
     * <code>INT_16</code>,
     * <code>U_INT_8</code>, or
     * <code>U_INT_16</code>
     *
     * @param value the value for the <code>DataElement</code> being created
     * of type <code>valueType</code>
     *
     * @exception IllegalArgumentException if the <code>value</code>
     * is not of the <code>valueType</code> type or is not in the range
     * specified or is <code>null</code>
     *
     */
     public DataElement(int valueType, Object value) {
    	 switch(valueType)
    	 {
    	 	case DataElement.UUID:
    	 	{
    	 		if((value instanceof UUID)==false)
    	 		{
    	 			throw new java.lang.IllegalArgumentException("if ValueType is UUID then value must be an instance of UUID");
    	 		}
    	 		break;
    	 	}
    	 	case DataElement.STRING:
    	 	{
    	 		if((value instanceof String)==false)
    	 		{
    	 			throw new java.lang.IllegalArgumentException("if ValueType is STRING then value must be an instance of String");
    	 		}
    	 		break;
    	 	}
    	 	case DataElement.INT_16: 
    	 	{
    	 		if((value instanceof byte[])==false)
    	 		{
    	 			byte[] b=(byte[])value;
    	 			if(b.length!=16)
    	 			{
    	 				throw new java.lang.IllegalArgumentException("if ValueTypei s INT_16 then value must be an instance of byte[] with length equals to 16.");
    	 			}
    	 		}
    	 		break;
    	 	}
    	 	case DataElement.U_INT_8: 
    	 	{
    	 		if((value instanceof byte[])==false)
    	 		{
    	 			byte[] b=(byte[])value;
    	 			if(b.length!=8)
    	 			{
    	 				throw new java.lang.IllegalArgumentException("if ValueType is U_INT_8 then value must be an instance of byte[] with length equals to 8.");
    	 			}
    	 		}
    	 		break;
    	 	}
    	 	case DataElement.U_INT_16: 
    	 	{
    	 		if((value instanceof byte[])==false)
    	 		{
    	 			byte[] b=(byte[])value;
    	 			if(b.length!=16)
    	 			{
    	 				throw new java.lang.IllegalArgumentException("if ValueType is U_INT_16 then value must be an instance of byte[] with length equals to 16.");
    	 			}
    	 		}
    	 		break;
    	 	}
    	 	default:
    	 	{
    	 		throw new java.lang.IllegalArgumentException("ValueType must be: URL, UUID, STRING, INT_16, U_INT_8, or U_INT_16");
    	 	}
    	 }
    	 this._innerObject=value;
     }   /*  End of the constructor  */





    /**
     * Adds a <code>DataElement</code> to this <code>DATALT</code>
     * or <code>DATSEQ</code> <code>DataElement</code> object.
     * The <code>elem</code> will be added at the end of the list.
     * The <code>elem</code> can be of any
     * <code>DataElement</code> type, i.e., <code>URL</code>,
     * <code>NULL</code>, <code>BOOL</code>, <code>UUID</code>,
     * <code>STRING</code>, <code>DATSEQ</code>, <code>DATALT</code>,
     * and the various signed and unsigned integer types.
     * The same object may be added twice. If the object is
     * successfully added the size of the <code>DataElement</code> is
     * increased by one.
     *
     * @param elem the <code>DataElement</code> object to add
     *
     * @exception ClassCastException if the method is invoked on a
     * <code>DataElement</code> whose type is not <code>DATALT</code>
     * or <code>DATSEQ</code>
     *
     * @exception NullPointerException if <code>elem</code> is
     * <code>null</code>
     *
     */
     public void addElement(DataElement elem) {
    	 if(elem==null)
    	 {
    		 throw new java.lang.NullPointerException("Param \"elem\" cannot be null");
    	 }
    	 if(this._valueType!=DataElement.DATSEQ||this._valueType!=DataElement.DATALT)
    	 {
    		 this._innerVector.addElement(elem);
    	 }else
    	 {
    		 throw new java.lang.ClassCastException("ValueType must be: DATSEQ, DATALT"); 
    	 }
    	 
     }   /*  End of the method addElement    */






    /**
     * Inserts a <code>DataElement</code> at the specified location.
     * This method can be invoked only on a <code>DATALT</code> or
     * <code>DATSEQ</code> <code>DataElement</code>.
     * <code>elem</code> can be of any <code>DataElement</code>
     * type, i.e., <code>URL</code>,  <code>NULL</code>,
     * <code>BOOL</code>,
     * <code>UUID</code>, <code>STRING</code>, <code>DATSEQ</code>,
     * <code>DATALT</code>, and the various signed and unsigned
     * integers. The same object may be added twice. If the object is
     * successfully added the size will be increased by one.
     * Each element with an index greater than or equal to the specified
     * index is shifted upward to have an index one
     * greater than the value it had previously.
     * <P>
     * The <code>index</code> must be greater than or equal to 0 and
     * less than or equal to the current size.  Therefore,
     * <code>DATALT</code> and
     * <code>DATSEQ</code> are zero-based objects.
     *
     * @param elem the <code>DataElement</code> object to add
     *
     * @param index the location at which to add the
     * <code>DataElement</code>
     *
     * @throws ClassCastException if the method is invoked on an
     * instance of <code>DataElement</code> whose type is not
     * <code>DATALT</code> or <code>DATSEQ</code>
     *
     * @throws IndexOutOfBoundsException if <code>index</code>
     * is negative or greater than
     * the size of the <code>DATALT</code> or <code>DATSEQ</code>
     *
     * @throws NullPointerException if <code>elem</code> is
     * <code>null</code>
     *
     */
    public void insertElementAt(DataElement elem, int index) {
    	if(elem==null)
		{
    		throw new java.lang.NullPointerException("Param \"elem\" cannot be null");
		}
		if(this._valueType!=DataElement.DATSEQ||this._valueType!=DataElement.DATALT)
		{
			this._innerVector.insertElementAt(elem, index);
		}else
		{
			throw new java.lang.ClassCastException("ValueType must be: DATSEQ, DATALT"); 
		}
     }   /*  End of the method insertElementAt   */




    /**
     * Returns the number of <code>DataElements</code> that are present
     * in this <code>DATALT</code> or <code>DATSEQ</code> object.
     * It is possible that the number of elements is equal to zero.
     *
     * @return the number of elements in this <code>DATALT</code>
     * or <code>DATSEQ</code>
     *
     * @throws ClassCastException if this object is not of type
     * <code>DATALT</code> or <code>DATSEQ</code>
     */
     public int getSize() {

		if(this._valueType!=DataElement.DATSEQ||this._valueType!=DataElement.DATALT)
		{
			return this._innerVector.size();
		}else
		{
			throw new java.lang.ClassCastException("ValueType must be: DATSEQ, DATALT"); 
		}
     }   /*  End of the method getSize   */







    /**
     * Removes the first occurrence of the <code>DataElement</code>
     * from this object.  <code>elem</code> may be of any type, i.e.,
     * <code>URL</code>,  <code>NULL</code>, <code>BOOL</code>,
     * <code>UUID</code>,  <code>STRING</code>, <code>DATSEQ</code>,
     * <code>DATALT</code>, or the variously sized signed and unsigned
     * integers.
     * Only the first object in the list that is equal to
     * <code>elem</code> will be removed. Other objects, if present,
     * are not removed.  Since this class doesn�t override the
     * <code>equals()</code> method of the <code>Object</code> class,
     * the remove method compares only the
     * references of objects. If <code>elem</code> is
     * successfully removed the size of this <code>DataElement</code>
     * is decreased by one.  Each <code>DataElement</code> in the
     * <code>DATALT</code> or <code>DATSEQ</code> with an index greater
     * than the index of <code>elem</code> is shifted downward to have
     * an index one smaller than the value it had previously.
     *
     * @param elem the <code>DataElement</code> to be removed
     *
     * @return <code>true</code> if the input value was found and
     * removed; else <code>false</code>
     *
     * @throws ClassCastException if this object is not of
     * type <code>DATALT</code> or <code>DATSEQ</code>
     *
     * @throws NullPointerException if <code>elem</code> is
     * <code>null</code>
     */
     public boolean removeElement(DataElement elem) {
     	if(elem==null)
		{
    		throw new java.lang.NullPointerException("Param \"elem\" cannot be null");
		}
		if(this._valueType!=DataElement.DATSEQ||this._valueType!=DataElement.DATALT)
		{
			return this._innerVector.remove(elem);
		}else
		{
			throw new java.lang.ClassCastException("ValueType must be: DATSEQ, DATALT"); 
		}
     }   /*  End of the method removeElement */





    /**
     * Returns the data type of the object this <code>DataElement</code>
     * represents.
     *
     * @return the data type of this <code>DataElement<code> object; the legal
     * return values are:
     *        <code>URL</code>,
     *        <code>NULL</code>,
     *        <code>BOOL</code>,
     *        <code>UUID</code>,
     *        <code>STRING</code>,
     *        <code>DATSEQ</code>,
     *        <code>DATALT</code>,
     *        <code>U_INT_1</code>,
     *        <code>U_INT_2</code>,
     *        <code>U_INT_4</code>,
     *        <code>U_INT_8</code>,
     *        <code>U_INT_16</code>,
     *        <code>INT_1</code>,
     *        <code>INT_2</code>,
     *        <code>INT_4</code>,
     *        <code>INT_8</code>, or
     *        <code>INT_16</code>
     *
     */
     public int getDataType() {
    	 return this._valueType;
     }   /*  End of the method getDataType   */






    /**
     * Returns the value of the <code>DataElement</code> if it can be
     * represented as a <code>long</code>. The data type of the object must be
     * <code>U_INT_1</code>,
     * <code>U_INT_2</code>,
     * <code>U_INT_4</code>,
     * <code>INT_1</code>,
     * <code>INT_2</code>,
     * <code>INT_4</code>, or
     * <code>INT_8</code>.
     *
     *
     * @return the value of the <code>DataElement</code> as a <code>long</code>
     *
     * @throws ClassCastException if the data type of the object is not
     * <code>U_INT_1</code>,
     * <code>U_INT_2</code>,
     * <code>U_INT_4</code>, <code>INT_1</code>,
     * <code>INT_2</code>, <code>INT_4</code>,
     * or <code>INT_8</code>
     */
     public long getLong() {
    	 switch(this._valueType)
    	 {
    	 	case DataElement.U_INT_1:
    	 	case DataElement.U_INT_2:
    	 	case DataElement.U_INT_4:
    	 	case DataElement.INT_1:
    	 	case DataElement.INT_2:
    	 	case DataElement.INT_4:
    	 	case DataElement.INT_8:
    	 	{
    	 		return ((Long)this._innerObject).longValue();
    	 	}
    	 	default:
    	 	{
    	 		throw new java.lang.IllegalArgumentException("ValueType must be: URL, UUID, STRING, INT_16, U_INT_8, or U_INT_16");
    	 	}
    	 }
     }   /*  End of the method getLong   */






    /**
     * Returns the value of the <code>DataElement</code> if it is represented as
     * a <code>boolean</code>.
     *
     *
     * @return the <code>boolean</code> value of this <code>DataElement</code>
     * object
     *
     * @throws ClassCastException if the data type of this object is
     * not of type <code>BOOL</code>
     */
     public boolean getBoolean() {
    	 switch(this._valueType)
    	 {
    	 	case DataElement.BOOL:
    	 	{
    	 		return ((Boolean)this._innerObject).booleanValue();
    	 	}
    	 	default:
    	 	{
    	 		throw new java.lang.IllegalArgumentException("ValueType must be: URL, UUID, STRING, INT_16, U_INT_8, or U_INT_16");
    	 	}
    	 }
     }   /*  End of the method getBoolean    */






    /**
     * Returns the value of this <code>DataElement</code> as an
     * <code>Object</code>. This method returns the appropriate Java
     * object for the following data types:
     * <code>URL</code>,
     * <code>UUID</code>,
     * <code>STRING</code>,
     * <code>DATSEQ</code>,
     * <code>DATALT</code>,
     * <code>U_INT_8</code>,
     * <code>U_INT_16</code>, and
     * <code>INT_16</code>.
     * Modifying the returned <code>Object</code> will not change this
     * <code>DataElement</code>.
     *
     * The following are the legal pairs of data type
     * and Java object type being returned.
     * <TABLE>
     * <TR><TH><code>DataElement</code> Data Type</code></TH>
     *    <TH>Java Data Type</TH></TR>
     * <TR><TD><code>URL</code></TD><TD><code>java.lang.String</code>
     * </TD></TR>
     * <TR><TD><code>UUID</code></TD>
     * <TD><code>javax.bluetooth.UUID</code></TD></TR>
     * <TR><TD><code>STRING</code></TD><TD><code>java.lang.String
     * </code></TD></TR>
     * <TR><TD><code>DATSEQ</code></TD>
     * <TD><code>java.util.Enumeration</code></TD></TR>
     * <TR><TD><code>DATALT</code></TD>
     * <TD><code>java.util.Enumeration</code></TD></TR>
     * <TR><TD><code>U_INT_8</code></TD>
     * <TD>byte[] of length 8</TD></TR>
     * <TR><TD><code>U_INT_16</code></TD>
     * <TD>byte[] of length 16</TD></TR>
     * <TR><TD><code>INT_16</code></TD>
     * <TD>byte[] of length 16</TD></TR>
     * </TABLE>
     *
     * @return the value of this object
     *
     * @throws ClassCastException if the object is not a
     * <code>URL</code>, <code>UUID</code>,
     * <code>STRING</code>, <code>DATSEQ</code>, <code>DATALT</code>,
     * <code>U_INT_8</code>,
     * <code>U_INT_16</code>,
     * or <code>INT_16</code>
     *
     */
     public Object getValue() {
    	 switch(this._valueType)
    	 {
    	 	case DataElement.DATALT:
    	 	case DataElement.DATSEQ:
    	 	{
    	 		return ((Vector<DataElement>)this._innerVector.clone()).elements();
    	 	}
    	 	case DataElement.UUID:
    	 	case DataElement.STRING:
    	 	case DataElement.INT_16: 
    	 	case DataElement.U_INT_8: 
    	 	case DataElement.U_INT_16: 
    	 	{
    	 		return this._innerObject;
    	 	}
    	 	default:
    	 	{
    	 		throw new java.lang.IllegalArgumentException("ValueType must be: DATSEQ, DATALT, URL, UUID, STRING, INT_16, U_INT_8, or U_INT_16");
    	 	}
    	 }
     }   /*  End of the method getValue  */



}   /*  End of the class DataElement    */
