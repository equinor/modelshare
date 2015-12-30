/**
 */
package com.statoil.modelshare.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelsharePackage;
import com.statoil.modelshare.TaskDetails;
import com.statoil.modelshare.TaskFolder;

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
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#getTaskDetails <em>Task Details</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#getUsage <em>Usage</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#getTaskFolders <em>Task Folders</em>}</li>
 *   <li>{@link com.statoil.modelshare.impl.ModelImpl#getSimaVersion <em>Sima Version</em>}</li>
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
	protected static final String LAST_UPDATED_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getLastUpdated() <em>Last Updated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastUpdated()
	 * @generated
	 * @ordered
	 */
	protected String lastUpdated = LAST_UPDATED_EDEFAULT;
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
	 * The cached value of the '{@link #getTaskDetails() <em>Task Details</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTaskDetails()
	 * @generated
	 * @ordered
	 */
	protected EList<TaskDetails> taskDetails;
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
	 * The cached value of the '{@link #getTaskFolders() <em>Task Folders</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTaskFolders()
	 * @generated
	 * @ordered
	 */
	protected EList<TaskFolder> taskFolders;
	/**
	 * The default value of the '{@link #getSimaVersion() <em>Sima Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimaVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String SIMA_VERSION_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getSimaVersion() <em>Sima Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimaVersion()
	 * @generated
	 * @ordered
	 */
	protected String simaVersion = SIMA_VERSION_EDEFAULT;
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
	public String getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastUpdated(String newLastUpdated) {
		String oldLastUpdated = lastUpdated;
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
	public EList<TaskDetails> getTaskDetails() {
		if (taskDetails == null) {
			taskDetails = new EObjectContainmentEList<TaskDetails>(TaskDetails.class, this, ModelsharePackage.MODEL__TASK_DETAILS);
		}
		return taskDetails;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setPath(String newPath) {
		super.setPath(newPath);
		setModelType();
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
	public EList<TaskFolder> getTaskFolders() {
		if (taskFolders == null) {
			taskFolders = new EObjectContainmentEList<TaskFolder>(TaskFolder.class, this, ModelsharePackage.MODEL__TASK_FOLDERS);
		}
		return taskFolders;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSimaVersion() {
		return simaVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSimaVersion(String newSimaVersion) {
		String oldSimaVersion = simaVersion;
		simaVersion = newSimaVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelsharePackage.MODEL__SIMA_VERSION, oldSimaVersion, simaVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelsharePackage.MODEL__TASK_DETAILS:
				return ((InternalEList<?>)getTaskDetails()).basicRemove(otherEnd, msgs);
			case ModelsharePackage.MODEL__TASK_FOLDERS:
				return ((InternalEList<?>)getTaskFolders()).basicRemove(otherEnd, msgs);
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
			case ModelsharePackage.MODEL__TASK_DETAILS:
				return getTaskDetails();
			case ModelsharePackage.MODEL__USAGE:
				return getUsage();
			case ModelsharePackage.MODEL__TASK_FOLDERS:
				return getTaskFolders();
			case ModelsharePackage.MODEL__SIMA_VERSION:
				return getSimaVersion();
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
				setLastUpdated((String)newValue);
				return;
			case ModelsharePackage.MODEL__STASK:
				setStask((Boolean)newValue);
				return;
			case ModelsharePackage.MODEL__TASK_DETAILS:
				getTaskDetails().clear();
				getTaskDetails().addAll((Collection<? extends TaskDetails>)newValue);
				return;
			case ModelsharePackage.MODEL__USAGE:
				setUsage((String)newValue);
				return;
			case ModelsharePackage.MODEL__TASK_FOLDERS:
				getTaskFolders().clear();
				getTaskFolders().addAll((Collection<? extends TaskFolder>)newValue);
				return;
			case ModelsharePackage.MODEL__SIMA_VERSION:
				setSimaVersion((String)newValue);
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
			case ModelsharePackage.MODEL__TASK_DETAILS:
				getTaskDetails().clear();
				return;
			case ModelsharePackage.MODEL__USAGE:
				setUsage(USAGE_EDEFAULT);
				return;
			case ModelsharePackage.MODEL__TASK_FOLDERS:
				getTaskFolders().clear();
				return;
			case ModelsharePackage.MODEL__SIMA_VERSION:
				setSimaVersion(SIMA_VERSION_EDEFAULT);
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
			case ModelsharePackage.MODEL__TASK_DETAILS:
				return taskDetails != null && !taskDetails.isEmpty();
			case ModelsharePackage.MODEL__USAGE:
				return USAGE_EDEFAULT == null ? usage != null : !USAGE_EDEFAULT.equals(usage);
			case ModelsharePackage.MODEL__TASK_FOLDERS:
				return taskFolders != null && !taskFolders.isEmpty();
			case ModelsharePackage.MODEL__SIMA_VERSION:
				return SIMA_VERSION_EDEFAULT == null ? simaVersion != null : !SIMA_VERSION_EDEFAULT.equals(simaVersion);
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
		result.append(", usage: ");
		result.append(usage);
		result.append(", simaVersion: ");
		result.append(simaVersion);
		result.append(')');
		return result.toString();
	}
	
	private void setModelType() {
		if (path.toString().endsWith(".stask")) {
			setStask(true);
		}
	}

	/**
	 * Kludge to get around the fact that we have two fields that represent the
	 * same information.
	 * 
	 * @generated NOT
	 */
	public String getDescription() {
		return getUsage();
	}

	/**
	 * Kludge to get around the fact that we have two fields that represent the
	 * same information.
	 * 
	 * @generated NOT
	 */
	public void setDescription(String newDescription) {
		super.setDescription(newDescription);
		setUsage(newDescription);;
	}
} //ModelImpl
