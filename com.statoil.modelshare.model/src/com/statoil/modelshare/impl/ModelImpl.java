/**
 */
package com.statoil.modelshare.impl;

import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelsharePackage;

import com.statoil.modelshare.TaskInformation;
import com.statoil.modelshare.util.UnzipUtility;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#getOrganisation <em>Organisation</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#getMail <em>Mail</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#getLastUpdated <em>Last Updated</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#isStask <em>Stask</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#getTaskInformation <em>Task Information</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#getPath <em>Path</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#getUsage <em>Usage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModelImpl extends AssetImpl implements Model {
	/**
	 * The default value of the '{@link #getOwner() <em>Owner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwner()
	 * @generated
	 * @ordered
	 */
	protected static final String OWNER_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getOwner() <em>Owner</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwner()
	 * @generated
	 * @ordered
	 */
	protected String owner = OWNER_EDEFAULT;
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
	 * The default value of the '{@link #getMail() <em>Mail</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMail()
	 * @generated
	 * @ordered
	 */
	protected static final String MAIL_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getMail() <em>Mail</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMail()
	 * @generated
	 * @ordered
	 */
	protected String mail = MAIL_EDEFAULT;
	/**
	 * The default value of the '{@link #getLastUpdated() <em>Last Updated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastUpdated()
	 * @generated
	 * @ordered
	 */
	protected static final Date LAST_UPDATED_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getLastUpdated() <em>Last Updated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastUpdated()
	 * @generated
	 * @ordered
	 */
	protected Date lastUpdated = LAST_UPDATED_EDEFAULT;
	/**
	 * The default value of the '{@link #isStask() <em>Stask</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStask()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STASK_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isStask() <em>Stask</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStask()
	 * @generated
	 * @ordered
	 */
	protected boolean stask = STASK_EDEFAULT;
	/**
	 * The cached value of the '{@link #getTaskInformation() <em>Task Information</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTaskInformation()
	 * @generated
	 * @ordered
	 */
	protected EList<TaskInformation> taskInformation;
	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;
	/**
	 * The default value of the '{@link #getUsage() <em>Usage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsage()
	 * @generated
	 * @ordered
	 */
	protected static final String USAGE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getUsage() <em>Usage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsage()
	 * @generated
	 * @ordered
	 */
	protected String usage = USAGE_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelImpl() {
		super();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelsharePackage.Literals.MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwner(String newOwner) {
		String oldOwner = owner;
		owner = newOwner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.MODEL__OWNER, oldOwner, owner));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.MODEL__ORGANISATION, oldOrganisation, organisation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMail(String newMail) {
		String oldMail = mail;
		mail = newMail;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.MODEL__MAIL, oldMail, mail));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastUpdated(Date newLastUpdated) {
		Date oldLastUpdated = lastUpdated;
		lastUpdated = newLastUpdated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.MODEL__LAST_UPDATED, oldLastUpdated, lastUpdated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStask() {
		return stask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStask(boolean newStask) {
		boolean oldStask = stask;
		stask = newStask;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.MODEL__STASK, oldStask, stask));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TaskInformation> getTaskInformation() {
		if (taskInformation == null) {
			taskInformation = new EObjectContainmentEList<TaskInformation>(TaskInformation.class, this, ModelsharePackage.MODEL__TASK_INFORMATION);
		}
		return taskInformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setPath(String newPath) {
		setPathGen(newPath);
		setModelType();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPathGen(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.MODEL__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUsage() {
		return usage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUsage(String newUsage) {
		String oldUsage = usage;
		usage = newUsage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.MODEL__USAGE, oldUsage, usage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelsharePackage.MODEL__TASK_INFORMATION:
				return ((InternalEList<?>)getTaskInformation()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelsharePackage.MODEL__OWNER:
				return getOwner();
			case ModelsharePackage.MODEL__ORGANISATION:
				return getOrganisation();
			case ModelsharePackage.MODEL__MAIL:
				return getMail();
			case ModelsharePackage.MODEL__LAST_UPDATED:
				return getLastUpdated();
			case ModelsharePackage.MODEL__STASK:
				return isStask();
			case ModelsharePackage.MODEL__TASK_INFORMATION:
				return getTaskInformation();
			case ModelsharePackage.MODEL__PATH:
				return getPath();
			case ModelsharePackage.MODEL__USAGE:
				return getUsage();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelsharePackage.MODEL__OWNER:
				setOwner((String)newValue);
				return;
			case ModelsharePackage.MODEL__ORGANISATION:
				setOrganisation((String)newValue);
				return;
			case ModelsharePackage.MODEL__MAIL:
				setMail((String)newValue);
				return;
			case ModelsharePackage.MODEL__LAST_UPDATED:
				setLastUpdated((Date)newValue);
				return;
			case ModelsharePackage.MODEL__STASK:
				setStask((Boolean)newValue);
				return;
			case ModelsharePackage.MODEL__TASK_INFORMATION:
				getTaskInformation().clear();
				getTaskInformation().addAll((Collection<? extends TaskInformation>)newValue);
				return;
			case ModelsharePackage.MODEL__PATH:
				setPath((String)newValue);
				return;
			case ModelsharePackage.MODEL__USAGE:
				setUsage((String)newValue);
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
			case ModelsharePackage.MODEL__OWNER:
				setOwner(OWNER_EDEFAULT);
				return;
			case ModelsharePackage.MODEL__ORGANISATION:
				setOrganisation(ORGANISATION_EDEFAULT);
				return;
			case ModelsharePackage.MODEL__MAIL:
				setMail(MAIL_EDEFAULT);
				return;
			case ModelsharePackage.MODEL__LAST_UPDATED:
				setLastUpdated(LAST_UPDATED_EDEFAULT);
				return;
			case ModelsharePackage.MODEL__STASK:
				setStask(STASK_EDEFAULT);
				return;
			case ModelsharePackage.MODEL__TASK_INFORMATION:
				getTaskInformation().clear();
				return;
			case ModelsharePackage.MODEL__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case ModelsharePackage.MODEL__USAGE:
				setUsage(USAGE_EDEFAULT);
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
			case ModelsharePackage.MODEL__OWNER:
				return OWNER_EDEFAULT == null ? owner != null : !OWNER_EDEFAULT.equals(owner);
			case ModelsharePackage.MODEL__ORGANISATION:
				return ORGANISATION_EDEFAULT == null ? organisation != null : !ORGANISATION_EDEFAULT.equals(organisation);
			case ModelsharePackage.MODEL__MAIL:
				return MAIL_EDEFAULT == null ? mail != null : !MAIL_EDEFAULT.equals(mail);
			case ModelsharePackage.MODEL__LAST_UPDATED:
				return LAST_UPDATED_EDEFAULT == null ? lastUpdated != null : !LAST_UPDATED_EDEFAULT.equals(lastUpdated);
			case ModelsharePackage.MODEL__STASK:
				return stask != STASK_EDEFAULT;
			case ModelsharePackage.MODEL__TASK_INFORMATION:
				return taskInformation != null && !taskInformation.isEmpty();
			case ModelsharePackage.MODEL__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case ModelsharePackage.MODEL__USAGE:
				return USAGE_EDEFAULT == null ? usage != null : !USAGE_EDEFAULT.equals(usage);
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (owner: ");
		result.append(owner);
		result.append(", organisation: ");
		result.append(organisation);
		result.append(", mail: ");
		result.append(mail);
		result.append(", lastUpdated: ");
		result.append(lastUpdated);
		result.append(", stask: ");
		result.append(stask);
		result.append(", path: ");
		result.append(path);
		result.append(", usage: ");
		result.append(usage);
		result.append(')');
		return result.toString();
	}
	
	private void setModelType() {
		if (path.toString().endsWith(".stask")) {
			setStask(true);
			
			unzipAndGetStaskInformation();
		}
	}

	private void unzipAndGetStaskInformation() {
		String tempDir = System.getProperty("java.io.tmpdir");
		UnzipUtility.unzip(path, tempDir);
	}

} //ModelImpl
