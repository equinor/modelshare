/**
 */
package com.statoil.modelshare.impl;

import com.statoil.modelshare.ModelsharePackage;
import com.statoil.modelshare.User;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.statoil.modelshare.impl.UserImpl#getOrganisation <em>Organisation</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.UserImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.UserImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.UserImpl#isForceChangePassword <em>Force Change Password</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UserImpl extends AccountImpl implements User {
	/**
	 * The default value of the '{@link #getOrganisation() <em>Organisation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganisation()
	 * @generated
	 * @ordered
	 */
	protected static final String ORGANISATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOrganisation() <em>Organisation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganisation()
	 * @generated
	 * @ordered
	 */
	protected String organisation = ORGANISATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEmail() <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmail()
	 * @generated
	 * @ordered
	 */
	protected String email = EMAIL_EDEFAULT;

	/**
	 * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected String password = PASSWORD_EDEFAULT;

	/**
	 * The default value of the '{@link #isForceChangePassword() <em>Force Change Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForceChangePassword()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FORCE_CHANGE_PASSWORD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isForceChangePassword() <em>Force Change Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForceChangePassword()
	 * @generated
	 * @ordered
	 */
	protected boolean forceChangePassword = FORCE_CHANGE_PASSWORD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UserImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelsharePackage.Literals.USER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOrganisation() {
		return organisation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrganisation(String newOrganisation) {
		String oldOrganisation = organisation;
		organisation = newOrganisation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.USER__ORGANISATION, oldOrganisation, organisation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmail(String newEmail) {
		String oldEmail = email;
		email = newEmail;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.USER__EMAIL, oldEmail, email));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPassword(String newPassword) {
		String oldPassword = password;
		password = newPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.USER__PASSWORD, oldPassword, password));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isForceChangePassword() {
		return forceChangePassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForceChangePassword(boolean newForceChangePassword) {
		boolean oldForceChangePassword = forceChangePassword;
		forceChangePassword = newForceChangePassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.USER__FORCE_CHANGE_PASSWORD, oldForceChangePassword, forceChangePassword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelsharePackage.USER__ORGANISATION:
				return getOrganisation();
			case ModelsharePackage.USER__EMAIL:
				return getEmail();
			case ModelsharePackage.USER__PASSWORD:
				return getPassword();
			case ModelsharePackage.USER__FORCE_CHANGE_PASSWORD:
				return isForceChangePassword();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelsharePackage.USER__ORGANISATION:
				setOrganisation((String)newValue);
				return;
			case ModelsharePackage.USER__EMAIL:
				setEmail((String)newValue);
				return;
			case ModelsharePackage.USER__PASSWORD:
				setPassword((String)newValue);
				return;
			case ModelsharePackage.USER__FORCE_CHANGE_PASSWORD:
				setForceChangePassword((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelsharePackage.USER__ORGANISATION:
				setOrganisation(ORGANISATION_EDEFAULT);
				return;
			case ModelsharePackage.USER__EMAIL:
				setEmail(EMAIL_EDEFAULT);
				return;
			case ModelsharePackage.USER__PASSWORD:
				setPassword(PASSWORD_EDEFAULT);
				return;
			case ModelsharePackage.USER__FORCE_CHANGE_PASSWORD:
				setForceChangePassword(FORCE_CHANGE_PASSWORD_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelsharePackage.USER__ORGANISATION:
				return ORGANISATION_EDEFAULT == null ? organisation != null : !ORGANISATION_EDEFAULT.equals(organisation);
			case ModelsharePackage.USER__EMAIL:
				return EMAIL_EDEFAULT == null ? email != null : !EMAIL_EDEFAULT.equals(email);
			case ModelsharePackage.USER__PASSWORD:
				return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
			case ModelsharePackage.USER__FORCE_CHANGE_PASSWORD:
				return forceChangePassword != FORCE_CHANGE_PASSWORD_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();
		return getIdentifier();
	}

} //UserImpl
