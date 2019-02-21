/**
 */
package com.equinor.modelshare;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.equinor.modelshare.TaskFolder#getTaskDetails <em>Task Details</em>}</li>
 * </ul>
 *
 * @see com.equinor.modelshare.ModelsharePackage#getTaskFolder()
 * @model
 * @generated
 */
public interface TaskFolder extends AbstractTask {
	/**
	 * Returns the value of the '<em><b>Task Details</b></em>' containment reference list.
	 * The list contents are of type {@link com.equinor.modelshare.TaskDetails}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Task Details</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task Details</em>' containment reference list.
	 * @see com.equinor.modelshare.ModelsharePackage#getTaskFolder_TaskDetails()
	 * @model containment="true"
	 * @generated
	 */
	EList<TaskDetails> getTaskDetails();

} // TaskFolder
