/**
 */
package com.equinor.modelshare;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Access</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.equinor.modelshare.ModelsharePackage#getAccess()
 * @model
 * @generated
 */
public enum Access implements Enumerator {
	/**
	 * The '<em><b>View</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VIEW_VALUE
	 * @generated
	 * @ordered
	 */
	VIEW(1, "View", "+v"),

	/**
	 * The '<em><b>Read</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #READ_VALUE
	 * @generated
	 * @ordered
	 */
	READ(2, "Read", "+r"),

	/**
	 * The '<em><b>Write</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WRITE_VALUE
	 * @generated
	 * @ordered
	 */
	WRITE(4, "Write", "+w"), /**
	 * The '<em><b>No View</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NO_VIEW_VALUE
	 * @generated
	 * @ordered
	 */
	NO_VIEW(3, "NoView", "-v"), /**
	 * The '<em><b>No Read</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NO_READ_VALUE
	 * @generated
	 * @ordered
	 */
	NO_READ(4, "NoRead", "-r"), /**
	 * The '<em><b>No Write</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NO_WRITE_VALUE
	 * @generated
	 * @ordered
	 */
	NO_WRITE(5, "NoWrite", "-w"), /**
	 * The '<em><b>Inherit View</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INHERIT_VIEW_VALUE
	 * @generated
	 * @ordered
	 */
	INHERIT_VIEW(6, "InheritView", "?v"), /**
	 * The '<em><b>Inherit Read</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INHERIT_READ_VALUE
	 * @generated
	 * @ordered
	 */
	INHERIT_READ(7, "InheritRead", "?r"), /**
	 * The '<em><b>Inherit Write</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INHERIT_WRITE_VALUE
	 * @generated
	 * @ordered
	 */
	INHERIT_WRITE(8, "InheritWrite", "?w");

	/**
	 * The '<em><b>View</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>View</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VIEW
	 * @model name="View" literal="+v"
	 * @generated
	 * @ordered
	 */
	public static final int VIEW_VALUE = 1;

	/**
	 * The '<em><b>Read</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Read</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #READ
	 * @model name="Read" literal="+r"
	 * @generated
	 * @ordered
	 */
	public static final int READ_VALUE = 2;

	/**
	 * The '<em><b>Write</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Write</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WRITE
	 * @model name="Write" literal="+w"
	 * @generated
	 * @ordered
	 */
	public static final int WRITE_VALUE = 4;

	/**
	 * The '<em><b>No View</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>No View</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NO_VIEW
	 * @model name="NoView" literal="-v"
	 * @generated
	 * @ordered
	 */
	public static final int NO_VIEW_VALUE = 3;

	/**
	 * The '<em><b>No Read</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>No Read</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NO_READ
	 * @model name="NoRead" literal="-r"
	 * @generated
	 * @ordered
	 */
	public static final int NO_READ_VALUE = 4;

	/**
	 * The '<em><b>No Write</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>No Write</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NO_WRITE
	 * @model name="NoWrite" literal="-w"
	 * @generated
	 * @ordered
	 */
	public static final int NO_WRITE_VALUE = 5;

	/**
	 * The '<em><b>Inherit View</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Inherit View</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INHERIT_VIEW
	 * @model name="InheritView" literal="?v"
	 * @generated
	 * @ordered
	 */
	public static final int INHERIT_VIEW_VALUE = 6;

	/**
	 * The '<em><b>Inherit Read</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Inherit Read</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INHERIT_READ
	 * @model name="InheritRead" literal="?r"
	 * @generated
	 * @ordered
	 */
	public static final int INHERIT_READ_VALUE = 7;

	/**
	 * The '<em><b>Inherit Write</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Inherit Write</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INHERIT_WRITE
	 * @model name="InheritWrite" literal="?w"
	 * @generated
	 * @ordered
	 */
	public static final int INHERIT_WRITE_VALUE = 8;

	/**
	 * An array of all the '<em><b>Access</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final Access[] VALUES_ARRAY =
		new Access[] {
			VIEW,
			READ,
			WRITE,
			NO_VIEW,
			NO_READ,
			NO_WRITE,
			INHERIT_VIEW,
			INHERIT_READ,
			INHERIT_WRITE,
		};

	/**
	 * A public read-only list of all the '<em><b>Access</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<Access> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Access</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Access get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Access result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Access</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Access getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Access result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Access</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Access get(int value) {
		switch (value) {
			case VIEW_VALUE: return VIEW;
			case READ_VALUE: return READ;
			case WRITE_VALUE: return WRITE;
			case NO_VIEW_VALUE: return NO_VIEW;
			case NO_WRITE_VALUE: return NO_WRITE;
			case INHERIT_VIEW_VALUE: return INHERIT_VIEW;
			case INHERIT_READ_VALUE: return INHERIT_READ;
			case INHERIT_WRITE_VALUE: return INHERIT_WRITE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private Access(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //Access
