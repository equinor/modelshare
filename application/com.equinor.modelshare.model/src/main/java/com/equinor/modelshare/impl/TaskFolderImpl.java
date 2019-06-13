/**
 */
package com.equinor.modelshare.impl;

import com.equinor.modelshare.ModelsharePackage;
import com.equinor.modelshare.TaskDetails;
import com.equinor.modelshare.TaskFolder;
import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task Folder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.equinor.modelshare.impl.TaskFolderImpl#getTaskDetails <em>Task Details</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaskFolderImpl extends AbstractTaskImpl implements TaskFolder {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskFolderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelsharePackage.Literals.TASK_FOLDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TaskDetails> getTaskDetails() {
		if (taskDetails == null) {
			taskDetails = new EObjectContainmentEList<TaskDetails>(TaskDetails.class, this, ModelsharePackage.TASK_FOLDER__TASK_DETAILS);
		}
		return taskDetails;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelsharePackage.TASK_FOLDER__TASK_DETAILS:
				return ((InternalEList<?>)getTaskDetails()).basicRemove(otherEnd, msgs);
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
			case ModelsharePackage.TASK_FOLDER__TASK_DETAILS:
				return getTaskDetails();
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
			case ModelsharePackage.TASK_FOLDER__TASK_DETAILS:
				getTaskDetails().clear();
				getTaskDetails().addAll((Collection<? extends TaskDetails>)newValue);
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
			case ModelsharePackage.TASK_FOLDER__TASK_DETAILS:
				getTaskDetails().clear();
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
			case ModelsharePackage.TASK_FOLDER__TASK_DETAILS:
				return taskDetails != null && !taskDetails.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TaskFolderImpl
