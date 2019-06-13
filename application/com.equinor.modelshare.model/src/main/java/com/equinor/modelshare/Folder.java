/**
 */
package com.equinor.modelshare;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.equinor.modelshare.Folder#getAssets <em>Assets</em>}</li>
 * </ul>
 *
 * @see com.equinor.modelshare.ModelsharePackage#getFolder()
 * @model
 * @generated
 */
public interface Folder extends Asset {

	/**
	 * Returns the value of the '<em><b>Assets</b></em>' reference list.
	 * The list contents are of type {@link com.equinor.modelshare.Asset}.
	 * It is bidirectional and its opposite is '{@link com.equinor.modelshare.Asset#getFolder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assets</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assets</em>' reference list.
	 * @see com.equinor.modelshare.ModelsharePackage#getFolder_Assets()
	 * @see com.equinor.modelshare.Asset#getFolder
	 * @model opposite="folder"
	 * @generated
	 */
	EList<Asset> getAssets();
} // Folder
