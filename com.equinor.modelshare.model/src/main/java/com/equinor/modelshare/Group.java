/**
 */
package com.equinor.modelshare;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.equinor.modelshare.Group#getExternalIdentifier <em>External Identifier</em>}</li>
 * </ul>
 *
 * @see com.equinor.modelshare.ModelsharePackage#getGroup()
 * @model
 * @generated
 */
public interface Group extends Account {

	/**
	 * Returns the value of the '<em><b>External Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>External Identifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Identifier</em>' attribute.
	 * @see #setExternalIdentifier(String)
	 * @see com.equinor.modelshare.ModelsharePackage#getGroup_ExternalIdentifier()
	 * @model
	 * @generated
	 */
	String getExternalIdentifier();

	/**
	 * Sets the value of the '{@link com.equinor.modelshare.Group#getExternalIdentifier <em>External Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External Identifier</em>' attribute.
	 * @see #getExternalIdentifier()
	 * @generated
	 */
	void setExternalIdentifier(String value);

} // Group
