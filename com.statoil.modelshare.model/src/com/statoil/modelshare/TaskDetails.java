/**
 */
package com.statoil.modelshare;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Details</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.statoil.modelshare.TaskDetails#getIdentifier <em>Identifier</em>}</li>
 * </ul>
 *
 * @see com.statoil.modelshare.ModelsharePackage#getTaskDetails()
 * @model
 * @generated
 */
public interface TaskDetails extends AbstractTask {
	/**
	 * Returns the value of the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifier</em>' attribute.
	 * @see #setIdentifier(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getTaskDetails_Identifier()
	 * @model
	 * @generated
	 */
	String getIdentifier();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.TaskDetails#getIdentifier <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identifier</em>' attribute.
	 * @see #getIdentifier()
	 * @generated
	 */
	void setIdentifier(String value);

} // TaskDetails
