/**
 */
package com.statoil.modelshare;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Client</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.statoil.modelshare.Client#getOrganisation <em>Organisation</em>}</li>
 *   <li>{@link com.statoil.modelshare.Client#getEmail <em>Email</em>}</li>
 *   <li>{@link com.statoil.modelshare.Client#getPassword <em>Password</em>}</li>
 *   <li>{@link com.statoil.modelshare.Client#isForceChangePassword <em>Force Change Password</em>}</li>
 *   <li>{@link com.statoil.modelshare.Client#getLocalUser <em>Local User</em>}</li>
 * </ul>
 *
 * @see com.statoil.modelshare.ModelsharePackage#getClient()
 * @model
 * @generated
 */
public interface Client extends Account {
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
	 * @see com.statoil.modelshare.ModelsharePackage#getClient_Organisation()
	 * @model
	 * @generated
	 */
	String getOrganisation();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Client#getOrganisation <em>Organisation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Organisation</em>' attribute.
	 * @see #getOrganisation()
	 * @generated
	 */
	void setOrganisation(String value);

	/**
	 * Returns the value of the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Email</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Email</em>' attribute.
	 * @see #setEmail(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getClient_Email()
	 * @model
	 * @generated
	 */
	String getEmail();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Client#getEmail <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email</em>' attribute.
	 * @see #getEmail()
	 * @generated
	 */
	void setEmail(String value);

	/**
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getClient_Password()
	 * @model
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Client#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

	/**
	 * Returns the value of the '<em><b>Force Change Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Force Change Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Force Change Password</em>' attribute.
	 * @see #setForceChangePassword(boolean)
	 * @see com.statoil.modelshare.ModelsharePackage#getClient_ForceChangePassword()
	 * @model
	 * @generated
	 */
	boolean isForceChangePassword();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Client#isForceChangePassword <em>Force Change Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Force Change Password</em>' attribute.
	 * @see #isForceChangePassword()
	 * @generated
	 */
	void setForceChangePassword(boolean value);

	/**
	 * Returns the value of the '<em><b>Local User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local User</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local User</em>' attribute.
	 * @see #setLocalUser(String)
	 * @see com.statoil.modelshare.ModelsharePackage#getClient_LocalUser()
	 * @model
	 * @generated
	 */
	String getLocalUser();

	/**
	 * Sets the value of the '{@link com.statoil.modelshare.Client#getLocalUser <em>Local User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local User</em>' attribute.
	 * @see #getLocalUser()
	 * @generated
	 */
	void setLocalUser(String value);

} // Client
