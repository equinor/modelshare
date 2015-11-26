/**
 */
package com.statoil.modelshare;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.statoil.modelshare.Model#getOwner <em>Owner</em>}</li>
 *   <li>{@link com.statoil.modelshare.Model#getOrganisation <em>Organisation</em>}</li>
 *   <li>{@link com.statoil.modelshare.Model#getMail <em>Mail</em>}</li>
 *   <li>{@link com.statoil.modelshare.Model#getLastUpdated <em>Last Updated</em>}</li>
 *   <li>{@link com.statoil.modelshare.Model#isStask <em>Stask</em>}</li>
 *   <li>{@link com.statoil.modelshare.Model#getTaskDetails <em>Task Details</em>}</li>
 *   <li>{@link com.statoil.modelshare.Model#getUsage <em>Usage</em>}</li>
 *   <li>{@link com.statoil.modelshare.Model#getTaskFolders <em>Task Folders</em>}</li>
 *   <li>{@link com.statoil.modelshare.Model#getSimaVersion <em>Sima Version</em>}</li>
 * </ul>
 *
 * @see com.statoil.modelshare.ModelsharePackage#getModel()
 * @model extendedMetaData="name='Model'"
 * @generated
 */
public interface Model extends Asset {

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' attribute.
	 * @see #setOwner(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getModel_Owner()
	 * @model
	 * @generated
	 */
	String getOwner();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Model#getOwner <em>Owner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' attribute.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(String value);

	/**
	 * Returns the value of the '<em><b>Organisation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organisation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organisation</em>' attribute.
	 * @see #setOrganisation(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getModel_Organisation()
	 * @model
	 * @generated
	 */
	String getOrganisation();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Model#getOrganisation <em>Organisation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Organisation</em>' attribute.
	 * @see #getOrganisation()
	 * @generated
	 */
	void setOrganisation(String value);

	/**
	 * Returns the value of the '<em><b>Mail</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mail</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mail</em>' attribute.
	 * @see #setMail(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getModel_Mail()
	 * @model
	 * @generated
	 */
	String getMail();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Model#getMail <em>Mail</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mail</em>' attribute.
	 * @see #getMail()
	 * @generated
	 */
	void setMail(String value);

	/**
	 * Returns the value of the '<em><b>Last Updated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Updated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Updated</em>' attribute.
	 * @see #setLastUpdated(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getModel_LastUpdated()
	 * @model
	 * @generated
	 */
	String getLastUpdated();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Model#getLastUpdated <em>Last Updated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Updated</em>' attribute.
	 * @see #getLastUpdated()
	 * @generated
	 */
	void setLastUpdated(String value);

	/**
	 * Returns the value of the '<em><b>Stask</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stask</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stask</em>' attribute.
	 * @see #setStask(boolean)
	 * @see com.statoil.modelshare.ModelsharePackage#getModel_Stask()
	 * @model default="false"
	 * @generated
	 */
	boolean isStask();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Model#isStask <em>Stask</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stask</em>' attribute.
	 * @see #isStask()
	 * @generated
	 */
	void setStask(boolean value);

	/**
	 * Returns the value of the '<em><b>Task Details</b></em>' containment reference list.
	 * The list contents are of type {@link com.statoil.modelshare.TaskDetails}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Task Details</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task Details</em>' containment reference list.
	 * @see com.statoil.modelshare.ModelsharePackage#getModel_TaskDetails()
	 * @model containment="true"
	 * @generated
	 */
	EList<TaskDetails> getTaskDetails();

	/**
	 * Returns the value of the '<em><b>Usage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Usage</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Usage</em>' attribute.
	 * @see #setUsage(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getModel_Usage()
	 * @model
	 * @generated
	 */
	String getUsage();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Model#getUsage <em>Usage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Usage</em>' attribute.
	 * @see #getUsage()
	 * @generated
	 */
	void setUsage(String value);

	/**
	 * Returns the value of the '<em><b>Task Folders</b></em>' containment reference list.
	 * The list contents are of type {@link com.statoil.modelshare.TaskFolder}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Task Folders</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task Folders</em>' containment reference list.
	 * @see com.statoil.modelshare.ModelsharePackage#getModel_TaskFolders()
	 * @model containment="true"
	 * @generated
	 */
	EList<TaskFolder> getTaskFolders();

	/**
	 * Returns the value of the '<em><b>Sima Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sima Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sima Version</em>' attribute.
	 * @see #setSimaVersion(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getModel_SimaVersion()
	 * @model
	 * @generated
	 */
	String getSimaVersion();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Model#getSimaVersion <em>Sima Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sima Version</em>' attribute.
	 * @see #getSimaVersion()
	 * @generated
	 */
	void setSimaVersion(String value);
} // Model
