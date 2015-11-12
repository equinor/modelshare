/**
 */
package com.statoil.modelshare;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Information</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.statoil.modelshare.TaskInformation#getName <em>Name</em>}</li>
 *   <li>{@link com.statoil.modelshare.TaskInformation#getDescription <em>Description</em>}</li>
 *   <li>{@link com.statoil.modelshare.TaskInformation#getFormattedName <em>Formatted Name</em>}</li>
 *   <li>{@link com.statoil.modelshare.TaskInformation#getFormattedDescription <em>Formatted Description</em>}</li>
 * </ul>
 *
 * @see com.statoil.modelshare.ModelsharePackage#getTaskInformation()
 * @model
 * @generated
 */
public interface TaskInformation extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getTaskInformation_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.TaskInformation#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getTaskInformation_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.TaskInformation#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Formatted Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Formatted Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formatted Name</em>' attribute.
	 * @see com.statoil.modelshare.ModelsharePackage#getTaskInformation_FormattedName()
	 * @model transient="true" changeable="false" derived="true"
	 * @generated
	 */
	String getFormattedName();

	/**
	 * Returns the value of the '<em><b>Formatted Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Formatted Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formatted Description</em>' attribute.
	 * @see com.statoil.modelshare.ModelsharePackage#getTaskInformation_FormattedDescription()
	 * @model transient="true" changeable="false" derived="true"
	 * @generated
	 */
	String getFormattedDescription();

} // TaskInformation
