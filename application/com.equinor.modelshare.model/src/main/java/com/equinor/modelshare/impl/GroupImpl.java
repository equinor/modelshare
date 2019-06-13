/**
 */
package com.equinor.modelshare.impl;

import com.equinor.modelshare.Group;
import com.equinor.modelshare.ModelsharePackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.equinor.modelshare.impl.GroupImpl#getExternalIdentifier <em>External Identifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GroupImpl extends AccountImpl implements Group {
	/**
	 * The default value of the '{@link #getExternalIdentifier() <em>External Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExternalIdentifier()
	 * @generated
	 * @ordered
	 */
	protected static final String EXTERNAL_IDENTIFIER_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getExternalIdentifier() <em>External Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExternalIdentifier()
	 * @generated
	 * @ordered
	 */
	protected String externalIdentifier = EXTERNAL_IDENTIFIER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelsharePackage.Literals.GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExternalIdentifier() {
		return externalIdentifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExternalIdentifier(String newExternalIdentifier) {
		String oldExternalIdentifier = externalIdentifier;
		externalIdentifier = newExternalIdentifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.GROUP__EXTERNAL_IDENTIFIER, oldExternalIdentifier, externalIdentifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelsharePackage.GROUP__EXTERNAL_IDENTIFIER:
				return getExternalIdentifier();
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
			case ModelsharePackage.GROUP__EXTERNAL_IDENTIFIER:
				setExternalIdentifier((String)newValue);
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
			case ModelsharePackage.GROUP__EXTERNAL_IDENTIFIER:
				setExternalIdentifier(EXTERNAL_IDENTIFIER_EDEFAULT);
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
			case ModelsharePackage.GROUP__EXTERNAL_IDENTIFIER:
				return EXTERNAL_IDENTIFIER_EDEFAULT == null ? externalIdentifier != null : !EXTERNAL_IDENTIFIER_EDEFAULT.equals(externalIdentifier);
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (externalIdentifier: ");
		result.append(externalIdentifier);
		result.append(')');
		return result.toString();
	}

} //GroupImpl
